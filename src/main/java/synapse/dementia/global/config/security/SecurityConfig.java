package synapse.dementia.global.config.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import jakarta.servlet.http.HttpServletResponse;
import synapse.dementia.global.config.security.customFilter.CustomAuthenticationFailureHandler;
import synapse.dementia.global.config.security.customFilter.CustomAuthenticationFilter;
import synapse.dementia.global.config.security.customFilter.CustomAuthenticationSuccessHandler;
import synapse.dementia.global.config.security.exception.CustomAccessDeniedHandler;
import synapse.dementia.global.config.security.exception.CustomAuthenticationEntryPoint;

import java.util.Collections;

@Configuration
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;
	private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
	private final CustomAuthenticationFailureHandler authenticationFailureHandler;
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;
	private final CustomAccessDeniedHandler accessDeniedHandler;

	public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,
						  CustomAuthenticationSuccessHandler authenticationSuccessHandler,
						  CustomAuthenticationFailureHandler authenticationFailureHandler,
						  CustomAuthenticationEntryPoint authenticationEntryPoint, CustomAccessDeniedHandler accessDeniedHandler) {
		this.authenticationConfiguration = authenticationConfiguration;
		this.authenticationSuccessHandler = authenticationSuccessHandler;
		this.authenticationFailureHandler = authenticationFailureHandler;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.accessDeniedHandler = accessDeniedHandler;
	}

	@Bean
	@ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
	public WebSecurityCustomizer configureH2ConsoleEnable() {
		return web -> web.ignoring()
				.requestMatchers(PathRequest.toH2Console());
	}

	// CORS 설정
	CorsConfigurationSource corsConfigurationSource() {
		return request -> {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedHeaders(Collections.singletonList("*"));
			config.setAllowedMethods(Collections.singletonList("*"));
			config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:3000")); // 허용할 origin
			config.setAllowCredentials(true);
			return config;
		};
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
				.csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
				.formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.headers(httpSecurityHeadersConfigurer ->
						httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer
								.FrameOptionsConfig::sameOrigin)
				)
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/", "/ws/**").permitAll() // WebSocket 엔드포인트 허용
						.anyRequest().authenticated())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
						.sessionFixation((sessionFixation) -> sessionFixation.newSession())
						.maximumSessions(1)
						.maxSessionsPreventsLogin(true))
				.exceptionHandling(config -> config
						.authenticationEntryPoint(authenticationEntryPoint)
						.accessDeniedHandler(accessDeniedHandler))
				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/users/signout", "POST"))
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.logoutSuccessHandler((request, response, authentication) -> {
							response.setStatus(HttpServletResponse.SC_OK);
						}));

		return http.build();
	}

	@Bean
	public CustomAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
		customAuthenticationFilter.setAuthenticationManager(customAuthenticationManager());
		customAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		customAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

		customAuthenticationFilter.setSecurityContextRepository(
				new DelegatingSecurityContextRepository(
						new RequestAttributeSecurityContextRepository(),
						new HttpSessionSecurityContextRepository()
				));

		return customAuthenticationFilter;
	}

	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() throws Exception {
		return new BCryptPasswordEncoder();
	}

}

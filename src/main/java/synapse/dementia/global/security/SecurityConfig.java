package synapse.dementia.global.security;

import java.util.Collections;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;

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

	//  CORS 설정
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
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			// .rememberMe(r -> r
			// 	.rememberMeParameter("remember")
			// 	.tokenValiditySeconds(3600)
			// 	.alwaysRemember(false))
			.headers(httpSecurityHeadersConfigurer ->
				httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer
					.FrameOptionsConfig::sameOrigin)
			)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/", "/users/signUp", "/auth/signIn", "/admin/**").permitAll()
				// .requestMatchers("/admin/**").hasRole(Role.ROLE_ADMIN.name())
				.anyRequest().authenticated())
			.addFilterBefore(
				ajaxAuthenticationFilter(),
				UsernamePasswordAuthenticationFilter.class)
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
					.sessionFixation((sessionFixation) -> sessionFixation.newSession())
					.maximumSessions(1)
					.maxSessionsPreventsLogin(true)
				//.sessionRegistry()
			)
			.exceptionHandling(config -> config
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler))
			.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/auth/signOut", "POST"))
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessHandler(((request, response, authentication) -> {
					response.setStatus(HttpServletResponse.SC_OK);
				})))
		;

		return http.build();
	}

	// @Bean
	// public AuthenticationManager authenticationManager(
	// 	UserDetailsService userDetailsService,
	// 	PasswordEncoder passwordEncoder
	// ) {
	// 	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
	// 	authenticationProvider.setUserDetailsService(userDetailsService);
	// 	authenticationProvider.setPasswordEncoder(passwordEncoder);
	//
	// 	return new ProviderManager(authenticationProvider);
	// }

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

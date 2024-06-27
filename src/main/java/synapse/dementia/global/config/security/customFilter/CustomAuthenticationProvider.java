package synapse.dementia.global.config.security.customFilter;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import synapse.dementia.domain.users.member.domain.CustomUserDetails;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	//private final UserDetailsService userDetailsService;
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Lazy
	public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String nickName = authentication.getName();
		String password = (String)authentication.getCredentials();

		CustomUserDetails customUserDetails = (CustomUserDetails)userDetailsService.loadUserByUsername(nickName);

		if (customUserDetails == null || !passwordEncoder.matches(password, customUserDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}

		return new CustomAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(CustomAuthenticationToken.class);
		//return CustomAuthenticationToken.class.isAssignableFrom(authentication);
	}
}

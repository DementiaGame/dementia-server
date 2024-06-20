package synapse.dementia.global.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import synapse.dementia.domain.auth.dto.request.UsersSignInReq;
import synapse.dementia.global.exception.BadRequestException;
import synapse.dementia.global.exception.ErrorResult;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private ObjectMapper objectMapper = new ObjectMapper();

	public CustomAuthenticationFilter() {
		super(new AntPathRequestMatcher("/auth/signin", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException,
		IOException,
		ServletException {
		UsersSignInReq usersSignInReq = objectMapper.readValue(request.getReader(), UsersSignInReq.class);

		// ID, PASSWORD 가 있는지 확인
		if (!StringUtils.hasLength(usersSignInReq.nickName())
			|| !StringUtils.hasLength(usersSignInReq.password())) {
			throw new BadRequestException(ErrorResult.DTO_Request_BAD_REQUEST);
		}

		CustomAuthenticationToken token = new CustomAuthenticationToken(
			usersSignInReq.nickName(),
			usersSignInReq.password()
		);

		Authentication authentication = getAuthenticationManager().authenticate(token);

		logger.info("CustomAuthenticationFilter" + usersSignInReq.toString());

		return authentication;
	}

	// @Override
	// protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
	// 	FilterChain chain, Authentication authResult) throws IOException, ServletException {
	// 	SecurityContextHolder.getContext().setAuthentication(authResult);
	// 	response.setStatus(HttpServletResponse.SC_OK);
	// 	response.getWriter().write("{\"message\": \"Authentication successful\"}");
	// }
	//
	// @Override
	// protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
	// 	AuthenticationException failed) throws IOException, ServletException {
	// 	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	// 	response.getWriter().write("{\"message\": \"Authentication failed\"}");
	// }
}

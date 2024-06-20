package synapse.dementia.global.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import synapse.dementia.domain.auth.domain.CustomUserDetails;
import synapse.dementia.domain.auth.dto.response.UsersSignInRes;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	//private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		CustomUserDetails user = (CustomUserDetails)authentication.getPrincipal();

		UsersSignInRes usersSignInRes = new UsersSignInRes(user.getUsersIdx(), user.getUsername(), user.getAuthorities());

		response.setStatus(HttpStatus.OK.value());
		//response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		objectMapper.writeValue(response.getWriter(), usersSignInRes);
	}
}

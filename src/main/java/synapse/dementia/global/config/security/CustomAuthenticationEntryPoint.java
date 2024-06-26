package synapse.dementia.global.config.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import synapse.dementia.global.base.BaseResponse;
import synapse.dementia.global.exception.ErrorResult;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	// 인증 거부 시 수행
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		//response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write("USER_UNAUTHORIZED_REQUEST");
	}
}

package synapse.dementia.global.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import synapse.dementia.domain.users.domain.CustomUserDetails;

public class SecurityUtil {
	// 내부 서버 세션에서 user 정보 로드
	public static CustomUserDetails getCustomUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
		return userDetails;
	}
}

package synapse.dementia.domain.auth.dto.response;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public record UsersSignInRes(
	Long userIdx,
	String nickName,
	Collection<? extends GrantedAuthority> authority
) {
}

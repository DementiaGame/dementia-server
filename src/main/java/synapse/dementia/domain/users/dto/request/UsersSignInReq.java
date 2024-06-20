package synapse.dementia.domain.users.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UsersSignInReq(
	@NotBlank(message = "닉네임을 입력해주세요.")
	String nickName,

	@NotBlank(message = "비밀번호를 입력해주세요.")
	String password
) {
}

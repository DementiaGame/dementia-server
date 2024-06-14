package synapse.dementia.domain.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import synapse.dementia.domain.users.domain.Gender;
import synapse.dementia.domain.users.domain.Role;

public record UsersSignUpReq(
	@NotBlank(message = "닉네임을 입력해주세요.")
	@Pattern(regexp = "^(?![ㄱ-ㅎ])[a-zA-Z0-9가-힣]{2,12}$", message = "닉네임은 2~12자리면서 초성이 아닌 한글, 영어, 숫자로 구성되어야 합니다.")
	String nickName,

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$", message = "비밀번호는 8~16자리면서 알파벳, 숫자, 특수문자를 포함해야 합니다.")
	String password,

	@NotBlank(message = "재확인 비밀번호를 입력해주세요.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$", message = "비밀번호는 8~16자리면서 알파벳, 숫자, 특수문자를 포함해야 합니다.")
	String secondPassword,

	@NotNull(message = "출생연도를 입력해주세요.")
	Integer birthYear,

	@NotNull(message = "성별을 입력해주세요.")
	Gender gender,
	Boolean deleted,
	Role role
) {
	public UsersSignUpReq {
		if (deleted == null)
			deleted = false;
		if (role == null)
			role = Role.ROLE_USER;
	}
}

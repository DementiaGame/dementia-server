package synapse.dementia.domain.users.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import synapse.dementia.domain.users.domain.Users;

public record UsersSignUpRes(
	@NotBlank()
	@Pattern(regexp = "^(?![ㄱ-ㅎ])[a-zA-Z0-9가-힣]{2,12}$\n", message = "닉네임은 2~12자리면서 초성이 아닌 한글, 영어, 숫자로 구성되어야 합니다.")
	String nickName
) {
	public static UsersSignUpRes fromEntity(Users users) {
		return new UsersSignUpRes(users.getNickName());
	}
}

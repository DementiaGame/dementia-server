package synapse.dementia.domain.users.dto.request;

import synapse.dementia.domain.users.domain.Gender;
import synapse.dementia.domain.users.domain.Role;
import synapse.dementia.domain.users.domain.Users;

public record UsersSignUpReq(
	String nickName,
	String password,
	Integer birthYear,
	Gender gender,
	Boolean deleted,
	Role role
) {
	public UsersSignUpReq(Users entity) {
		this(entity.getNickName(), entity.getPassword(), entity.getBirthYear(), entity.getGender(), false,
			Role.ROLE_USER);
	}
}

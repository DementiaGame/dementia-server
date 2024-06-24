package synapse.dementia.domain.users.dto.response;

import synapse.dementia.domain.users.domain.Gender;
import synapse.dementia.domain.users.domain.Role;

public record UsersInfoRes(
	Long usersIdx,
	Integer birthYear,
	Gender gender,
	String nickName,
	Boolean deleted,
	String faceData,
	String profileImage,
	Role role
) {
}

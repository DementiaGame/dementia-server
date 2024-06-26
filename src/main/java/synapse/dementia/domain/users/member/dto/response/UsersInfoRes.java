package synapse.dementia.domain.users.member.dto.response;

import synapse.dementia.domain.users.member.domain.Gender;
import synapse.dementia.domain.users.member.domain.Role;

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

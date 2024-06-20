package synapse.dementia.domain.users.dto;

import synapse.dementia.domain.users.domain.Gender;
import synapse.dementia.domain.users.domain.Role;

public record UsersDto(
	Long usersIdx,
	Integer birthYear,
	Gender gender,
	String nickName,
	String password,
	Boolean deleted,
	String faceData,
	String profileImage,
	Role role

) {
}

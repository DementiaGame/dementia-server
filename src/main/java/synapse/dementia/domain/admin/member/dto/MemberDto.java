package synapse.dementia.domain.admin.member.dto;

import synapse.dementia.domain.users.member.domain.Gender;
import synapse.dementia.domain.users.member.domain.Role;

public record MemberDto(
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

package synapse.dementia.domain.admin.member.dto.request;

import synapse.dementia.domain.users.member.domain.Gender;

public record ModifyMemberDto(
	Long userIdx,
	Integer birthYear,
	Gender gender,
	String nickName
) {

}

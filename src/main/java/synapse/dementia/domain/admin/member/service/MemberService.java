package synapse.dementia.domain.admin.member.service;

import java.util.List;

import synapse.dementia.domain.admin.member.dto.MemberDto;
import synapse.dementia.domain.admin.member.dto.request.DeleteMemberDto;
import synapse.dementia.domain.admin.member.dto.request.ModifyMemberDto;

public interface MemberService {
	List<MemberDto> findAllUsers();

	void deleteUsers(DeleteMemberDto deleteMemberDto);

	void modifyUsers(ModifyMemberDto modifyMemberDto);
}

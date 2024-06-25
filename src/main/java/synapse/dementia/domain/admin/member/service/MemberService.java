package synapse.dementia.domain.admin.member.service;

import java.util.List;

import synapse.dementia.domain.admin.member.dto.MemberDto;

public interface MemberService {
	List<MemberDto> findAllUsers();
}

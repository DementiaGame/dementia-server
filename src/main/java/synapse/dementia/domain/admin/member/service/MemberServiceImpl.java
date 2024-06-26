package synapse.dementia.domain.admin.member.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.domain.admin.member.dto.MemberDto;
import synapse.dementia.domain.users.member.repository.UsersRepository;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final UsersRepository usersRepository;

	@Transactional(readOnly = true)
	public List<MemberDto> findAllUsers() {
		List<Users> users = usersRepository.findAll();
		return users.stream()
			.map(user -> new MemberDto(
				user.getUsersIdx(),
				user.getBirthYear(),
				user.getGender(),
				user.getNickName(),
				user.getPassword(),
				user.getDeleted(),
				user.getFaceData(),
				user.getProfileImage(),
				user.getRole()
			))
			.collect(Collectors.toList());
	}
}

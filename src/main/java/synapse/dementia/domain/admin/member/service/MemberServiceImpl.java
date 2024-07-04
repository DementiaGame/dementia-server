package synapse.dementia.domain.admin.member.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.admin.member.dto.request.DeleteMemberDto;
import synapse.dementia.domain.admin.member.dto.request.ModifyMemberDto;
import synapse.dementia.domain.users.member.domain.Gender;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.domain.admin.member.dto.MemberDto;
import synapse.dementia.domain.users.member.repository.UsersRepository;
import synapse.dementia.global.exception.ConflictException;
import synapse.dementia.global.exception.ErrorResult;
import synapse.dementia.global.exception.NotFoundException;

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

	@Override
	@Transactional
	public void deleteUsers(DeleteMemberDto deleteMemberDto) {
		usersRepository.findById(deleteMemberDto.userIdx())
			.orElseThrow(() -> new NotFoundException(ErrorResult.USER_NOT_EXIST_BAD_REQUEST));

		usersRepository.deleteById(deleteMemberDto.userIdx());
	}

	@Override
	@Transactional
	public void modifyUsers(ModifyMemberDto modifyMemberDto) {
		// IDX 확인 예외처리
		usersRepository.findById(modifyMemberDto.userIdx())
			.orElseThrow(() -> new NotFoundException(ErrorResult.USER_NOT_EXIST_BAD_REQUEST));

		// NickName 중복 예외처리
		Users users = usersRepository.findByNickName(modifyMemberDto.nickName())
			.orElseThrow(() -> new ConflictException(ErrorResult.NICK_NAME_DUPLICATION_CONFLICT));

		users.updateUsers(modifyMemberDto.birthYear(), modifyMemberDto.gender(), modifyMemberDto.nickName());
	}

}

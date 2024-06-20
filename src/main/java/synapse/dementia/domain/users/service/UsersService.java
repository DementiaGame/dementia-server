package synapse.dementia.domain.users.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import synapse.dementia.domain.users.domain.Users;
import synapse.dementia.domain.users.dto.UsersDto;
import synapse.dementia.domain.users.dto.request.UsersSignUpReq;
import synapse.dementia.domain.users.dto.response.UsersSignUpRes;
import synapse.dementia.domain.users.repository.UsersRepository;
import synapse.dementia.global.exception.BadRequestException;
import synapse.dementia.global.exception.ConflictException;
import synapse.dementia.global.exception.ErrorResult;

@Service
public class UsersService {

	private final UsersRepository usersRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Transactional
	public UsersSignUpRes signUp(UsersSignUpReq dto) {
		// todo: 1. 중복 닉네임 체크 2. 비밀번호 재확인 3. 비밀번호 암호화 4. save 5. return user's nickName in response
		if (usersRepository.findByNickName(dto.nickName()).isPresent()) {
			throw new ConflictException(ErrorResult.NICK_NAME_DUPLICATION_CONFLICT);
		}

		if (!dto.password().equals(dto.secondPassword())) {
			throw new BadRequestException(ErrorResult.PASSWORD_MISMATCH_BAD_REQUEST);
		}

		Users user = dto.toEntity(bCryptPasswordEncoder.encode(dto.password()));
		usersRepository.save(user);
		return new UsersSignUpRes(user.getNickName());
	}

	@Transactional(readOnly = true)
	public List<UsersDto> findAllUsers() {
		List<Users> users = usersRepository.findAll();
		return users.stream()
			.map(user -> new UsersDto(
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

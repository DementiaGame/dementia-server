package synapse.dementia.domain.users.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import synapse.dementia.domain.users.domain.Users;
import synapse.dementia.domain.users.dto.request.UsersSignUpReq;
import synapse.dementia.domain.users.dto.response.UsersSignUpRes;
import synapse.dementia.domain.users.repository.UsersRepository;

@Service
@Transactional
public class UsersService {

	private final UsersRepository usersRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public UsersSignUpRes signUp(UsersSignUpReq dto) {
		// todo: 1. 중복 닉네임 체크 2. 비밀번호 재확인 3. 비밀번호 암호화 4. save 5. return user's nickName in response
		if (usersRepository.findByNickName(dto.nickName()).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
		}

		if (!dto.password().equals(dto.secondPassword())) {
			throw new IllegalArgumentException("재확인 비밀번호가 일치하지 않습니다.");
		}

		Users user = dto.toEntity(bCryptPasswordEncoder.encode(dto.password()));
		usersRepository.save(user);
		return UsersSignUpRes.fromEntity(user);
	}
}

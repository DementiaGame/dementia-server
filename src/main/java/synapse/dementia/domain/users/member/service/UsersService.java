package synapse.dementia.domain.users.member.service;

import static synapse.dementia.global.config.security.SecurityUtil.*;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.domain.users.member.dto.request.UsersSignUpReq;
import synapse.dementia.domain.users.member.dto.response.UsersSignUpRes;
import synapse.dementia.domain.users.member.repository.UsersRepository;
import synapse.dementia.domain.users.member.domain.CustomUserDetails;
import synapse.dementia.domain.users.member.dto.request.UsersInfoUpdateReq;
import synapse.dementia.domain.users.member.dto.request.UsersSignInReq;
import synapse.dementia.domain.users.member.dto.response.UsersInfoRes;
import synapse.dementia.domain.users.member.dto.response.UsersSignInRes;
import synapse.dementia.global.exception.BadRequestException;
import synapse.dementia.global.exception.ConflictException;
import synapse.dementia.global.exception.ErrorResult;
import synapse.dementia.global.exception.NotFoundException;
import synapse.dementia.global.config.security.customFilter.CustomAuthenticationToken;

@Service
public class UsersService {

	private final UsersRepository usersRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final AuthenticationManager authenticationManager;

	public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
		AuthenticationManager authenticationManager) {
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.authenticationManager = authenticationManager;
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
		return new UsersSignUpRes(user.getUsersIdx(), user.getNickName());
	}

	// @Transactional(readOnly = true)
	// public List<UsersDto> findAllUsers() {
	// 	List<Users> users = usersRepository.findAll();
	// 	return users.stream()
	// 		.map(user -> new UsersDto(
	// 			user.getUsersIdx(),
	// 			user.getBirthYear(),
	// 			user.getGender(),
	// 			user.getNickName(),
	// 			user.getPassword(),
	// 			user.getDeleted(),
	// 			user.getFaceData(),
	// 			user.getProfileImage(),
	// 			user.getRole()
	// 		))
	// 		.collect(Collectors.toList());
	// }

	@Transactional
	public UsersInfoRes findCurrentUser() {
		// 내부 서버 세션에서 authentication 객체 로드
		CustomUserDetails userDetails = getCustomUserDetails();

		Optional<Users> user = usersRepository.findById(userDetails.getUsersIdx());

		return new UsersInfoRes(
			user.get().getUsersIdx(),
			user.get().getBirthYear(),
			user.get().getGender(),
			user.get().getNickName(),
			user.get().getDeleted(),
			user.get().getFaceData(),
			user.get().getProfileImage(),
			user.get().getRole()
		);
	}

	@Transactional
	public boolean findUserByNickName(String nickName) {
		Optional<Users> user = usersRepository.findByNickName(nickName);
		if(user.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public UsersInfoRes updateUser(UsersInfoUpdateReq dto) {
		// 중복 닉네임 체크
		if (usersRepository.findByNickName(dto.nickName()).isPresent()) {
			throw new ConflictException(ErrorResult.NICK_NAME_DUPLICATION_CONFLICT);
		}
		// 비밀번호 재확인
		if (!dto.password().equals(dto.secondPassword())) {
			throw new BadRequestException(ErrorResult.PASSWORD_MISMATCH_BAD_REQUEST);
		}

		CustomUserDetails userDetails = getCustomUserDetails();
		//Users user = usersRepository.updateUserInfoByUserId(userDetails.getUsersIdx(), dto);

		Users user = usersRepository.findById(userDetails.getUsersIdx()).orElseThrow(() -> {
			throw new NotFoundException(ErrorResult.USER_NOT_EXIST_BAD_REQUEST);
		});

		// 변경하지 않을 데이터도 요청에서 그대로 보내줘야 함, 더티체킹
		user.setBirthYear(dto.birthYear());
		user.setGender(dto.gender());
		user.setNickName(dto.nickName());
		user.setPassword(bCryptPasswordEncoder.encode(dto.password()));
		//user.setDeleted(dto.deleted());
		user.setFaceData(dto.faceData());
		user.setProfileImage(dto.profileImage());
		//user.setRole(dto.role());

		//usersRepository.save(user);

		return new UsersInfoRes(
			user.getUsersIdx(),
			user.getBirthYear(),
			user.getGender(),
			user.getNickName(),
			user.getDeleted(),
			user.getFaceData(),
			user.getProfileImage(),
			user.getRole()
		);
	}

	@Transactional
	public void deletedUser() {
		CustomUserDetails userDetails = getCustomUserDetails();

		Users user = usersRepository.findById(userDetails.getUsersIdx()).orElseThrow(() -> {
			throw new NotFoundException(ErrorResult.USER_NOT_EXIST_BAD_REQUEST);
		});

		user.setDeleted(Boolean.TRUE);
	}

	@Transactional
	public UsersSignInRes authenticateUser(UsersSignInReq dto) {
		// authenticationManager를 호출해 미인증 토큰을 인증 토큰으로 반환
		Authentication authentication = authenticationManager.authenticate(
			new CustomAuthenticationToken(dto.nickName(), dto.password())
		);

		// 인증된 토큰을 인증 컨텍스트에 설정
		SecurityContextHolder.getContext().setAuthentication(authentication);

		CustomUserDetails user = (CustomUserDetails)authentication.getPrincipal();
		return new UsersSignInRes(user.getUsersIdx(), user.getUsername(), user.getAuthorities());
	}

	public Users findUserById(Long userId) {
		return usersRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
	}
}

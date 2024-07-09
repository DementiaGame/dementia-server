package synapse.dementia.domain.users.game.memorygame.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import synapse.dementia.domain.users.game.memorygame.domain.MemoryGame;
import synapse.dementia.domain.users.game.memorygame.domain.MemoryGameResult;
import synapse.dementia.domain.users.game.memorygame.dto.request.MemoryGameResultRequest;
import synapse.dementia.domain.users.game.memorygame.dto.response.MemoryGameResultResponse;
import synapse.dementia.domain.users.game.memorygame.repository.MemoryGameRepository;
import synapse.dementia.domain.users.game.memorygame.repository.MemoryGameResultRepository;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.domain.users.member.repository.UsersRepository;
import synapse.dementia.global.exception.ErrorResult;
import synapse.dementia.global.exception.NotFoundException;

@Service
public class MemoryGameResultService {
	private final MemoryGameResultRepository memoryGameResultRepository;
	private final MemoryGameRepository memoryGameRepository;
	private final UsersRepository usersRepository;

	public MemoryGameResultService(MemoryGameResultRepository memoryGameResultRepository,
		MemoryGameRepository memoryGameRepository, UsersRepository usersRepository) {
		this.memoryGameResultRepository = memoryGameResultRepository;
		this.memoryGameRepository = memoryGameRepository;
		this.usersRepository = usersRepository;
	}

	@Transactional
	public MemoryGameResultResponse postGameResult(Long userIdx, MemoryGameResultRequest dto) {
		// 유저 존재 체크
		Users user = usersRepository.findById(userIdx)
			.orElseThrow(() -> new NotFoundException(ErrorResult.USER_NOT_EXIST_BAD_REQUEST));

		// 선택한 난이도에 해당하는 게임 로드
		MemoryGame game = memoryGameRepository.findById(dto.gameIdx())
			.orElseThrow(() -> new NotFoundException(ErrorResult.GAME_NOT_EXIST_BAD_REQUEST));

		MemoryGameResult memoryGameResult = MemoryGameResult.builder()
			.user(user)
			.game(game)
			.level(dto.level())
			.score(dto.score())
			.build();

		memoryGameResultRepository.save(memoryGameResult);

		// 얻은 점수를 하트수에 누적
		game.updateHearts(dto.score());
		memoryGameRepository.save(game);

		return new MemoryGameResultResponse(memoryGameResult.getScore());
	}
}

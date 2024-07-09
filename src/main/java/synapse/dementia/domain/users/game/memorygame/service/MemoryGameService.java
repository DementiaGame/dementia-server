package synapse.dementia.domain.users.game.memorygame.service;

import java.util.List;

import org.springframework.stereotype.Service;

import synapse.dementia.domain.users.game.memorygame.domain.MemoryGame;
import synapse.dementia.domain.users.game.memorygame.dto.response.MemoryGameLevelInfoResponse;
import synapse.dementia.domain.users.game.memorygame.repository.MemoryGameRepository;

@Service
public class MemoryGameService {
	private final MemoryGameRepository memoryGameRepository;

	public MemoryGameService(MemoryGameRepository memoryGameRepository) {
		this.memoryGameRepository = memoryGameRepository;
	}

	public MemoryGameLevelInfoResponse getGameLevelInfo() {
		List<MemoryGame> games = memoryGameRepository.findAll();
		return new MemoryGameLevelInfoResponse(games);
	}

	// public MemoryGameQuestionResponse getQuestionByLevel(Integer level) {
	//
	// 	return new MemoryGameQuestionResponse();
	// }
}

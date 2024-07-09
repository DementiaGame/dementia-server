package synapse.dementia.domain.users.game.memorygame.dto.response;

import java.util.List;

import synapse.dementia.domain.users.game.memorygame.domain.MemoryGame;

public record MemoryGameLevelInfoResponse(
	List<MemoryGame> games
) {
}

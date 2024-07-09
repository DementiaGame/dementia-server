package synapse.dementia.domain.users.game.memorygame.dto.request;

public record MemoryGameResultRequest(
	Long gameIdx,
	Integer level,
	Integer score
) {
}

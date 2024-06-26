package synapse.dementia.domain.users.game.gameresult.dto;

public record GameResultResponse(Long resultIdx, Long userId, String gameType, Boolean correct, Integer hearts) {
}

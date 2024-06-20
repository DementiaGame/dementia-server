package synapse.dementia.domain.gameresult.dto;

public record GameResultResponse(Long resultIdx, Long userId, String gameType, Boolean correct, Integer hearts) {
}

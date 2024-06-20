package synapse.dementia.domain.initialgame.dto.response;

public record InitialGameResultResponse(Long resultIdx, Long userId, Long questionIdx, Boolean correct, Integer gameScore, Integer hearts) {
}

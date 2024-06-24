package synapse.dementia.domain.initialgame.dto.response;

public record InitialGameResultResponse(Long questionIdx, Long userId, Boolean correct, Integer gameScore) {
}

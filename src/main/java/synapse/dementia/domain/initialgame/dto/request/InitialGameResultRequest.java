package synapse.dementia.domain.initialgame.dto.request;

public record InitialGameResultRequest(Long userId, Long questionIdx, String answer) {
}

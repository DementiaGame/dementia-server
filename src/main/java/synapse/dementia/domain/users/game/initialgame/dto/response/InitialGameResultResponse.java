package synapse.dementia.domain.users.game.initialgame.dto.response;

public record InitialGameResultResponse(Long questionIdx, Long userId, Boolean correct, Integer gameScore) {
}

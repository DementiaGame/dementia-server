package synapse.dementia.domain.users.game.initialgame.dto.response;

public record InitialGameResultResponse(Long resultId, Long userId, Boolean correct, Integer totalGameScore, Integer totalHearts) {}

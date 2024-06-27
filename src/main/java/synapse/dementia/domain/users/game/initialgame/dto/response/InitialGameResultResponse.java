package synapse.dementia.domain.users.game.initialgame.dto.response;

public record InitialGameResultResponse(Long questionIdx, Long userIdx, Boolean correct, Integer gameScore) {
}

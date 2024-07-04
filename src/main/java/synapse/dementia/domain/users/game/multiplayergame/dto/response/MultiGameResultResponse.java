package synapse.dementia.domain.users.game.multiplayergame.dto.response;

public record MultiGameResultResponse(Long resultIdx, Long gameIdx, Long userIdx, int correctAnswer) {}


package synapse.dementia.domain.users.game.multiplayergame.dto.request;

public record SubmitAnswerRequest(Long userId, Long questionId, String answer) {}


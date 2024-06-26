package synapse.dementia.domain.users.game.initialgame.dto.response;

public record InitialGameQuestionResponse(Long questionIdx, Long excelDataIdx, String consonantQuiz, String answerWord, String hintImage) {
}

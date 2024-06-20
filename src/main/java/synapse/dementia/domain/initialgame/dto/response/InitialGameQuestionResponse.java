package synapse.dementia.domain.initialgame.dto.response;

public record InitialGameQuestionResponse(Long questionIdx, Long excelDataIdx, String consonantQuiz, String answerWord, String hintImage) {
}

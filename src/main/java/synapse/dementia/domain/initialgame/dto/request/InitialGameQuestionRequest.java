package synapse.dementia.domain.initialgame.dto.request;

public record InitialGameQuestionRequest(
        String topic,
        String consonantQuiz,
        String answerWord,
        String hintImage
) {}

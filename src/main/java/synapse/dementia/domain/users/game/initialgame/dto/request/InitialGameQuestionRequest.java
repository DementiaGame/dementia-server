package synapse.dementia.domain.users.game.initialgame.dto.request;

public record InitialGameQuestionRequest(
        String topic,
        String consonantQuiz,
        String answerWord,
        String hintImage
) {}

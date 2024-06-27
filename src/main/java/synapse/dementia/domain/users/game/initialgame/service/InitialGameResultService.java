package synapse.dementia.domain.users.game.initialgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.domain.users.game.initialgame.domain.InitialGameQuestion;
import synapse.dementia.domain.users.game.initialgame.domain.InitialGameResult;
import synapse.dementia.domain.users.game.initialgame.dto.request.InitialGameResultRequest;
import synapse.dementia.domain.users.game.initialgame.dto.response.InitialGameResultResponse;
import synapse.dementia.domain.users.game.initialgame.repository.InitialGameQuestionRepository;
import synapse.dementia.domain.users.game.initialgame.repository.InitialGameResultRepository;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.domain.users.member.repository.UsersRepository;

@Service
public class InitialGameResultService {

    private final InitialGameQuestionRepository initialGameQuestionRepository;
    private final InitialGameResultRepository initialGameResultRepository;
    private final UsersRepository userRepository;

    @Autowired
    public InitialGameResultService(InitialGameQuestionRepository initialGameQuestionRepository, InitialGameResultRepository initialGameResultRepository, UsersRepository userRepository) {
        this.initialGameQuestionRepository = initialGameQuestionRepository;
        this.initialGameResultRepository = initialGameResultRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public InitialGameResultResponse checkAnswer(Long userId, Long questionIdx, InitialGameResultRequest request) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        InitialGameQuestion question = initialGameQuestionRepository.findById(questionIdx)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID"));

        boolean correct = question.getAnswerWord().equalsIgnoreCase(request.answer());

        InitialGameResult result = initialGameResultRepository.findByUser(user)
                .orElseGet(() -> initialGameResultRepository.save(new InitialGameResult(user, 0, 3)));

        if (correct) {
            question.incrementScore();
            question.setCorrect(true);
            result.addGameScore(1);
        } else {
            question.setCorrect(false);
            result.subtractHearts(1);
        }

        initialGameQuestionRepository.save(question);
        initialGameResultRepository.save(result);

        return new InitialGameResultResponse(result.getResultIdx(), result.getUser().getUsersIdx(), correct, result.getTotalGameScore(), result.getTotalHearts());
    }

    @Transactional(readOnly = true)
    public int getTotalCorrectAnswers(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return initialGameQuestionRepository.countByUserAndCorrect(user, true);
    }
}

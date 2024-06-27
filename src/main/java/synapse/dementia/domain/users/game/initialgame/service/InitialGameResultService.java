package synapse.dementia.domain.users.game.initialgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.domain.users.game.initialgame.domain.InitialGameQuestion;
import synapse.dementia.domain.users.game.initialgame.dto.request.InitialGameResultRequest;
import synapse.dementia.domain.users.game.initialgame.dto.response.InitialGameResultResponse;
import synapse.dementia.domain.users.game.initialgame.repository.InitialGameQuestionRepository;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.domain.users.member.repository.UsersRepository;

@Service
public class InitialGameResultService {

    private final InitialGameQuestionRepository initialGameQuestionRepository;
    private final UsersRepository userRepository;

    @Autowired
    public InitialGameResultService(InitialGameQuestionRepository initialGameQuestionRepository, UsersRepository userRepository) {
        this.initialGameQuestionRepository = initialGameQuestionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public InitialGameResultResponse checkAnswer(Long userId, Long questionIdx, InitialGameResultRequest request) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        InitialGameQuestion question = initialGameQuestionRepository.findById(questionIdx)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID"));

        boolean correct = question.getAnswerWord().equalsIgnoreCase(request.answer());

        if (correct) {
            question.incrementScore();
            question.setCorrect(true);
        } else {
            question.setCorrect(false);
        }

        InitialGameQuestion savedQuestion = initialGameQuestionRepository.save(question);

        return new InitialGameResultResponse(savedQuestion.getQuestionIdx(), savedQuestion.getUser().getUsersIdx(), savedQuestion.getCorrect(), savedQuestion.getGameScore());
    }

    @Transactional(readOnly = true)
    public int getTotalCorrectAnswers(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return initialGameQuestionRepository.countByUserAndCorrect(user, true);
    }
}

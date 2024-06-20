package synapse.dementia.domain.initialgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.domain.initialgame.domain.InitialGameQuestion;
import synapse.dementia.domain.initialgame.domain.InitialGameResult;
import synapse.dementia.domain.initialgame.dto.request.InitialGameResultRequest;
import synapse.dementia.domain.initialgame.dto.response.InitialGameResultResponse;
import synapse.dementia.domain.initialgame.repository.InitialGameResultRepository;
import synapse.dementia.domain.initialgame.repository.InitialGameQuestionRepository;
import synapse.dementia.domain.users.domain.Users;
import synapse.dementia.domain.users.repository.UsersRepository;

import java.util.Optional;

@Service
public class InitialGameResultService {

    private final InitialGameResultRepository initialGameResultRepository;
    private final UsersRepository userRepository;
    private final InitialGameQuestionRepository initialGameQuestionRepository;

    @Autowired
    public InitialGameResultService(InitialGameResultRepository initialGameResultRepository, UsersRepository userRepository, InitialGameQuestionRepository initialGameQuestionRepository) {
        this.initialGameResultRepository = initialGameResultRepository;
        this.userRepository = userRepository;
        this.initialGameQuestionRepository = initialGameQuestionRepository;
    }

    @Transactional
    public InitialGameResultResponse checkAnswer(Long userId, Long questionIdx, InitialGameResultRequest request) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        InitialGameQuestion question = initialGameQuestionRepository.findById(questionIdx)
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID"));

        boolean correct = question.getAnswerWord().equalsIgnoreCase(request.answer());

        Optional<InitialGameResult> existingResultOpt = initialGameResultRepository.findByUserAndQuestion(user, question);

        InitialGameResult initialGameResult;
        if (existingResultOpt.isPresent()) {
            initialGameResult = existingResultOpt.get();
            if (correct) {
                initialGameResult.incrementGameScore();
                initialGameResult.incrementHearts();
                initialGameResult.setCorrect(true);
            } else {
                initialGameResult.decrementHearts();
                initialGameResult.setCorrect(false);
            }
        } else {
            initialGameResult = InitialGameResult.builder()
                    .user(user)
                    .question(question)
                    .hintImage(question.getHintImage())
                    .correct(correct)
                    .gameScore(correct ? 1 : 0)
                    .hearts(correct ? 1 : 0)
                    .build();
        }

        InitialGameResult savedResult = initialGameResultRepository.save(initialGameResult);

        return new InitialGameResultResponse(savedResult.getResultIdx(), savedResult.getUser().getUsersIdx(), savedResult.getQuestion().getQuestionIdx(), savedResult.getCorrect(), savedResult.getGameScore(), savedResult.getHearts());
    }

    @Transactional(readOnly = true)
    public int getTotalCorrectAnswers(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        return initialGameResultRepository.countCorrectAnswersByUser(user);
    }
}

package synapse.dementia.domain.users.game.initialgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.domain.users.game.initialgame.domain.InitialGameQuestion;
import synapse.dementia.domain.users.game.initialgame.domain.InitialGameResult;
import synapse.dementia.domain.users.game.initialgame.domain.SelectedGameTopic;
import synapse.dementia.domain.users.game.initialgame.dto.request.InitialGameResultRequest;
import synapse.dementia.domain.users.game.initialgame.dto.response.InitialGameResultResponse;
import synapse.dementia.domain.users.game.initialgame.dto.response.TopicCorrectCountResponse;
import synapse.dementia.domain.users.game.initialgame.repository.InitialGameQuestionRepository;
import synapse.dementia.domain.users.game.initialgame.repository.InitialGameResultRepository;
import synapse.dementia.domain.users.game.initialgame.repository.SelectedGameTopicRepository;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.domain.users.member.repository.UsersRepository;

import java.util.List;

@Service
public class InitialGameResultService {

    private final InitialGameQuestionRepository initialGameQuestionRepository;
    private final InitialGameResultRepository initialGameResultRepository;
    private final UsersRepository userRepository;
    private final SelectedGameTopicRepository selectedGameTopicRepository;


    @Autowired
    public InitialGameResultService(InitialGameQuestionRepository initialGameQuestionRepository, InitialGameResultRepository initialGameResultRepository, UsersRepository userRepository,SelectedGameTopicRepository selectedGameTopicRepository) {
        this.initialGameQuestionRepository = initialGameQuestionRepository;
        this.initialGameResultRepository = initialGameResultRepository;
        this.selectedGameTopicRepository = selectedGameTopicRepository;
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
    @Transactional(readOnly = true)
    public TopicCorrectCountResponse getCorrectCountByTopic(Long userId, String topicName) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        List<SelectedGameTopic> selectedGameTopics = selectedGameTopicRepository.findByUserAndTopicName(user, topicName);

        if (selectedGameTopics.isEmpty()) {
            throw new IllegalArgumentException("Selected topic not found");
        }

        SelectedGameTopic selectedGameTopic = selectedGameTopics.get(0); // 여러 레코드 중 첫 번째를 사용
        int correctCount = initialGameQuestionRepository.countByUserAndSelectedGameTopicAndCorrect(user, selectedGameTopic, true);

        return new TopicCorrectCountResponse(user.getUsersIdx(), topicName, correctCount);
    }
}

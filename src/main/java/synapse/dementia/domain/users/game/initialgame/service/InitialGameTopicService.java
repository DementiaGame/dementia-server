package synapse.dementia.domain.users.game.initialgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.domain.users.game.initialgame.domain.SelectedGameTopic;
import synapse.dementia.domain.users.game.initialgame.dto.request.SelectGameTopicRequest;
import synapse.dementia.domain.users.game.initialgame.dto.response.InitialGameQuestionResponse;
import synapse.dementia.domain.users.game.initialgame.dto.response.InitialGameTopicResponse;
import synapse.dementia.domain.users.game.initialgame.dto.response.SelectedGameTopicResponse;
import synapse.dementia.domain.users.game.initialgame.repository.InitialGameQuestionRepository;
import synapse.dementia.domain.users.game.initialgame.repository.SelectedGameTopicRepository;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.domain.users.member.repository.UsersRepository;
import synapse.dementia.domain.admin.excel.repository.ExcelDataRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InitialGameTopicService {

    private final ExcelDataRepository excelDataRepository;
    private final SelectedGameTopicRepository selectedGameTopicRepository;
    private final InitialGameQuestionRepository initialGameQuestionRepository;
    private final UsersRepository userRepository;
    private final InitialGameQuestionService initialGameQuestionService;

    @Autowired
    public InitialGameTopicService(ExcelDataRepository excelDataRepository,
                                   SelectedGameTopicRepository selectedGameTopicRepository,
                                   InitialGameQuestionRepository initialGameQuestionRepository,
                                   UsersRepository userRepository,
                                   InitialGameQuestionService initialGameQuestionService) {
        this.excelDataRepository = excelDataRepository;
        this.selectedGameTopicRepository = selectedGameTopicRepository;
        this.initialGameQuestionRepository = initialGameQuestionRepository;
        this.userRepository = userRepository;
        this.initialGameQuestionService = initialGameQuestionService;
    }

    @Transactional(readOnly = true)
    public List<InitialGameTopicResponse> getAllTopics() {
        return excelDataRepository.findDistinctTopics()
                .stream()
                .map(InitialGameTopicResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public SelectedGameTopicResponse selectTopic(Long userId, SelectGameTopicRequest request) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // 새로운 주제 선택
        SelectedGameTopic selectedGameTopic = SelectedGameTopic.builder()
                .user(user)
                .topicName(request.topicName())
                .build();

        SelectedGameTopic savedSelectedTopic = selectedGameTopicRepository.save(selectedGameTopic);
        return new SelectedGameTopicResponse(savedSelectedTopic.getSelectedTopicIdx(), savedSelectedTopic.getUser().getUsersIdx(), savedSelectedTopic.getTopicName());
    }

    @Transactional
    public SelectAndQuestionsResponse selectTopicAndGetQuestions(Long userId, SelectGameTopicRequest request) {
        // 새로운 주제 선택
        SelectedGameTopicResponse selectedTopic = selectTopic(userId, request);

        // 선택한 주제에 대한 질문 가져오기
        List<InitialGameQuestionResponse> questions = initialGameQuestionService.getRandomQuestionsByTopic(userId, request);

        return new SelectAndQuestionsResponse(selectedTopic, questions);
    }

    @Transactional(readOnly = true)
    public List<SelectedGameTopicResponse> getSelectedTopics(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        return selectedGameTopicRepository.findByUser(user).stream()
                .map(topic -> new SelectedGameTopicResponse(topic.getSelectedTopicIdx(), topic.getUser().getUsersIdx(), topic.getTopicName()))
                .collect(Collectors.toList());
    }

    public record SelectAndQuestionsResponse(SelectedGameTopicResponse selectedTopic, List<InitialGameQuestionResponse> questions) {}
}

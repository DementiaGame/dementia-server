package synapse.dementia.domain.initialgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.domain.initialgame.domain.SelectedGameTopic;
import synapse.dementia.domain.initialgame.dto.request.SelectGameTopicRequest;
import synapse.dementia.domain.initialgame.dto.response.InitialGameTopicResponse;
import synapse.dementia.domain.initialgame.dto.response.SelectedGameTopicResponse;
import synapse.dementia.domain.initialgame.repository.SelectedGameTopicRepository;
import synapse.dementia.domain.users.repository.UsersRepository;
import synapse.dementia.global.excel.repository.ExcelDataRepository;
import synapse.dementia.domain.users.domain.Users;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameTopicService {

    private final ExcelDataRepository excelDataRepository;
    private final SelectedGameTopicRepository selectedGameTopicRepository;
    private final UsersRepository userRepository;

    @Autowired
    public GameTopicService(ExcelDataRepository excelDataRepository,
                            SelectedGameTopicRepository selectedGameTopicRepository,
                            UsersRepository userRepository) {
        this.excelDataRepository = excelDataRepository;
        this.selectedGameTopicRepository = selectedGameTopicRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<InitialGameTopicResponse> getAllTopics() {
        return excelDataRepository.findDistinctTopics()
                .stream()
                .map(InitialGameTopicResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public SelectedGameTopicResponse selectTopic(SelectGameTopicRequest request) {
        if (request.userId() == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }

        Users user = userRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        SelectedGameTopic selectedGameTopic = SelectedGameTopic.builder()
                .user(user)
                .topicName(request.topicName())
                .build();

        SelectedGameTopic savedSelectedTopic = selectedGameTopicRepository.save(selectedGameTopic);
        return new SelectedGameTopicResponse(savedSelectedTopic.getIdx(), savedSelectedTopic.getUser().getUsersIdx(), savedSelectedTopic.getTopicName());
    }
}

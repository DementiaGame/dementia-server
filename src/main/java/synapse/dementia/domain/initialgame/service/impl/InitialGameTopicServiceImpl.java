package synapse.dementia.domain.initialgame.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.domain.initialgame.domain.InitialGameTopic;
import synapse.dementia.domain.initialgame.repository.InitialTopicRepository;
import synapse.dementia.domain.initialgame.service.InitialGameTopicService;

@Service
public class InitialGameTopicServiceImpl implements InitialGameTopicService {
    private final InitialTopicRepository initialTopicRepository;

    public InitialGameTopicServiceImpl(InitialTopicRepository initialTopicRepository) {
        this.initialTopicRepository = initialTopicRepository;
    }

    @Override
    @Transactional
    public void saveTopic(String topicName) {
        InitialGameTopic topic = new InitialGameTopic(topicName);
        initialTopicRepository.save(topic);
    }
}

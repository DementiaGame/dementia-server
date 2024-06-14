package synapse.dementia.domain.initialgame.service;

import java.io.IOException;


public interface InitialGameTopicService {
    void saveTopic(String topicName) throws IOException;
}

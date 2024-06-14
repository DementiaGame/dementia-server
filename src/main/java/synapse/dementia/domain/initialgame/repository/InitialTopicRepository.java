package synapse.dementia.domain.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.initialgame.domain.InitialGameTopic;

import java.util.List;
import java.util.Optional;

public interface InitialTopicRepository extends JpaRepository<InitialGameTopic, Long> {
    List<InitialGameTopic> findByTopicNameIn(List<String> topicNames);
}

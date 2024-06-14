package synapse.dementia.domain.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.initialgame.domain.InitialGameTopic;

public interface InitialTopicRepository extends JpaRepository<InitialGameTopic, Long> {
}

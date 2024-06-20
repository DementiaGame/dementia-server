package synapse.dementia.domain.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.initialgame.domain.SelectedGameTopic;

public interface SelectedGameTopicRepository extends JpaRepository<SelectedGameTopic, Long> {
}

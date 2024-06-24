package synapse.dementia.domain.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.initialgame.domain.SelectedGameTopic;
import synapse.dementia.domain.users.domain.Users;

import java.util.Optional;

public interface SelectedGameTopicRepository extends JpaRepository<SelectedGameTopic, Long> {
    Optional<SelectedGameTopic> findByUserAndTopicName(Users user, String topicName);
}

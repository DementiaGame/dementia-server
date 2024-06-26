package synapse.dementia.domain.users.game.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.users.game.initialgame.domain.SelectedGameTopic;
import synapse.dementia.domain.users.member.domain.Users;

import java.util.Optional;

public interface SelectedGameTopicRepository extends JpaRepository<SelectedGameTopic, Long> {
    Optional<SelectedGameTopic> findByUserAndTopicName(Users user, String topicName);
    Optional<SelectedGameTopic> findByUser(Users user);

}

package synapse.dementia.domain.users.game.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synapse.dementia.domain.users.game.initialgame.domain.SelectedGameTopic;
import synapse.dementia.domain.users.member.domain.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface SelectedGameTopicRepository extends JpaRepository<SelectedGameTopic, Long> {
    Optional<SelectedGameTopic> findByUser(Users user);
    List<SelectedGameTopic> findByUserAndTopicName(Users user, String topicName);

}

package synapse.dementia.domain.users.game.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synapse.dementia.domain.users.game.initialgame.domain.InitialGameQuestion;
import synapse.dementia.domain.users.game.initialgame.domain.SelectedGameTopic;
import synapse.dementia.domain.users.member.domain.Users;


@Repository
public interface InitialGameQuestionRepository extends JpaRepository<InitialGameQuestion, Long> {
    int countByUserAndCorrect(Users user, boolean correct);
    void deleteBySelectedGameTopic(SelectedGameTopic selectedGameTopic);
    int countByUserAndSelectedGameTopicAndCorrect(Users user, SelectedGameTopic selectedGameTopic, boolean correct);


}

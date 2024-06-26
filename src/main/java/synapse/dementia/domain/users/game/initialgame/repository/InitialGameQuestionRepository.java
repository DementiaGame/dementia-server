package synapse.dementia.domain.users.game.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.users.game.initialgame.domain.InitialGameQuestion;
import synapse.dementia.domain.users.member.domain.Users;

public interface InitialGameQuestionRepository extends JpaRepository<InitialGameQuestion, Long> {
    int countByUserAndCorrect(Users user, boolean correct);
}

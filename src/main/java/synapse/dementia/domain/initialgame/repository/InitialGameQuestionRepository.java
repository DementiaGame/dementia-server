package synapse.dementia.domain.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.initialgame.domain.InitialGameQuestion;
import synapse.dementia.domain.users.domain.Users;

public interface InitialGameQuestionRepository extends JpaRepository<InitialGameQuestion, Long> {
    int countByUserAndCorrect(Users user, boolean correct);
}

package synapse.dementia.domain.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import synapse.dementia.domain.initialgame.domain.InitialGameResult;
import synapse.dementia.domain.initialgame.domain.InitialGameQuestion;
import synapse.dementia.domain.users.domain.Users;

import java.util.Optional;

public interface InitialGameResultRepository extends JpaRepository<InitialGameResult, Long> {
    Optional<InitialGameResult> findByUserAndQuestion(Users user, InitialGameQuestion question);

    @Query("SELECT COUNT(r) FROM InitialGameResult r WHERE r.user = :user AND r.correct = true")
    int countCorrectAnswersByUser(Users user);
}

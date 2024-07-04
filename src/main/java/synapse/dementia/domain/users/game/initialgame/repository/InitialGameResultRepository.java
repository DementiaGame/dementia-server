package synapse.dementia.domain.users.game.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import synapse.dementia.domain.users.game.initialgame.domain.InitialGameResult;
import synapse.dementia.domain.users.game.initialgame.domain.InitialGameQuestion;
import synapse.dementia.domain.users.member.domain.Users;

import java.util.Optional;

@Repository
public interface InitialGameResultRepository extends JpaRepository<InitialGameResult, Long> {

    Optional<InitialGameResult> findByUser(Users user);

}

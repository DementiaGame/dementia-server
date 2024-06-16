package synapse.dementia.domain.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.initialgame.domain.InitialGameResult;
import synapse.dementia.domain.users.domain.Users;

import java.util.List;

public interface InitialGameResultRepository extends JpaRepository<InitialGameResult, Long> {
    List<InitialGameResult> findByUser(Users user);
}

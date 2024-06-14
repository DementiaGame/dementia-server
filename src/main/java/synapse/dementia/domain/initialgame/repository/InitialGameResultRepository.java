package synapse.dementia.domain.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.initialgame.domain.InitialGameResult;

public interface InitialGameResultRepository extends JpaRepository<InitialGameResult, Long> {
}

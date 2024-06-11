package synapse.dementia.domain.initialgame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.initialgame.domain.InitialGameQuestion;

public interface InitialQuestionRepository extends JpaRepository<InitialGameQuestion, Long> {
}

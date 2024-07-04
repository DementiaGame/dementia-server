package synapse.dementia.domain.users.game.multiplayergame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameQuestion;

public interface MultiGameQuestionRepository extends JpaRepository<MultiGameQuestion, Long> {}


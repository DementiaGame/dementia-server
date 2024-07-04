package synapse.dementia.domain.users.game.multiplayergame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameQuestion;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameRoom;

import java.util.List;

public interface MultiGameQuestionRepository extends JpaRepository<MultiGameQuestion, Long> {
    List<MultiGameQuestion> findByMultiGameRoom(MultiGameRoom room);

}


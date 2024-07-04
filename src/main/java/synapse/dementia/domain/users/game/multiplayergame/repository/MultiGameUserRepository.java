package synapse.dementia.domain.users.game.multiplayergame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameRoom;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameUser;

import java.util.List;

public interface MultiGameUserRepository extends JpaRepository<MultiGameUser, Long> {
    List<MultiGameUser> findByMultiGameRoom(MultiGameRoom room);
}


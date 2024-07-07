package synapse.dementia.domain.users.game.multiplayergame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameRoom;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiplayerGame;

import java.util.Optional;

public interface MultiplayerGameRepository extends JpaRepository<MultiplayerGame, Long> {
    Optional<MultiplayerGame> findByMultiGameRoomAndIsStarted(MultiGameRoom room, boolean isStarted);

}


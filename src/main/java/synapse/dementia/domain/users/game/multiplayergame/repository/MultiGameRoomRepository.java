package synapse.dementia.domain.users.game.multiplayergame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameRoom;

import java.util.Optional;

public interface MultiGameRoomRepository extends JpaRepository<MultiGameRoom, Long> {
    Optional<MultiGameRoom> findByRoomName(String roomName);

}


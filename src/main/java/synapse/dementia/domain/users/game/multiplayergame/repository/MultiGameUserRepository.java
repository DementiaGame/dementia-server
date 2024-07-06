package synapse.dementia.domain.users.game.multiplayergame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameRoom;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameUser;
import synapse.dementia.domain.users.member.domain.Users;

import java.util.List;
import java.util.Optional;

public interface MultiGameUserRepository extends JpaRepository<MultiGameUser, Long> {
    List<MultiGameUser> findByMultiGameRoom(MultiGameRoom room);
    Optional<MultiGameUser> findByMultiGameRoomAndUser(MultiGameRoom room, Users user);
    List<MultiGameUser> findByMultiGameRoomId(Long roomId);


}


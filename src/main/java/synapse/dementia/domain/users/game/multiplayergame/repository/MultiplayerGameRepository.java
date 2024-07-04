package synapse.dementia.domain.users.game.multiplayergame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiplayerGame;

public interface MultiplayerGameRepository extends JpaRepository<MultiplayerGame, Long> {}


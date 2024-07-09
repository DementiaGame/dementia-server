package synapse.dementia.domain.users.game.memorygame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import synapse.dementia.domain.users.game.memorygame.domain.MemoryGame;

@Repository
public interface MemoryGameRepository extends JpaRepository<MemoryGame, Long> {
	Optional<MemoryGame> findByLevel(Integer level);
}

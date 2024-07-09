package synapse.dementia.domain.users.game.memorygame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import synapse.dementia.domain.users.game.memorygame.domain.MemoryGameResult;

public interface MemoryGameResultRepository extends JpaRepository<MemoryGameResult, Long> {
}

package synapse.dementia.global.config.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import synapse.dementia.domain.users.game.memorygame.domain.MemoryGame;
import synapse.dementia.domain.users.game.memorygame.repository.MemoryGameRepository;
import synapse.dementia.domain.users.member.repository.UsersRepository;

@Component
public class MemoryGameInitializer implements ApplicationRunner {

	private final MemoryGameRepository memoryGameRepository;

	public MemoryGameInitializer(MemoryGameRepository memoryGameRepository, UsersRepository usersRepository) {
		this.memoryGameRepository = memoryGameRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 초기 데이터를 MemoryGame 엔티티에 삽입
		MemoryGame easyLevelGame = MemoryGame.builder()
			.level(1)
			.cardPairs(3)
			.cardLows(2)
			.cardColumns(3)
			.hearts(0)
			.build();

		memoryGameRepository.save(easyLevelGame);

		MemoryGame normalLevelGame = MemoryGame.builder()
			.level(2)
			.cardPairs(6)
			.cardLows(3)
			.cardColumns(4)
			.hearts(0)
			.build();
		memoryGameRepository.save(normalLevelGame);

		MemoryGame hardLevelGame = MemoryGame.builder()
			.level(3)
			.cardPairs(8)
			.cardLows(4)
			.cardColumns(4)
			.hearts(0)
			.build();
		memoryGameRepository.save(hardLevelGame);

	}
}

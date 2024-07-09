package synapse.dementia.domain.users.game.memorygame.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MEMORY_GAME")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemoryGame {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "game_Idx")
	private Long gameIdx;

	@Column(name = "level")
	private Integer level;

	@Column(name = "card_pairs")
	private Integer cardPairs;

	@Column(name = "card_lows")
	private Integer cardLows;

	@Column(name = "card_columns")
	private Integer cardColumns;

	@Column(name = "hearts")
	private Integer hearts = 0;

	@Builder
	public MemoryGame(Integer level, Integer cardPairs, Integer cardLows, Integer cardColumns, Integer hearts) {
		this.level = level;
		this.cardPairs = cardPairs;
		this.cardLows = cardLows;
		this.cardColumns = cardColumns;
		this.hearts += hearts;
	}

	public void updateHearts(Integer gainedHearts) {
		this.hearts += gainedHearts;
	}
}

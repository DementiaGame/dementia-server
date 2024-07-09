package synapse.dementia.domain.users.game.memorygame.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synapse.dementia.domain.users.member.domain.Users;

@Entity
@Table(name = "MEMORY_GAME_RESULTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemoryGameResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "result_idx")
	private Long resultIdx;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_idx", nullable = false)
	private Users user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "game_idx", nullable = false)
	private MemoryGame game;

	@Column(name = "level", nullable = false)
	private Integer level;

	@Column(name = "score", nullable = false)
	private Integer score;

	// @Column(name = "hearts", nullable = false)
	// private Integer hearts;

	@Builder
	public MemoryGameResult(Users user, MemoryGame game, Integer level, Integer score) {
		this.user = user;
		this.game = game;
		this.level = level;
		this.score = score;
	}
}

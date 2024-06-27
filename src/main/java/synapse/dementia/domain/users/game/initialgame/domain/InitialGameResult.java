package synapse.dementia.domain.users.game.initialgame.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.global.base.BaseEntity;

@Entity
@Table(name = "INITIAL_GAME_RESULTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InitialGameResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long resultIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "total_game_score", nullable = false)
    private Integer totalGameScore;

    @Column(name = "total_hearts", nullable = false)
    private Integer totalHearts;

    @Builder
    public InitialGameResult(Users user, Integer totalGameScore, Integer totalHearts) {
        this.user = user;
        this.totalGameScore = totalGameScore != null ? totalGameScore : 0;
        this.totalHearts = totalHearts != null ? totalHearts : 3;
    }

    public void addGameScore(Integer score) {
        this.totalGameScore += score;
    }

    public void addHearts(Integer hearts) {
        this.totalHearts += hearts;
    }

    public void subtractHearts(Integer hearts) {
        this.totalHearts -= hearts;
    }
}

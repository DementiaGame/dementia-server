package synapse.dementia.domain.initialgame.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synapse.dementia.domain.users.domain.Users;
import synapse.dementia.global.domain.BaseEntity;

@Entity
@Table(name="INITIAL_GAME_RESULTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InitialGameResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="result_idx")
    private Long resultIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="users_idx",nullable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topic_idx", nullable = false)
    private InitialGameTopic topic;

    @Column(name="game_score",nullable = false)
    private Integer gameScore;

    @Column(name="hearts", nullable = false)
    private Integer hearts;

}

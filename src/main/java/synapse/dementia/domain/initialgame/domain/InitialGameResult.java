package synapse.dementia.domain.initialgame.domain;

import jakarta.persistence.*;
import lombok.Getter;
import synapse.dementia.domain.users.domain.Users;
import synapse.dementia.global.domain.BaseEntity;

@Entity
@Table(name="initial_game_results")
@Getter
public class InitialGameResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topic_id", nullable = false)
    private InitialGameTopic topic;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private Integer hearts;

}

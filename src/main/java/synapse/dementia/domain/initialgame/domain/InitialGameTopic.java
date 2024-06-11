package synapse.dementia.domain.initialgame.domain;

import jakarta.persistence.*;
import lombok.Getter;
import synapse.dementia.global.domain.BaseEntity;

@Entity
@Table(name="INITIAL_GAME_TOPICS")
@Getter
public class InitialGameTopic extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="topic_idx")
    private Long topicIdx;

    @Column(name="topic_name",nullable = false)
    private String topicName;
}

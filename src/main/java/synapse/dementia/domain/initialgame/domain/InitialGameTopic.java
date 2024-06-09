package synapse.dementia.domain.initialgame.domain;

import jakarta.persistence.*;
import synapse.dementia.global.domain.BaseEntity;

import java.util.List;

@Entity
@Table(name="initial_game_topics")
public class InitialGameTopic extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;

    @Column(nullable = false)
    private String topicName;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InitialGameQuestion> questions;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InitialGameResult> results;
}

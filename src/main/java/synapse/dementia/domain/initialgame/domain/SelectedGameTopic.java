package synapse.dementia.domain.initialgame.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synapse.dementia.domain.users.domain.Users;
import synapse.dementia.global.domain.BaseEntity;

@Entity
@Table(name = "SELECTED_GAME_TOPIC")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectedGameTopic extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "selected_topic_idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idx", nullable = false)
    private Users user;

    @Column(name = "topic_name", nullable = false)
    private String topicName;

    @Builder
    public SelectedGameTopic(Users user, String topicName) {
        this.user = user;
        this.topicName = topicName;
    }
}

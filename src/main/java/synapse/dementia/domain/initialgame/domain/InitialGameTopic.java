package synapse.dementia.domain.initialgame.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synapse.dementia.global.domain.BaseEntity;

@Entity
@Table(name="INITIAL_GAME_TOPICS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)// 토픽의 이름들은 변경과 추가가 가능하기에 사용 가능하다.
public class InitialGameTopic extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="topic_idx")
    private Long topicIdx;

    @Column(name="topic_name",nullable = false)
    private String topicName;

    @Builder
    public InitialGameTopic(String topicName){
        this.topicName=topicName;
    }

    //관리자가 주제이름을 수정할 경우, setter대신 사용하기 위해 메서드 생성
    public void updateTopicName(String topicName){
        this.topicName=topicName;
    }
}

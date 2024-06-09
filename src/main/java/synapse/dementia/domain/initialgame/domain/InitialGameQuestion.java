package synapse.dementia.domain.initialgame.domain;

import jakarta.persistence.*;
import lombok.Getter;
import synapse.dementia.global.domain.BaseEntity;

@Entity
@Table(name="initial_game_questions")
public class InitialGameQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topic_id",nullable = false)
    private InitialGameTopic topicIdx;

    //초성퀴즈 문제
    @Column(nullable = false)
    private String consonants;

    //초성퀴즈 정답
    @Column(nullable = false)
    private String answer;


    //쿠폰받을 때 생성가능
    @Column(nullable = true)
    private String hintImage;

}

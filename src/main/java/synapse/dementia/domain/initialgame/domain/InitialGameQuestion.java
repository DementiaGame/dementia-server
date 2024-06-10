package synapse.dementia.domain.initialgame.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import synapse.dementia.global.domain.BaseEntity;

@Entity
@Getter
@ToString
@Table(name="INITIAL_GAME_QUESTIONS")
public class InitialGameQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_idx")
    private Long questionIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topic_id",nullable = false)
    private InitialGameTopic topicIdx;

    //초성퀴즈 문제
    @Column(name="consonant_quiz", nullable = false)
    private String consonantQuiz;

    //초성퀴즈 정답
    @Column(name="answer_word", nullable = false)
    private String answerWord;


    //쿠폰받을 때 생성가능
    @Column(name="hint_image",nullable = true)
    private String hintImage;

}

package synapse.dementia.domain.initialgame.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synapse.dementia.global.domain.BaseEntity;
import synapse.dementia.global.excel.model.ExcelData;

@Entity
@Getter
@Table(name="INITIAL_GAME_QUESTIONS")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class InitialGameQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="question_idx")
    private Long questionIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="excel_data_id", nullable = false)
    private ExcelData excelData;

    //초성퀴즈 문제
    @Column(name="consonant_quiz")
    private String consonantQuiz;

    //초성퀴즈 정답
    @Column(name="answer_word")
    private String answerWord;

    //쿠폰받을 때 생성가능
    @Column(name="hint_image",nullable = true)
    private String hintImage;

    @Builder
    public InitialGameQuestion(ExcelData excelData, String consonantQuiz, String answerWord, String hintImage) {
        this.excelData = excelData;
        this.consonantQuiz = consonantQuiz;
        this.answerWord = answerWord;
        this.hintImage = hintImage;
    }
}

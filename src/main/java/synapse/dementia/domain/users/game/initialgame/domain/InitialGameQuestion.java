package synapse.dementia.domain.users.game.initialgame.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synapse.dementia.global.base.BaseEntity;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.domain.admin.excel.model.ExcelData;

@Entity
@Getter
@Table(name = "INITIAL_GAME_QUESTIONS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InitialGameQuestion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_idx")
    private Long questionIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "excel_data_id", nullable = false)
    private ExcelData excelData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_topic_idx", nullable = false)
    private SelectedGameTopic selectedGameTopic;

    @Column(name = "consonant_quiz")
    private String consonantQuiz;

    @Column(name = "answer_word")
    private String answerWord;

    @Column(name = "hint_image", nullable = true)
    private String hintImage;

    @Column(name = "correct", nullable = false)
    private Boolean correct = false; // 기본값 설정

    @Column(name = "game_score", nullable = false)
    private Integer gameScore = 0; // 기본값 설정

    @Builder
    public InitialGameQuestion(ExcelData excelData, Users user, SelectedGameTopic selectedGameTopic, String consonantQuiz, String answerWord, String hintImage, Boolean correct, Integer gameScore) {
        this.excelData = excelData;
        this.user = user;
        this.selectedGameTopic = selectedGameTopic;
        this.consonantQuiz = consonantQuiz;
        this.answerWord = answerWord;
        this.hintImage = hintImage;
        this.correct = correct != null ? correct : false;
        this.gameScore = gameScore != null ? gameScore : 0;
    }

    public void incrementHearts() {
        this.gameScore += 1;
    }

    public void decrementHearts() {
        this.gameScore -= 1;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}

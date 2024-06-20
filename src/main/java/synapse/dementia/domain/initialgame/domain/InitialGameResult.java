package synapse.dementia.domain.initialgame.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synapse.dementia.domain.users.domain.Users;
import synapse.dementia.global.domain.BaseEntity;
import synapse.dementia.global.excel.model.ExcelData;

@Entity
@Table(name="INITIAL_GAME_RESULTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InitialGameResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="result_id")
    private Long resultIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="excel_data_id", nullable = false)
    private ExcelData excelData;

    @Column(name="hint_image", nullable = true)
    private String hintImage;

    @Column(name="correct", nullable = false)
    private Boolean correct;

    @Column(name="game_score", nullable = false)
    private Integer gameScore;

    @Column(name="hearts", nullable = false)
    private Integer hearts;

    @Builder
    public InitialGameResult(Users user, ExcelData excelData, String hintImage, Boolean correct, Integer gameScore, Integer hearts) {
        this.user = user;
        this.excelData = excelData;
        this.hintImage = hintImage;
        this.correct = correct;
        this.gameScore = gameScore;
        this.hearts = hearts;
    }

    public void markAsCorrect() {
        this.correct = true;
        this.gameScore += 1;
        this.hearts += 1;
    }

    public void markAsIncorrect() {
        this.correct = false;
        this.hearts -= 1;
    }
}

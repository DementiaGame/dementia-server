package synapse.dementia.domain.initialgame.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synapse.dementia.domain.users.domain.Users;
import synapse.dementia.global.domain.BaseEntity;

@Entity
@Table(name = "INITIAL_GAME_RESULTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InitialGameResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "result_id")
    private Long resultIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private InitialGameQuestion question;

    @Column(name = "hint_image", nullable = true)
    private String hintImage;

    @Column(name = "correct", nullable = false)
    private Boolean correct;

    @Column(name = "game_score", nullable = false)
    private Integer gameScore;

    @Column(name = "hearts", nullable = false)
    private Integer hearts;

    @Builder
    public InitialGameResult(Users user, InitialGameQuestion question, String hintImage, Boolean correct, Integer gameScore, Integer hearts) {
        this.user = user;
        this.question = question;
        this.hintImage = hintImage;
        this.correct = correct;
        this.gameScore = gameScore;
        this.hearts = hearts;
    }

    public void incrementGameScore() {
        this.gameScore += 1;
    }

    public void incrementHearts() {
        this.hearts += 1;
    }

    public void decrementHearts() {
        this.hearts -= 1;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}

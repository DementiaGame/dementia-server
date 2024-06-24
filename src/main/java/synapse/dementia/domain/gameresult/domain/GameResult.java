//package synapse.dementia.domain.gameresult.domain;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import synapse.dementia.domain.users.domain.Users;
//import synapse.dementia.global.domain.BaseEntity;
//
//@Entity
//@Getter
//@Table(name = "GAME_RESULTS")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class GameResult extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "result_id")
//    private Long resultIdx;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private Users user;
//
//    @Column(name = "game_type", nullable = false)
//    private String gameType;
//
//    @Column(name = "correct", nullable = false)
//    private Boolean correct;
//
//    @Column(name = "gameScore", nullable = false)
//    private Integer hearts;
//
//    @Builder
//    public GameResult(Users user, String gameType, Boolean correct, Integer hearts) {
//        this.user = user;
//        this.gameType = gameType;
//        this.correct = correct;
//        this.hearts = hearts;
//    }
//
//    public void incrementHearts() {
//        this.hearts += 1;
//    }
//
//    public void decrementHearts() {
//        this.hearts -= 1;
//    }
//
//    public void setCorrect(boolean correct) {
//        this.correct = correct;
//    }
//}

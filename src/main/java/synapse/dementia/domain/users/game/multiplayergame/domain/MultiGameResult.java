package synapse.dementia.domain.users.game.multiplayergame.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synapse.dementia.domain.users.member.domain.Users;

@Entity
@Getter
@NoArgsConstructor
@Table(name="MULTI_GAME_RESULT")
public class MultiGameResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="game_idx", nullable = false)
    private MultiplayerGame multiplayerGame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_idx", nullable = false)
    private Users user;

    private int correctAnswer;

    @Builder
    public MultiGameResult(MultiplayerGame multiplayerGame, Users user, int correctAnswer){
        this.multiplayerGame=multiplayerGame;
        this.user=user;
        this.correctAnswer=correctAnswer;
    }
}

package synapse.dementia.domain.users.game.multiplayergame.domain;

import jakarta.persistence.*;
import lombok.*;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.global.base.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="MULTI_GAME")
public class MultiplayerGame extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameIdx;

    @OneToOne
    @JoinColumn(name = "room_id", nullable = false)
    private MultiGameRoom multiGameRoom;

    private boolean isStarted;

    @Builder
    public MultiplayerGame(MultiGameRoom multiGameRoom, boolean isStarted){
        this.multiGameRoom=multiGameRoom;
        this.isStarted=isStarted;
    }
}

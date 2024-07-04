package synapse.dementia.domain.users.game.multiplayergame.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Table(name="ROOM")
public class MultiGameRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomIdx;

    private String roomName;
    @OneToOne(mappedBy = "multiGameRoom", cascade = CascadeType.ALL)
    private MultiplayerGame multiplayerGame;

    @Builder
    public MultiGameRoom(String roomName){
        this.roomName=roomName;
    }

}

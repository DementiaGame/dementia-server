package synapse.dementia.domain.users.game.multiplayergame.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synapse.dementia.domain.users.member.domain.Users;

@Entity
@Getter
@NoArgsConstructor
@Table(name="ROOM_USER")
public class MultiGameUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="room_idx",nullable = false)
    private MultiGameRoom multiGameRoom;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_idx", nullable = false)
    private Users user;

    private String answer;  // 사용자가 제출한 답을 저장하는 필드


    @Builder
    public MultiGameUser(MultiGameRoom multiGameRoom, Users user){
        this.multiGameRoom=multiGameRoom;
        this.user=user;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

package synapse.dementia.domain.users.game.multiplayergame.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MULTI_QUESTION")
public class MultiGameQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionIdx;

    private String question;

    private String answer;

    @Builder
    public MultiGameQuestion(String question, String answer){
        this.question=question;
        this.answer=answer;
    }
}

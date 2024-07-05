package synapse.dementia.global.config.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import synapse.dementia.domain.users.game.multiplayergame.dto.response.MultiGameUserResponse;

@Controller
public class WebSocketController {

    @MessageMapping("/join")
    @SendTo("/topic/room")
    public MultiGameUserResponse joinRoom(MultiGameUserResponse user) {
        // 로직 추가: 사용자 방에 추가
        return user; // 모든 클라이언트에게 전송
    }
}

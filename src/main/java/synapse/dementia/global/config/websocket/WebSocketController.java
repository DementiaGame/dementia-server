package synapse.dementia.global.config.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import synapse.dementia.domain.users.game.multiplayergame.dto.response.MultiGameUserResponse;
import synapse.dementia.domain.users.game.multiplayergame.service.MultiplayerGameService;
import synapse.dementia.domain.users.member.service.UsersService;
import synapse.dementia.domain.users.member.domain.Users;

import java.util.List;

@Controller
public class WebSocketController {

    private final MultiplayerGameService multiplayerGameService;
    private final UsersService usersService;

    public WebSocketController(MultiplayerGameService multiplayerGameService, UsersService usersService) {
        this.multiplayerGameService = multiplayerGameService;
        this.usersService = usersService;
    }

    @MessageMapping("/join")
    @SendTo("/topic/room")
    public List<MultiGameUserResponse> joinRoom(MultiGameUserResponse user) {
        // 사용자 정보를 조회합니다.
        Users existingUser = usersService.findUserById(user.usersIdx());
        // 사용자를 방에 추가합니다.
        multiplayerGameService.joinRoom(user.roomIdx(), existingUser);
        // 방에 있는 사용자 목록을 반환합니다.
        return multiplayerGameService.getUsersInRoom(user.roomIdx());
    }
}

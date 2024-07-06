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
        Users existingUser = usersService.findUserById(user.usersIdx());
        multiplayerGameService.joinRoom(user.roomIdx(), existingUser);
        return multiplayerGameService.getUsersInRoom(user.roomIdx());
    }
}

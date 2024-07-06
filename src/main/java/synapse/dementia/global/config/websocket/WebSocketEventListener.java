package synapse.dementia.global.config.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import synapse.dementia.domain.users.game.multiplayergame.dto.response.MultiGameUserResponse;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.domain.users.member.service.UsersService;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketEventListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final UsersService usersService;
    private final Map<String, MultiGameUserResponse> connectedUsers = new HashMap<>();

    public WebSocketEventListener(SimpMessagingTemplate messagingTemplate, UsersService usersService) {
        this.messagingTemplate = messagingTemplate;
        this.usersService = usersService;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        Long userIdx = Long.valueOf(event.getMessage().getHeaders().get("userIdx").toString());

        Users user = usersService.findUserById(userIdx); // 유저 정보를 데이터베이스에서 조회
        MultiGameUserResponse userResponse = new MultiGameUserResponse(user.getUsersIdx(), null, user.getUsersIdx()); // 필요한 유저 정보를 설정
        connectedUsers.put(sessionId, userResponse);
        messagingTemplate.convertAndSend("/topic/room-users", connectedUsers.values());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        connectedUsers.remove(sessionId);
        messagingTemplate.convertAndSend("/topic/room-users", connectedUsers.values());
    }
}

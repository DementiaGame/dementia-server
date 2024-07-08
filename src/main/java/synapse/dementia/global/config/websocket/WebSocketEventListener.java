package synapse.dementia.global.config.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import synapse.dementia.domain.users.game.multiplayergame.dto.response.MultiGameUserResponse;
import synapse.dementia.domain.users.game.multiplayergame.service.MultiplayerGameService;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketEventListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final MultiplayerGameService multiplayerGameService;
    private final Map<String, MultiGameUserResponse> connectedUsers = new HashMap<>();

    public WebSocketEventListener(SimpMessagingTemplate messagingTemplate, MultiplayerGameService multiplayerGameService) {
        this.messagingTemplate = messagingTemplate;
        this.multiplayerGameService = multiplayerGameService;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        // WebSocket 연결 이벤트를 처리합니다. 필요시 구현 가능합니다.
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        // 연결이 끊긴 사용자를 조회합니다.
        MultiGameUserResponse user = connectedUsers.remove(sessionId);
        if (user != null) {
            // 사용자가 방을 떠납니다.
            multiplayerGameService.leaveRoom(user.roomIdx(), user.usersIdx());
            // 방에 있는 사용자 목록을 업데이트합니다.
            messagingTemplate.convertAndSend("/topic/room/" + user.roomIdx(), multiplayerGameService.getUsersInRoom(user.roomIdx()));
        }
    }
}

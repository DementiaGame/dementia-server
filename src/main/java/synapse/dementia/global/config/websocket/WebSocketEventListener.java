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
        // Handle WebSocket connection event if necessary
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        MultiGameUserResponse user = connectedUsers.remove(sessionId);
        if (user != null) {
            multiplayerGameService.leaveRoom(user.roomIdx(), user.usersIdx());
            messagingTemplate.convertAndSend("/topic/room/" + user.roomIdx(), multiplayerGameService.getUsersInRoom(user.roomIdx()));
        }
    }
}

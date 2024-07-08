package synapse.dementia.global.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 엔드포인트를 등록합니다. 클라이언트는 이 엔드포인트로 연결합니다.
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 메시지 브로커를 활성화합니다. "/topic"과 "/queue" 접두사가 붙은 목적지를 구독합니다.
        config.enableSimpleBroker("/topic", "/queue");
        // 애플리케이션 목적지 접두사를 설정합니다. 클라이언트는 "/app" 접두사가 붙은 목적지로 메시지를 보냅니다.
        config.setApplicationDestinationPrefixes("/app");
    }
}

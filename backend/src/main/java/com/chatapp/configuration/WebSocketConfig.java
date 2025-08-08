package com.chatapp.configuration;

import com.chatapp.observer.ChatRoom;
import com.chatapp.repository.ChatMessageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatMessageRepository chatMessageRepository;

    public WebSocketConfig(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    // singleton shared room
    @Bean
    public ChatRoom chatRoom() {
        return new ChatRoom();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatWebSocketHandlerFactory(chatRoom(), chatMessageRepository), "/chat")
            .setAllowedOrigins("http://localhost:4200");
    }
}

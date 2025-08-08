package com.chatapp.configuration;

import com.chatapp.observer.ChatRoom;
import com.chatapp.observer.ChatWebSocketHandler;
import com.chatapp.repository.ChatMessageRepository;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A WebSocketHandler that creates a ChatWebSocketHandler instance per connection and delegates events.
 */
public class ChatWebSocketHandlerFactory implements WebSocketHandler {

    private final ChatRoom chatRoom;
    private final ChatMessageRepository chatMessageRepository;
    // maps sessionId -> handler instance
    private final Map<String, ChatWebSocketHandler> handlers = new ConcurrentHashMap<>();

    public ChatWebSocketHandlerFactory(ChatRoom chatRoom, ChatMessageRepository chatMessageRepository) {
        this.chatRoom = chatRoom;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        ChatWebSocketHandler handler = new ChatWebSocketHandler(chatRoom, chatMessageRepository);
        handlers.put(session.getId(), handler);
        handler.afterConnectionEstablished(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        ChatWebSocketHandler handler = handlers.get(session.getId());
        if (handler != null && message instanceof TextMessage) {
            handler.handleTextMessage(session, (TextMessage) message);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        ChatWebSocketHandler handler = handlers.get(session.getId());
        if (handler != null) {
            handler.handleTransportError(session, exception);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        ChatWebSocketHandler handler = handlers.remove(session.getId());
        if (handler != null) {
            handler.afterConnectionClosed(session, closeStatus);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}


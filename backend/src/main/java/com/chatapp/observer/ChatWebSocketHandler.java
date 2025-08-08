package com.chatapp.observer;

import com.chatapp.model.entity.ChatMessage;
import com.chatapp.repository.ChatMessageRepository;
import com.chatapp.util.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler implements ChatObserver {

    private final ChatRoom chatRoom;
    private final ChatMessageRepository chatMessageRepository;
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    // These are per-instance (per connection)
    private WebSocketSession session;
    private String username;

    public ChatWebSocketHandler(ChatRoom chatRoom, ChatMessageRepository chatMessageRepository) {
        this.chatRoom = chatRoom;
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
        this.username = getUsernameFromSession(session);
        chatRoom.addObserver(this, username);
        // Optionally send the online users list here
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        chatRoom.removeObserver(username, this);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
            // ensure sender & timestamp are set by the server (authoritative)
            chatMessage.setSender(username);
            chatMessage.setTimestamp(DateUtils.now());

            chatMessageRepository.save(chatMessage);

            String broadcastMsg = objectMapper.writeValueAsString(chatMessage);
            chatRoom.broadcast(broadcastMsg);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error handling chat message", e);
        }
    }

    // Called by ChatRoom to deliver messages to this session
    @Override
    public void receive(String message) {
        try {
            if (session != null && session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUsernameFromSession(WebSocketSession session) {
        if (session.getUri() == null) return "Anonymous";
        String query = session.getUri().getQuery();
        if (query == null) return "Anonymous";
        for (String param : query.split("&")) {
            String[] parts = param.split("=");
            if (parts.length == 2 && "username".equals(parts[0])) {
                return parts[1];
            }
        }
        return "Anonymous";
    }
}

package com.chatapp.observer;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler implements ChatObserver {

    private final ChatRoom chatRoom;
    private WebSocketSession session;

    public ChatWebSocketHandler(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
        chatRoom.addObserver(this);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        chatRoom.broadcast(message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        chatRoom.removeObserver(this);
    }

    @Override
    public void receive(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

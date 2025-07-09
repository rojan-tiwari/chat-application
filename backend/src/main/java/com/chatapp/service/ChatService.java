package com.chatapp.service;

import com.chatapp.model.entity.ChatMessage;
import com.chatapp.observer.ChatSubject;
import com.chatapp.observer.Observer;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private ChatSubject chatSubject = new ChatSubject();

    public void sendMessage(ChatMessage chatMessage) {
        chatSubject.notifyObservers(chatMessage);
    }

    public void registerObserver(Observer observer) {
        chatSubject.registerObserver(observer);
    }
}

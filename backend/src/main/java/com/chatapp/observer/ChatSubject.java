package com.chatapp.observer;

import com.chatapp.model.entity.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatSubject {

    private final List<Observer> observers = new ArrayList<>();

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(ChatMessage chatMessage) {
        for (Observer observer : observers) {
            observer.update(chatMessage);
        }
    }
}

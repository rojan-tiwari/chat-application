package com.chatapp.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatRoom {
    private final List<ChatObserver> observers = new CopyOnWriteArrayList<>();

    public void addObserver(ChatObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ChatObserver observer) {
        observers.remove(observer);
    }

    public void broadcast(String message) {
        for (ChatObserver observer : observers) {
            observer.receive(message);
        }
    }
}


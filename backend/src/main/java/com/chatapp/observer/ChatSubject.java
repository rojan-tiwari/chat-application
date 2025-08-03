package com.chatapp.observer;

public interface ChatSubject {
    void registerObserver(ChatObserver observer);
    void removeObserver(ChatObserver observer);
    void notifyObservers(String message);
}

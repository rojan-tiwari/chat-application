package com.chatapp.observer;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoom {

    // username -> set of observers (one per tab/session)
    private final Map<String, Set<ChatObserver>> observers = new ConcurrentHashMap<>();

    public void addObserver(ChatObserver observer, String username) {
        observers.computeIfAbsent(username, k -> ConcurrentHashMap.newKeySet()).add(observer);
    }

    public void removeObserver(String username, ChatObserver observer) {
        Set<ChatObserver> set = observers.get(username);
        if (set != null) {
            set.remove(observer);
            if (set.isEmpty()) {
                observers.remove(username);
            }
        }
    }

    public void broadcast(String message) {
        observers.values().stream()
            .flatMap(Set::stream)
            .forEach(obs -> {
                try { obs.receive(message); }
                catch (Exception ex) { ex.printStackTrace(); }
            });
    }
}

package com.chatapp.observer;

import com.chatapp.model.entity.ChatMessage;

public interface Observer {
    void update(ChatMessage chatMessage);
}

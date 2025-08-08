package com.chatapp.service;

import com.chatapp.model.entity.User;

import java.util.Optional;

public interface UserService {

    void registerUser(User user);

    Optional<User> findByUsername(String username,String password);
}

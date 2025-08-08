package com.chatapp.service.impl;


import com.chatapp.model.entity.User;
import com.chatapp.repository.UserRepository;
import com.chatapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        user.setPassword(encryptPassword(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordMatches(user.get().getPassword(), password)) {
            return user;
        } else {
            return Optional.empty();
        }
    }

    private boolean passwordMatches(String storedPassword, String providedPassword) {
        return bCryptPasswordEncoder.matches(providedPassword, storedPassword);
    }

    private String encryptPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}

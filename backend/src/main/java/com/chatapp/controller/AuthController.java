package com.chatapp.controller;

import com.chatapp.configuration.jwt.JwtUtil;
import com.chatapp.exception.UsernameAlreadyExistsException;
import com.chatapp.model.entity.User;
import com.chatapp.model.entity.record.AuthResponse;
import com.chatapp.model.entity.record.LoginRequest;
import com.chatapp.service.UserService;
import com.chatapp.util.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<String>> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok(BaseResponse.success("User Registered Successfully",null));
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(400, "An unexpected error occurred"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<Object>> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<User> user = userService.findByUsername(loginRequest.username(), loginRequest.password());
            if (user.isPresent()) {
                String token = jwtUtil.generateToken(user.get().getUsername());
                return ResponseEntity.ok(BaseResponse.success(
                    new AuthResponse(token, user.get().getUsername()),
                    "Login was successful"
                ));
            } else {
                return ResponseEntity.badRequest().body(BaseResponse.error(400, "Invalid username or password"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(400, e.getMessage()));
        }
    }

}

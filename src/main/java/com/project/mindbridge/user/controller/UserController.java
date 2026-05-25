package com.project.mindbridge.user.controller;

import com.project.mindbridge.user.dto.CreateUserRequest;
import com.project.mindbridge.user.dto.LoginRequest;
import com.project.mindbridge.user.dto.LoginResponse;
import com.project.mindbridge.user.dto.RefreshTokenRequest;
import com.project.mindbridge.user.entity.User;
import com.project.mindbridge.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/createUser")
    public User createUser(@RequestBody CreateUserRequest user) {
        return userService.createUser(user.getEmailId(),user.getPassword());
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest.getEmailId(), loginRequest.getPassword());
    }

    @PostMapping("/refresh")
    public LoginResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return userService.refreshToken(refreshTokenRequest.getRefreshToken());
    }

    @PostMapping("/logout")
    public String logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return userService.logout(refreshTokenRequest.getRefreshToken());
    }
}

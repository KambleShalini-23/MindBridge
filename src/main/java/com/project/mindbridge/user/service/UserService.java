package com.project.mindbridge.user.service;

import com.project.mindbridge.config.JwtService;
import com.project.mindbridge.user.dto.LoginRequest;
import com.project.mindbridge.user.dto.LoginResponse;
import com.project.mindbridge.user.entity.RefreshToken;
import com.project.mindbridge.user.entity.Role;
import com.project.mindbridge.user.entity.User;
import com.project.mindbridge.user.repository.RefreshTokenRepository;
import com.project.mindbridge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public User createUser(String emailId, String password) {
        User user = User.builder()
                .emailId(emailId)
                .password(passwordEncoder.encode(password))
                .role(Role.PATIENT) // Default role, can be changed as needed
                .build();

        return userRepository.save(user);
    }

    public LoginResponse login(String emailId, String password) {
        User user = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        refreshTokenRepository.findByEmailId(user.getEmailId()).ifPresent(refreshTokenRepository::delete);

        refreshTokenRepository.flush();

        // most of the times even if we delete the entry from the database, it is not deleted immediately
        // and it is still present in the database for some time until the transaction is committed.
        // So to make sure that the previous token is deleted before saving the new token,
        // we can use flush() method to flush the changes to the database immediately.

        String refreshToken = UUID.randomUUID().toString();

        RefreshToken rt = RefreshToken.builder()
                .token(refreshToken)
                .emailId(user.getEmailId())
                .expiryDate(new Date(System.currentTimeMillis() + 7L + 24 * 60 * 60 * 1000))
                .build();

        refreshTokenRepository.saveAndFlush(rt);

        String token = jwtService.generateToken(user.getEmailId(), user.getRole().name());
        return new LoginResponse(token, refreshToken);
    }

    public LoginResponse refreshToken(String refreshToken){
        RefreshToken rt = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token"));

        if (rt.getExpiryDate().before(new Date())) {
            refreshTokenRepository.delete(rt);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token expired");
        }

        User user = userRepository.findByEmailId(rt.getEmailId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        String token = jwtService.generateToken(user.getEmailId(), user.getRole().name());
        return new LoginResponse(token, refreshToken);
    }

    public String logout(String refreshToken) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailId = auth.getName();

        refreshTokenRepository.findByEmailId(emailId).ifPresent(refreshTokenRepository::delete);

        return "Logged out successfully";
    }
}

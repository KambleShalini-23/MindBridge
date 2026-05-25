package com.project.mindbridge.user.repository;

import com.project.mindbridge.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByEmailId(String emailId);

    void deleteByEmailId(String emailId);

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}

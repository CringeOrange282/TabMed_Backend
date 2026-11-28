package com.example.demo.service;

import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.User;
import com.example.demo.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${refreshToken.expiration}")
    private long refreshTokenExpiration;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken generateRefreshToken(User user){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if (token.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(token);
            throw new RuntimeException("время токена истекло");
        }
        return token;
    }
}

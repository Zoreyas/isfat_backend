package org.erusakov.backend.service.impl;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.erusakov.backend.controller.response.user.TokenResponse;
import org.erusakov.backend.entities.redis.AccessTokenEntity;
import org.erusakov.backend.entities.redis.RefreshTokenEntity;
import org.erusakov.backend.repository.redis.AccessTokenRepository;
import org.erusakov.backend.repository.redis.RefreshTokenRepository;
import org.erusakov.backend.service.TokenService;
import org.erusakov.backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private Duration accessExpirationDuration;
    private Duration refreshExpirationDuration;
    private SecretKey secretKey;

    @Value("${security.access-token-expiration}")
    private int accessTokenExpirationInHours;

    @Value("${security.refresh-token-expiration}")
    private int refreshTokenExpirationInDays;

    @Value("${security.secret-key-string}")
    private byte[] secretKeyValue;

    @Override
    public TokenResponse generateTokens(String username) {
        if (accessTokenRepository.existsByUsername(username) || refreshTokenRepository.existsByUsername(username)) {
            invalidateTokens(username);
        }
        String accessToken = JwtUtils.generateToken(username, accessExpirationDuration.toMillis(), secretKey);
        saveAccessToken(username, accessToken);
        String refreshToken = JwtUtils.generateToken(username, refreshExpirationDuration.toMillis(), secretKey);
        saveRefreshToken(username, refreshToken);
        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public String getUsernameFromToken(String token) {
        return JwtUtils.extractUsername(token, secretKey);
    }

    @Override
    public boolean validateToken(String token, String username) throws AuthenticationException {
        String tokenUsername = JwtUtils.extractUsername(token, secretKey);
        return username.equals(tokenUsername) && !JwtUtils.isTokenExpired(token, secretKey);
    }

    @Override
    public boolean validateAccessToken(String token) {
        return accessTokenRepository.existsByToken(token);
    }

    @Override
    public void invalidateTokens(String token) {
        String username = JwtUtils.extractUsername(token, secretKey);
        accessTokenRepository.deleteById(username);
        refreshTokenRepository.deleteById(username);
    }

    @Override
    public TokenResponse refreshTokens(String username, String refreshToken) {
        boolean hasRefreshToken = refreshTokenRepository.existsByToken(refreshToken);
        if (!hasRefreshToken) {
            throw new AuthenticationException("Invalid refresh token") {
            };
        }
        return generateTokens(username);
    }

    @PostConstruct
    private void init() {
        secretKey = Keys.hmacShaKeyFor(secretKeyValue);
        secretKeyValue = null;
        accessExpirationDuration = Duration.ofHours(accessTokenExpirationInHours);
        refreshExpirationDuration = Duration.ofDays(refreshTokenExpirationInDays);
    }

    private void saveAccessToken(String username, String accessToken) {
        AccessTokenEntity accessTokenEntity = new AccessTokenEntity();
        accessTokenEntity.setUsername(username);
        accessTokenEntity.setToken(accessToken);
        accessTokenRepository.save(accessTokenEntity);
    }

    private void saveRefreshToken(String username, String refreshToken) {
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setUsername(username);
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenRepository.save(refreshTokenEntity);
    }

}
package org.erusakov.backend.service;

import org.erusakov.backend.controller.response.user.TokenResponse;
import org.springframework.security.core.AuthenticationException;

public interface TokenService {

    TokenResponse generateTokens(String username);

    String getUsernameFromToken(String token);

    boolean validateToken(String token, String username) throws AuthenticationException;

    boolean validateAccessToken(String token) throws AuthenticationException;

    void invalidateTokens(String username);

    TokenResponse refreshTokens(String username, String refreshToken);

}


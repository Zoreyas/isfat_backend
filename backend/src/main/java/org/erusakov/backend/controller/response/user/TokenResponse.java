package org.erusakov.backend.controller.response.user;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}

package org.erusakov.backend.controller.response.user;

public record NotificationResponse(
        Long id,
        Long userId,
        String message,
        boolean isRead
) {
}

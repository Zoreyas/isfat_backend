package org.erusakov.backend.controller.request.user;

public record CreateNotificationRequest(
        Long userId,
        String message
) {
}

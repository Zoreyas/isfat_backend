package org.erusakov.backend.controller;

import lombok.RequiredArgsConstructor;
import org.erusakov.backend.controller.request.user.CreateNotificationRequest;
import org.erusakov.backend.controller.response.IdResponse;
import org.erusakov.backend.controller.response.user.NotificationResponse;
import org.erusakov.backend.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationResponse>> getUserNotifications(
            @PathVariable Long userId,
            @RequestParam Boolean unreadOnly) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId, unreadOnly));
    }

    @PostMapping
    public ResponseEntity<IdResponse> createNotification(
            @RequestBody CreateNotificationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new IdResponse(notificationService.createNotification(request)));
    }

    // Пометить как прочитанное
    @PatchMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
    }

    // Удаление уведомления
    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
    }
}

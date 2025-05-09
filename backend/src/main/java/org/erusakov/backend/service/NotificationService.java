package org.erusakov.backend.service;

import org.erusakov.backend.controller.request.user.CreateNotificationRequest;
import org.erusakov.backend.controller.response.user.NotificationResponse;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> getUserNotifications(Long userId, Boolean isRead);

    Long createNotification(Long userId, CreateNotificationRequest request);

    void markAsRead(Long id);

    void deleteNotification(Long id);
}

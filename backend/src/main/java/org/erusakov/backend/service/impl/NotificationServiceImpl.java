package org.erusakov.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.erusakov.backend.controller.request.user.CreateNotificationRequest;
import org.erusakov.backend.controller.response.user.NotificationResponse;
import org.erusakov.backend.entities.user.NotificationEntity;
import org.erusakov.backend.mapper.NotificationMapper;
import org.erusakov.backend.repository.jpa.NotificationRepository;
import org.erusakov.backend.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public List<NotificationResponse> getUserNotifications(Long userId, boolean unreadOnly) {
        return notificationRepository.findAllByUser_IdAndIsRead(userId, unreadOnly).stream()
                .map(notificationMapper::toResponse).toList();
    }

    @Override
    public Long createNotification(CreateNotificationRequest request) {
        return notificationRepository.save(notificationMapper.toEntity(request)).getId();
    }

    @Override
    public void markAsRead(Long id) {
        NotificationEntity notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found with id: " + id));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}

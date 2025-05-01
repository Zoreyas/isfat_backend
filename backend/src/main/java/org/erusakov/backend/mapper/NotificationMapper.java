package org.erusakov.backend.mapper;

import lombok.RequiredArgsConstructor;
import org.erusakov.backend.controller.request.user.CreateNotificationRequest;
import org.erusakov.backend.controller.response.user.NotificationResponse;
import org.erusakov.backend.entities.user.NotificationEntity;
import org.erusakov.backend.entities.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    public NotificationEntity toEntity(CreateNotificationRequest request) {
        return NotificationEntity
                .builder()
                .user(new UserEntity(request.userId()))
                .message(request.message())
                .isRead(false)
                .build();
    }

    public NotificationResponse toResponse(NotificationEntity entity) {
        return new NotificationResponse(
                entity.getId(),
                entity.getUser().getId(),
                entity.getMessage(),
                entity.getIsRead()
        );
    }

}

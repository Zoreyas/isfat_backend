package org.erusakov.backend.repository.jpa;

import org.erusakov.backend.entities.user.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findAllByUser_Id(Long id);

    List<NotificationEntity> findAllByUser_IdAndIsRead(Long userId, Boolean isRead);

}

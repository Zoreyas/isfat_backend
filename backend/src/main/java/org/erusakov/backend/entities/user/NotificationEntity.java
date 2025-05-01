package org.erusakov.backend.entities.user;

import jakarta.persistence.*;
import lombok.*;
import org.erusakov.backend.entities.BaseEntity;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEntity extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "message")
    private String message;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

}

package org.erusakov.backend.entities.car;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.erusakov.backend.entities.BaseEntity;
import org.erusakov.backend.entities.user.DriverEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "car_driver_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDriverHistory extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private CarEntity car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private DriverEntity driver;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;
}
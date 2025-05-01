package org.erusakov.backend.entities.car;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.erusakov.backend.entities.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "car_repair_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRepairHistory extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private CarEntity car;

    @Column(name = "mechanic_id", nullable = false)
    private String mechanic;

    @Column(name = "repair_details", nullable = false)
    private String repairDetails;

    @Column(name = "repaired_at", nullable = false, updatable = false)
    private LocalDateTime repairedAt = LocalDateTime.now();
}
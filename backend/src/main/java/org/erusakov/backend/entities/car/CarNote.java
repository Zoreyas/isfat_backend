package org.erusakov.backend.entities.car;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.erusakov.backend.entities.BaseEntity;

@Entity
@Table(name = "car_notes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarNote extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private CarEntity car;

    @Column(nullable = false)
    private String note;
}
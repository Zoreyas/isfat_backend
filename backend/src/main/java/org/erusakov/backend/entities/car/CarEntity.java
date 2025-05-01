package org.erusakov.backend.entities.car;

import jakarta.persistence.*;
import lombok.*;
import org.erusakov.backend.entities.BaseEntity;
import org.erusakov.backend.entities.user.DriverEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarEntity extends BaseEntity<Long> {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "integrity", nullable = false)
    private Integer integrity;

    @Column(name = "max_fuel_tank", nullable = false)
    private Double maxFuelTank;

    @Column(name = "current_fuel", nullable = false)
    private Double currentFuel;

    @Column(name = "max_cargo_capacity", nullable = false)
    private Long maxCapacity;

    @Column(name = "current_cargo_load", nullable = false)
    private Long currentLoad;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarNote> notes = new ArrayList<>();

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarDriverHistory> driverHistory = new ArrayList<>();

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarRepairHistory> repairHistory = new ArrayList<>();

    @Transient
    public DriverEntity getCurrentDriver() {
        return driverHistory.stream()
                .filter(h -> h.getEndedAt() == null)
                .findFirst()
                .map(CarDriverHistory::getDriver)
                .orElse(null);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "name='" + name + '\'' +
                ", integrity=" + integrity +
                ", maxFuelTank=" + maxFuelTank +
                ", currentFuel=" + currentFuel +
                ", maxCapacity=" + maxCapacity +
                ", currentLoad=" + currentLoad +
                '}';
    }
}

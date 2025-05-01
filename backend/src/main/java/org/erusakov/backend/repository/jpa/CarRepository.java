package org.erusakov.backend.repository.jpa;

import org.erusakov.backend.entities.car.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<CarEntity, Long> {
    Optional<CarEntity> findByName(String name);
}


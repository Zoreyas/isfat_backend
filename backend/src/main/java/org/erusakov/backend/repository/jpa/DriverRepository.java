package org.erusakov.backend.repository.jpa;

import org.erusakov.backend.entities.user.DriverEntity;
import org.erusakov.backend.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

    Optional<DriverEntity> findByUser_Id(Long id);

    boolean existsByUser(UserEntity user);

    void deleteByUser(UserEntity user);
}

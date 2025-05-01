package org.erusakov.backend.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.erusakov.backend.controller.request.user.CreateDriverRequest;
import org.erusakov.backend.entities.user.DriverEntity;
import org.erusakov.backend.entities.user.RoleEntity;
import org.erusakov.backend.entities.user.UserEntity;
import org.erusakov.backend.exceptions.UserNotFoundException;
import org.erusakov.backend.repository.jpa.DriverRepository;
import org.erusakov.backend.repository.jpa.RoleRepository;
import org.erusakov.backend.repository.jpa.UserRepository;
import org.erusakov.backend.service.DriverService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public Long createDriver(CreateDriverRequest request) {
        UserEntity user = userRepository.findById(request.userId())
                .orElseThrow(UserNotFoundException::new);

        if (driverRepository.existsByUser(user)) {
            throw new IllegalStateException("User is already a driver");
        }

        RoleEntity driverRole = roleRepository.findByAuthority("DRIVER")
                .orElseThrow(() -> new IllegalStateException("DRIVER role not found"));

        if (!user.getRoles().contains(driverRole)) {
            user.getRoles().add(driverRole);
            userRepository.save(user);
        }

        DriverEntity driver = DriverEntity.builder()
                .user(user)
                .licenseNumber(request.licenseNumber())
                .phone(request.phone())
                .build();

        DriverEntity savedDriver = driverRepository.save(driver);

        return savedDriver.getId();
    }

    @Override
    public DriverEntity getByIdOrThrow(Long id) {
        return driverRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    @Override
    public void removeDriverRole(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        RoleEntity driverRole = roleRepository.findByAuthority("DRIVER")
                .orElseThrow(() -> new IllegalStateException("DRIVER role not found"));

        user.getRoles().remove(driverRole);
        userRepository.save(user);

        driverRepository.deleteByUser(user);
    }
}
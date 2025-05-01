package org.erusakov.backend.service;

import org.erusakov.backend.controller.request.user.CreateDriverRequest;
import org.erusakov.backend.entities.user.DriverEntity;
import org.springframework.transaction.annotation.Transactional;

public interface DriverService {
    @Transactional
    Long createDriver(CreateDriverRequest request);

    DriverEntity getByIdOrThrow(Long id);

    @Transactional
    void removeDriverRole(Long userId);
}

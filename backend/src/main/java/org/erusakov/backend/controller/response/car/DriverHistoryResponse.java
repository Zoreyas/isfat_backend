package org.erusakov.backend.controller.response.car;

import org.erusakov.backend.entities.car.CarDriverHistory;

import java.time.LocalDateTime;

public record DriverHistoryResponse(
        Long driverId,
        LocalDateTime endedAt,
        boolean isDriving
) {
    public DriverHistoryResponse(CarDriverHistory driverHistory) {
        this(driverHistory.getDriver().getId(),
                driverHistory.getEndedAt(),
                driverHistory.getEndedAt() == null
        );
    }
}

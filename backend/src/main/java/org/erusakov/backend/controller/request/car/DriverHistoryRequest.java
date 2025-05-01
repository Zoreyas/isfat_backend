package org.erusakov.backend.controller.request.car;

import java.time.LocalDateTime;

public record DriverHistoryRequest(
        Long driverId,
        LocalDateTime endedAt
) {
}

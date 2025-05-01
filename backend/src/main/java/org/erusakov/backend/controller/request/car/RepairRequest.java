package org.erusakov.backend.controller.request.car;

import java.time.LocalDateTime;

public record RepairRequest(
        String mechanic,
        String repairDetails,
        LocalDateTime repairedAt
) {
}

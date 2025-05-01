package org.erusakov.backend.controller.response.car;

import org.erusakov.backend.entities.car.CarRepairHistory;

import java.time.LocalDateTime;

public record RepairResponse(
        Long carId,
        String mechanic,
        String repairDetails,
        LocalDateTime repairedAt
) {
    public RepairResponse(CarRepairHistory repair) {
        this(repair.getCar().getId(), repair.getMechanic(), repair.getRepairDetails(), repair.getRepairedAt());
    }
}

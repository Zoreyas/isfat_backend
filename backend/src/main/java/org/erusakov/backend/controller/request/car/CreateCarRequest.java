package org.erusakov.backend.controller.request.car;

public record CreateCarRequest(
        String name,
        Integer integrity,
        Double maxFuelTank,
        Double currentFuel,
        Long maxCapacity,
        Long currentLoad
) {
}

package org.erusakov.backend.controller.response.car;

public record FullCarResponse(
        Long id,
        String name,
        Integer integrity,
        Double maxFuelTank,
        Double currentFuel,
        Long maxCapacity,
        Long currentLoad
) {
}
package org.erusakov.backend.mapper;

import org.erusakov.backend.controller.request.car.CreateCarRequest;
import org.erusakov.backend.controller.request.car.UpdateCarRequest;
import org.erusakov.backend.controller.response.car.FullCarResponse;
import org.erusakov.backend.controller.response.car.ShortCarResponse;
import org.erusakov.backend.entities.car.CarEntity;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public CarEntity fromCreateRequest(CreateCarRequest request) {
        return CarEntity.builder()
                .name(request.name())
                .integrity(request.integrity())
                .maxFuelTank(request.maxFuelTank())
                .currentFuel(request.currentFuel())
                .maxCapacity(request.maxCapacity())
                .currentLoad(request.currentLoad())
                .build();
    }

    public void updateEntity(CarEntity entity, UpdateCarRequest request) {
        entity.setName(request.name());
        entity.setIntegrity(request.integrity());
        entity.setMaxFuelTank(request.maxFuelTank());
        entity.setCurrentFuel(request.currentFuel());
        entity.setMaxCapacity(request.maxCapacity());
        entity.setCurrentLoad(request.currentLoad());
    }

    public ShortCarResponse toShortResponse(CarEntity entity) {
        return new ShortCarResponse(
                entity.getId(),
                entity.getName(),
                entity.getIntegrity()
        );
    }

    public FullCarResponse toFullResponse(CarEntity entity) {
        return new FullCarResponse(
                entity.getId(),
                entity.getName(),
                entity.getIntegrity(),
                entity.getMaxFuelTank(),
                entity.getCurrentFuel(),
                entity.getMaxCapacity(),
                entity.getCurrentLoad()
        );
    }
}
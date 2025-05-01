package org.erusakov.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.erusakov.backend.controller.request.car.*;
import org.erusakov.backend.controller.response.car.*;
import org.erusakov.backend.entities.car.CarDriverHistory;
import org.erusakov.backend.entities.car.CarEntity;
import org.erusakov.backend.entities.car.CarNote;
import org.erusakov.backend.entities.car.CarRepairHistory;
import org.erusakov.backend.entities.user.DriverEntity;
import org.erusakov.backend.mapper.CarMapper;
import org.erusakov.backend.repository.jpa.CarRepository;
import org.erusakov.backend.repository.jpa.DriverRepository;
import org.erusakov.backend.service.CarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final DriverRepository driverRepository;
    private final CarMapper carMapper;

    @Override
    public List<ShortCarResponse> findAll() {
        return carRepository.findAll().stream()
                .map(carMapper::toShortResponse)
                .toList();
    }

    @Override
    public FullCarResponse findById(Long id) {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
        return carMapper.toFullResponse(car);
    }

    @Override
    public FullCarResponse findByName(String name) {
        CarEntity car = carRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with name: " + name));
        return carMapper.toFullResponse(car);
    }

    @Transactional
    @Override
    public List<RepairResponse> findRepairsById(Long id) {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
        return car.getRepairHistory()
                .stream()
                .map(RepairResponse::new)
                .toList();
    }

    @Transactional
    @Override
    public List<DriverHistoryResponse> findDriversById(Long id) {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
        return car.getDriverHistory()
                .stream()
                .map(DriverHistoryResponse::new)
                .toList();
    }

    @Transactional
    @Override
    public List<NoteResponse> findNotesById(Long id) {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
        return car.getNotes()
                .stream()
                .map(NoteResponse::new)
                .toList();
    }

    @Transactional
    @Override
    public Long create(CreateCarRequest request) {
        CarEntity car = carMapper.fromCreateRequest(request);
        return carRepository.save(car).getId();
    }

    @Transactional
    @Override
    public void update(Long id, UpdateCarRequest request) {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
        carMapper.updateEntity(car, request);
        carRepository.save(car);
    }

    @Transactional
    @Override
    public void updateRepairs(Long id, RepairRequest request) {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
        car.getRepairHistory().add(
                new CarRepairHistory(car, request.mechanic(), request.repairDetails(), request.repairedAt()
                ));
        carRepository.save(car);
    }

    @Transactional
    @Override
    public void updateDrivers(Long id, DriverHistoryRequest request) {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
        DriverEntity driver = driverRepository.findByUser_Id(request.driverId())
                .orElseThrow(() -> new IllegalArgumentException("Driver not found with id: " + request.driverId()));
        car.getDriverHistory().add(new CarDriverHistory(car, driver, request.endedAt()));
        carRepository.save(car);
    }

    @Transactional
    @Override
    public void updateNotes(Long id, CarNoteRequest request) {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + id));
        car.getNotes().add(new CarNote(car, request.note()));
        carRepository.save(car);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!carRepository.existsById(id)) {
            throw new IllegalArgumentException("Car not found with id: " + id);
        }
        carRepository.deleteById(id);
    }
}

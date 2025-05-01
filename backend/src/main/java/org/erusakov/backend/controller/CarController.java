package org.erusakov.backend.controller;

import lombok.RequiredArgsConstructor;
import org.erusakov.backend.controller.request.car.*;
import org.erusakov.backend.controller.response.car.*;
import org.erusakov.backend.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<ShortCarResponse>> findAll() {
        return ResponseEntity.ok(carService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullCarResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<FullCarResponse> findByName(@PathVariable String name) {
        return ResponseEntity.ok(carService.findByName(name));
    }

    @GetMapping("/repairs/{id}")
    public ResponseEntity<List<RepairResponse>> findRepairsById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.findRepairsById(id));
    }

    @GetMapping("/drivers/{id}")
    public ResponseEntity<List<DriverHistoryResponse>> findDriversById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.findDriversById(id));
    }

    @GetMapping("/notes/{id}")
    public ResponseEntity<List<NoteResponse>> findNotesById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.findNotesById(id));
    }

    @PostMapping
    public ResponseEntity<Long> createCar(@RequestBody CreateCarRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.create(request));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateCar(@PathVariable Long id, @RequestBody UpdateCarRequest request) {
        carService.update(id, request);
    }

    @PatchMapping("/repairs/{id}")
    public void findRepairsById(@PathVariable Long id, @RequestBody RepairRequest request) {
        carService.updateRepairs(id, request);
    }

    @PatchMapping("/drivers/{id}")
    public void findDriversById(@PathVariable Long id, @RequestBody DriverHistoryRequest request) {
        carService.updateDrivers(id, request);
    }

    @PatchMapping("/notes/{id}")
    public void findNotesById(@PathVariable Long id, @RequestBody CarNoteRequest request) {
        carService.updateNotes(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.delete(id);
    }


}


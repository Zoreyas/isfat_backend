package org.erusakov.backend.service;

import org.erusakov.backend.controller.request.car.*;
import org.erusakov.backend.controller.response.car.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CarService {

    List<ShortCarResponse> findAll();

    FullCarResponse findById(Long id);

    FullCarResponse findByName(String name);

    List<RepairResponse> findRepairsById(Long id);

    List<DriverHistoryResponse> findDriversById(Long id);

    List<NoteResponse> findNotesById(Long id);

    @Transactional
    Long create(CreateCarRequest request);

    @Transactional
    void update(Long id, UpdateCarRequest request);

    void updateRepairs(Long id, RepairRequest request);

    void updateDrivers(Long id, DriverHistoryRequest request);

    void updateNotes(Long id, CarNoteRequest request);

    @Transactional
    void delete(Long id);

}

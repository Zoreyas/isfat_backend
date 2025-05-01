package org.erusakov.backend.controller.response.car;

import org.erusakov.backend.entities.car.CarNote;

public record NoteResponse(
        Long id,
        String note
) {
    public NoteResponse(CarNote note) {
        this(note.getId(), note.getNote());
    }
}

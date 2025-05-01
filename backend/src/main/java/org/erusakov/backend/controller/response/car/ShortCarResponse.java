package org.erusakov.backend.controller.response.car;

public record ShortCarResponse(
        Long id,
        String name,
        Integer integrity
) {
}
package org.erusakov.backend.controller.response.user;

public record DriverResponse(
        Long userId,
        String phone,
        String licenseNumber
) {
}

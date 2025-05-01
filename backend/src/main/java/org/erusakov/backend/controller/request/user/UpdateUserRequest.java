package org.erusakov.backend.controller.request.user;

public record UpdateUserRequest(
        String name,
        String surname) {
}

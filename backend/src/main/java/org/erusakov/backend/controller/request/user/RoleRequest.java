package org.erusakov.backend.controller.request.user;

import jakarta.validation.constraints.NotBlank;

public record RoleRequest(@NotBlank String name) {
}

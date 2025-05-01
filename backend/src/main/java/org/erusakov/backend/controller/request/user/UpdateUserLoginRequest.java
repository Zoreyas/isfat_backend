package org.erusakov.backend.controller.request.user;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserLoginRequest(@NotBlank String username) {
}

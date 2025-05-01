package org.erusakov.backend.controller.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserPasswordRequest(@NotBlank @Size(min = 8) String password) {
}

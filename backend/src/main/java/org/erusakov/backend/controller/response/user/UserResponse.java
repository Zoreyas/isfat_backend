package org.erusakov.backend.controller.response.user;

import java.util.List;

public record UserResponse(
        Long id,
        String username,
        String email,
        String fullName,
        List<String> roles
) {
}

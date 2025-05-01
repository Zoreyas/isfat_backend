package org.erusakov.backend.controller.request.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record CreateDriverRequest(

        @NotNull Long userId,

        @Pattern(regexp = "^[A-Z0-9]{8,20}$", message = "License number must be 8-20 alphanumeric characters")
        String licenseNumber,

        @Size(max = 20, message = "Phone number too long")
        @Pattern(regexp = "^\\+?[0-9\\s-]{10,15}$", message = "Invalid phone number format")
        String phone

) {
}

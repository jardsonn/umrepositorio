package com.gruposuporte.projetosuporte.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank @Size(min = 3, max = 20) String name,

        @NotBlank @Size(min = 3, max = 20) String username,
        @NotBlank @Size(max = 50) @Email String email,
        @NotBlank @Size(min = 6, max = 40) String password,
        @NotBlank @Size(min = 6, max = 40) String passwordConfirm
) {
}

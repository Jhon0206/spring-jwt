package dev.jhon0206.springjwt.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileRequest(
    @NotBlank String name,
    @NotBlank String lastname) {
}

package dev.jhon0206.springjwt.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
    @NotBlank String name,
    @NotBlank String lastname,
    @NotBlank @Email String email,
    @NotBlank @Size(min = 8) String password) {
}

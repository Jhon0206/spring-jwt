package dev.jhon0206.springjwt.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
    @NotBlank String name,
    @NotBlank @JsonProperty ("last_name") String lastName,
    @NotBlank @Email String email,
    @NotBlank @Size(min = 8) String password) {
}

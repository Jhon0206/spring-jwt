package dev.jhon0206.springjwt.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileRequest(
    @NotBlank String name,
    @NotBlank @JsonProperty("last_name") String lastname) {
}

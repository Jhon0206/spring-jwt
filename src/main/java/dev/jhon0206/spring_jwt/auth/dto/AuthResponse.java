package dev.jhon0206.spring_jwt.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse(
  @JsonProperty("access_token") String accessToken,
  @JsonProperty("refresh_token") String refreshToken
) { }

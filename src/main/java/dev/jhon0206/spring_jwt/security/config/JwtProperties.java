package dev.jhon0206.spring_jwt.security.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import io.jsonwebtoken.security.Keys;

@ConfigurationProperties(prefix = "application.jwt")
public record JwtProperties(String secretKey, String tokenPrefix,
    Integer tokenExpirationAfterDays, Integer refreshTokenExpirationAfterDays) {

  public long getTokenExpirationInMillis() {
    return tokenExpirationAfterDays * 24L * 60 * 60 * 1000;
  }

  public long getRefreshTokenExpirationInMillis() {
    return refreshTokenExpirationAfterDays * 24L * 60 * 60 * 1000;
  }

  public SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }
}

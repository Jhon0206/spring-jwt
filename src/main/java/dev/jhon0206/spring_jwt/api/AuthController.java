package dev.jhon0206.spring_jwt.api;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.jhon0206.spring_jwt.services.AuthService;
import dev.jhon0206.spring_jwt.util.AuthRequest;
import dev.jhon0206.spring_jwt.util.AuthResponse;
import dev.jhon0206.spring_jwt.util.RefreshTokenRequest;
import dev.jhon0206.spring_jwt.util.RegisterRequest;
import dev.jhon0206.spring_jwt.util.RegisterResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(
      @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthResponse> authenticate(
      @RequestBody AuthRequest request) {
    return ResponseEntity.ok(authService.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(
      @RequestBody RefreshTokenRequest request) {
    try {
      return ResponseEntity.ok(authService.refreshToken(request));
    } catch (RuntimeException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(Map.of("Error", ex.getMessage()));
    }
  }

}

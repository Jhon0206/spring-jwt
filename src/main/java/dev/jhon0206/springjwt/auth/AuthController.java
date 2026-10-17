package dev.jhon0206.springjwt.auth;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.jhon0206.springjwt.auth.dto.AuthRequest;
import dev.jhon0206.springjwt.auth.dto.AuthResponse;
import dev.jhon0206.springjwt.util.RefreshTokenRequest;
import dev.jhon0206.springjwt.util.RegisterRequest;
import dev.jhon0206.springjwt.util.RegisterResponse;

@RestController
@RequestMapping("auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

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

package dev.jhon0206.spring_jwt.services;

import dev.jhon0206.spring_jwt.entities.User;
import dev.jhon0206.spring_jwt.repositories.UserRepository;
import dev.jhon0206.spring_jwt.util.AuthRequest;
import dev.jhon0206.spring_jwt.util.AuthResponse;
import dev.jhon0206.spring_jwt.util.RefreshTokenRequest;
import dev.jhon0206.spring_jwt.util.RegisterRequest;
import dev.jhon0206.spring_jwt.util.RegisterResponse;
import dev.jhon0206.spring_jwt.util.RolesEnum;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final TokenService tokenService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public RegisterResponse register(RegisterRequest request) {
    if (userRepository.findByEmail(request.email()).isPresent()) {
      return new RegisterResponse(request.email(), "WARNING", "User already registered.");
    }
    var user = User.builder()
        .name(request.name())
        .lastName(request.lastname())
        .email(request.email())
        .pass(passwordEncoder.encode(request.password()))
        .role(RolesEnum.USER)
        .build();
    userRepository.save(user).getEmail();
    return new RegisterResponse(request.email(), "INFORMATION", "New user registered.");
  }

  public AuthResponse authenticate(AuthRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.password()));
    var user = userRepository.findByEmail(request.email()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    tokenService.saveTokens(user,jwtToken,refreshToken);
    return new AuthResponse(jwtToken, refreshToken);
  }

  public AuthResponse refreshToken(RefreshTokenRequest request) {
    String userEmail = jwtService.extractUsername(request.refreshToken());
    if (userEmail != null) {
      var user = userRepository.findByEmail(userEmail).orElseThrow();
      if (jwtService.isTokenValid(request.refreshToken(), user)) {
        var accessToken = jwtService.generateToken(user);
        return new AuthResponse(accessToken, request.refreshToken());
      }
    }
    throw new RuntimeException("INVALID Refresh Token");
  }
}

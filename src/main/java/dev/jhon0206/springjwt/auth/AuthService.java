package dev.jhon0206.springjwt.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.jhon0206.springjwt.auth.dto.AuthRequest;
import dev.jhon0206.springjwt.auth.dto.AuthResponse;
import dev.jhon0206.springjwt.security.model.CustomUserDetails;
import dev.jhon0206.springjwt.security.service.JwtService;
import dev.jhon0206.springjwt.user.RolesEnum;
import dev.jhon0206.springjwt.user.User;
import dev.jhon0206.springjwt.user.UserRepository;
import dev.jhon0206.springjwt.util.RefreshTokenRequest;
import dev.jhon0206.springjwt.util.RegisterRequest;
import dev.jhon0206.springjwt.util.RegisterResponse;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

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
    var customUserDetail = new CustomUserDetails(user);
    var jwtToken = jwtService.generateAccessToken(customUserDetail);
    var refreshToken = jwtService.generateRefreshToken(customUserDetail);
    return new AuthResponse(jwtToken, refreshToken);
  }

  public AuthResponse refreshToken(RefreshTokenRequest request) {
    String userEmail = jwtService.extractUsername(request.refreshToken());
    if (userEmail != null) {
      var user = userRepository.findByEmail(userEmail).orElseThrow();
      var customUserDetail = new CustomUserDetails(user);
      if (jwtService.isTokenValid(request.refreshToken(), customUserDetail)) {
        var accessToken = jwtService.generateAccessToken(customUserDetail);
        return new AuthResponse(accessToken, request.refreshToken());
      }
    }
    throw new RuntimeException("INVALID Refresh Token");
  }
}

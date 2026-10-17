package dev.jhon0206.springjwt.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.jhon0206.springjwt.auth.dto.AuthRequest;
import dev.jhon0206.springjwt.auth.dto.AuthResponse;
import dev.jhon0206.springjwt.auth.dto.RefreshTokenRequest;
import dev.jhon0206.springjwt.exception.JwtValidationException;
import dev.jhon0206.springjwt.exception.UserNotFoundException;
import dev.jhon0206.springjwt.security.model.CustomUserDetails;
import dev.jhon0206.springjwt.security.service.JwtService;
import dev.jhon0206.springjwt.user.UserRepository;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }
  
  @Transactional (readOnly = true)
  public AuthResponse authenticate(AuthRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.password()));
    var user = userRepository.findByEmail(request.email()).orElseThrow();
    var customUserDetail = new CustomUserDetails(user);
    var jwtToken = jwtService.generateAccessToken(customUserDetail);
    var refreshToken = jwtService.generateRefreshToken(customUserDetail);
    return new AuthResponse(jwtToken, refreshToken);
  }

  @Transactional (readOnly = true)
  public AuthResponse refreshToken(RefreshTokenRequest request) {
    String refreshToken = request.refreshToken();
    String userEmail = jwtService.extractUsername(refreshToken);

    if (userEmail != null) {
      var user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException(userEmail));
      var userDetails = new CustomUserDetails(user);

      if (jwtService.isTokenValid(refreshToken, userDetails) && !jwtService.isAccessToken(refreshToken)) {
        var newAccessToken = jwtService.generateAccessToken(userDetails);
        return new AuthResponse(newAccessToken, refreshToken);
      }
    }
    throw new JwtValidationException("Invalid or incorrect token type");
  }
}

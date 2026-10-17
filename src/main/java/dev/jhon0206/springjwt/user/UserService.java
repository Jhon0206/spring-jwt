package dev.jhon0206.springjwt.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.jhon0206.springjwt.exception.UserAlreadyExistsException;
import dev.jhon0206.springjwt.exception.UserNotFoundException;
import dev.jhon0206.springjwt.user.dto.ChangePasswordRequest;
import dev.jhon0206.springjwt.user.dto.RegisterUserRequest;
import dev.jhon0206.springjwt.user.dto.RegisterUserResponse;
import dev.jhon0206.springjwt.user.dto.UpdateProfileRequest;
import dev.jhon0206.springjwt.user.dto.UserResponse;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional(readOnly = true)
  public UserResponse getMyProfile(String email) {
    User user = repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
    return new UserResponse(
        user.getId(),
        user.getName(),
        user.getLastName(),
        user.getEmail(),
        user.getRole(),
        user.getStatus());
  }

  @Transactional
  public void updateMyProfile(String email, UpdateProfileRequest request) {
    var user = repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
    user.setName(request.name());
    user.setLastName(request.lastname());
    repository.save(user);
  }

  @Transactional
  public void updateMyPassword(String email, ChangePasswordRequest request) {
    var user = repository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
    if (!passwordEncoder.matches(request.currentPassword(), user.getPass())) {
      throw new IllegalArgumentException("Old password does not match.");
    }
    user.setPass(passwordEncoder.encode(request.newPassword()));
    repository.save(user);
  }

  @Transactional
  public RegisterUserResponse register(RegisterUserRequest request) {
    if (repository.findByEmail(request.email()).isPresent()) {
      throw new UserAlreadyExistsException(String.format("User with email %s is already registered.", request.email()));
    }
    var user = User.builder()
        .name(request.name())
        .lastName(request.lastName())
        .email(request.email())
        .pass(passwordEncoder.encode(request.password()))
        .role(RolesEnum.USER)
        .status(StatusEnum.PENDING)
        .build();

    repository.save(user);
    return new RegisterUserResponse(user.getEmail(),
        "Registration successful. Your account is pending administrator approval.");
  }
}

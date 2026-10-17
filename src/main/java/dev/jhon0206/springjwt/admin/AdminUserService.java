package dev.jhon0206.springjwt.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.jhon0206.springjwt.exception.UserAlreadyExistsException;
import dev.jhon0206.springjwt.exception.UserNotFoundException;
import dev.jhon0206.springjwt.user.RolesEnum;
import dev.jhon0206.springjwt.user.StatusEnum;
import dev.jhon0206.springjwt.user.User;
import dev.jhon0206.springjwt.user.UserRepository;
import dev.jhon0206.springjwt.user.dto.RegisterUserRequest;
import dev.jhon0206.springjwt.user.dto.UserResponse;
import dev.jhon0206.springjwt.user.mapper.UserMapper;

@Service
public class AdminUserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AdminUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional(readOnly = true)
  public List<UserResponse> getAllUsers(StatusEnum status) {
    List<User> users;

    if (status != null) {
      users = userRepository.findByStatus(status);
    } else {
      users = userRepository.findAll();
    }
    return users.stream()
        .map(UserMapper::mapToResponse)
        .collect(Collectors.toList());
  }

  @Transactional
  public UserResponse createUser(RegisterUserRequest request) {
    if (userRepository.findByEmail(request.email()).isPresent()) {
      throw new UserAlreadyExistsException(request.email());
    }

    User user = User.builder()
        .name(request.name())
        .lastName(request.lastName())
        .email(request.email())
        .pass(passwordEncoder.encode(request.password()))
        .role(RolesEnum.USER)
        .status(StatusEnum.APPROVED)
        .build();

    userRepository.save(user);
    return UserMapper.mapToResponse(user);
  }

  @Transactional
  public void approveUser(Integer userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException());
    user.setStatus(StatusEnum.APPROVED);
    userRepository.save(user);
  }

  @Transactional
  public void deactivateUser(Integer userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException());
    user.setStatus(StatusEnum.INACTIVE);
    userRepository.save(user);
  }

}

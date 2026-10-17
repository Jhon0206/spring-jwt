package dev.jhon0206.springjwt.user.mapper;

import dev.jhon0206.springjwt.user.User;
import dev.jhon0206.springjwt.user.dto.RegisterUserRequest;
import dev.jhon0206.springjwt.user.dto.UserResponse;

public class UserMapper {

  public static UserResponse mapToResponse(User user) {
    return new UserResponse(
        user.getId(),
        user.getName(),
        user.getLastName(),
        user.getEmail(),
        user.getRole(),
        user.getStatus());
  }

  public static User mapToEntity(RegisterUserRequest userRequest) {
    User user = new User();
    user.setName(userRequest.name());
    user.setLastName(userRequest.lastName());
    user.setEmail(userRequest.email());
    return user;
  }
}

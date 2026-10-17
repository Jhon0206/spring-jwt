package dev.jhon0206.springjwt.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.jhon0206.springjwt.security.model.CustomUserDetails;
import dev.jhon0206.springjwt.user.dto.ChangePasswordRequest;
import dev.jhon0206.springjwt.user.dto.RegisterUserRequest;
import dev.jhon0206.springjwt.user.dto.RegisterUserResponse;
import dev.jhon0206.springjwt.user.dto.UserResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<RegisterUserResponse> register(@Valid @RequestBody RegisterUserRequest request) {

    RegisterUserResponse response = userService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/me")
  public ResponseEntity<UserResponse> getMyProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
    UserResponse response = userService.getMyProfile(userDetails.getUsername());
    return ResponseEntity.ok(response);
  }



}

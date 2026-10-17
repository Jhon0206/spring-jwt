package dev.jhon0206.springjwt.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.jhon0206.springjwt.user.StatusEnum;
import dev.jhon0206.springjwt.user.dto.RegisterUserRequest;
import dev.jhon0206.springjwt.user.dto.UserResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admins")
public class AdminUserController {

  private final AdminUserService service;

  public AdminUserController(AdminUserService adminUserService) {
    this.service = adminUserService;
  }

  @GetMapping
  public ResponseEntity<List<UserResponse>> getAllUsers(
      @RequestParam(required = false) StatusEnum status) {

    List<UserResponse> users = service.getAllUsers(status);
    return ResponseEntity.ok(users);
  }

  // 2. Crear un usuario directamente (Ej. Crear otro administrador)
  @PostMapping
  public ResponseEntity<UserResponse> createUser(
      @Valid @RequestBody RegisterUserRequest request) {

    UserResponse response = service.createUser(request);
    // Retorna HTTP 201 Created
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // 3. Aprobar un usuario pendiente
  @PatchMapping("/{userId}/approve")
  public ResponseEntity<Void> approveUser(@PathVariable Integer userId) {
    service.approveUser(userId);
    return ResponseEntity.noContent().build();
  }

  // 4. Dar de baja a un usuario (Soft Delete)
  @PatchMapping("/{userId}/deactivate")
  public ResponseEntity<Void> deactivateUser(@PathVariable Integer userId) {
    service.deactivateUser(userId);
    return ResponseEntity.noContent().build();
  }
}

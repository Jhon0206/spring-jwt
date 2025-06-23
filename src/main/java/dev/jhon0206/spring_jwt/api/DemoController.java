package dev.jhon0206.spring_jwt.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.jhon0206.spring_jwt.entities.Token;
import dev.jhon0206.spring_jwt.repositories.UserRepository;
import dev.jhon0206.spring_jwt.services.TokenService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("demo")
@RequiredArgsConstructor
public class DemoController {

  private final TokenService tokenService;
  private final UserRepository userService;

  @GetMapping("user")
  public String userMethod() {
    return "Looged as user or admin";
  }

  @GetMapping("admin")
  public String adminMethod() {
    return "Looged as admin";
  }

  @GetMapping("admin/get-tokens/{id}")
  public List<Token> adminMethodGetTokensByUser(@PathVariable Integer id) throws Exception {
    return tokenService.getTokensByUser(userService.findById(id).orElseThrow());
  }
}

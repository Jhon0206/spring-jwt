package dev.jhon0206.spring_jwt.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("info")
public class InfoController {

  @GetMapping()
  public String getMethodName() {
      return "Spring Boot App with JWT";
  }
  
}

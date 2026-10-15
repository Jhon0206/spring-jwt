package dev.jhon0206.spring_jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BadCredentialsException.class)
  public ProblemDetail handleBadCredentials(BadCredentialsException ex) {
    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    problemDetail.setTitle("Authentication Failed");
    return problemDetail;
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleGenericException(Exception ex) {
    return switch (ex) {
      case UserNotFoundException e -> ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
      case JwtValidationException e -> ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, e.getMessage());
      default -> ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    };
  }
}
package dev.jhon0206.spring_jwt.exception;

public class JwtValidationException extends RuntimeException {

  public JwtValidationException() {
    super("Invalid token structure");
  }

  public JwtValidationException(String message) {
    super(message);
  }

}

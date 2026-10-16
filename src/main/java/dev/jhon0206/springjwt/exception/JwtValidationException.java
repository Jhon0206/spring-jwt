package dev.jhon0206.springjwt.exception;

public class JwtValidationException extends RuntimeException {

  public JwtValidationException() {
    super("Invalid token structure");
  }

  public JwtValidationException(String message) {
    super(message);
  }

}

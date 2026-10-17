package dev.jhon0206.springjwt.exception;

import java.text.MessageFormat;

public class UserAlreadyExistsException extends RuntimeException {

  public UserAlreadyExistsException() {
    super("User already exists");
  }

  public UserAlreadyExistsException(String email) {
    super(MessageFormat.format("User with email {0} already exists", email));
  }

}

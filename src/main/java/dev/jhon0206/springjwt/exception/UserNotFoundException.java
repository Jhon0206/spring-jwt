package dev.jhon0206.springjwt.exception;

import java.text.MessageFormat;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException() {
    super("User not found");
  }

  public UserNotFoundException(String email) {
    super(MessageFormat.format("User with email {0} not found", email));
  }

}

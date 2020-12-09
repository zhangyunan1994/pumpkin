package org.example.pumpkin;

public class VerifyException extends RuntimeException {

  public VerifyException() {
  }

  public VerifyException(String message) {
    super(message);
  }

  public VerifyException(Throwable cause) {
    super(cause);
  }

  public VerifyException(String message, Throwable cause) {
    super(message, cause);
  }
}

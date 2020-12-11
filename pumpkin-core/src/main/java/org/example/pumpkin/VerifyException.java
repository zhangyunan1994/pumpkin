package org.example.pumpkin;

/**
 * The type Verify exception.
 */
public class VerifyException extends RuntimeException {

  /**
   * Instantiates a new Verify exception.
   */
  public VerifyException() {
  }

  /**
   * Instantiates a new Verify exception.
   *
   * @param message the message
   */
  public VerifyException(String message) {
    super(message);
  }

  /**
   * Instantiates a new Verify exception.
   *
   * @param cause the cause
   */
  public VerifyException(Throwable cause) {
    super(cause);
  }

  /**
   * Instantiates a new Verify exception.
   *
   * @param message the message
   * @param cause   the cause
   */
  public VerifyException(String message, Throwable cause) {
    super(message, cause);
  }
}

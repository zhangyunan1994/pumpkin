package org.example.pumpkin;

import static org.example.pumpkin.lang.StrUtil.lenientFormat;

import java.util.function.Predicate;


public final class Verify {

  private Verify() {
  }

  private static <T> void validate(T value, Predicate<T> predicate) {
    if (!predicate.test(value)) {
      throw new VerifyException();
    }
  }

  private static <T> void validate(T value, Predicate<T> predicate, String errorMessage) {
    if (!predicate.test(value)) {
      throw new VerifyException(errorMessage);
    }
  }

  public static void verify(boolean expression) {
    if (!expression) {
      throw new VerifyException();
    }
  }

  public static void verify(
      boolean expression,
      String errorMessageTemplate,
      Object... errorMessageArgs) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, errorMessageArgs));
    }
  }

  public static void verify(boolean expression, String errorMessageTemplate, char p1) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void verify(boolean expression, String errorMessageTemplate, int p1) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void verify(boolean expression, String errorMessageTemplate, long p1) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, Object p1) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, char p1, char p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, int p1, char p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, long p1, char p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, Object p1, char p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, char p1, int p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, int p1, int p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, long p1, int p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, Object p1, int p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, char p1, long p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, int p1, long p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, long p1, long p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, Object p1, long p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, char p1, Object p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, int p1, Object p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression, String errorMessageTemplate, long p1, Object p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression,
      String errorMessageTemplate,
      Object p1,
      Object p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void verify(
      boolean expression,
      String errorMessageTemplate,
      Object p1,
      Object p2,
      Object p3) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2, p3));
    }
  }

  public static void verify(
      boolean expression,
      String errorMessageTemplate,
      Object p1,
      Object p2,
      Object p3,
      Object p4) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2, p3, p4));
    }
  }

  public static <T> T verifyNotNull(T reference) {
    return verifyNotNull(reference, "expected a non-null reference");
  }

  public static <T> T verifyNotNull(
      T reference,
      String errorMessageTemplate,
      Object... errorMessageArgs) {
    verify(reference != null, errorMessageTemplate, errorMessageArgs);
    return reference;
  }

}

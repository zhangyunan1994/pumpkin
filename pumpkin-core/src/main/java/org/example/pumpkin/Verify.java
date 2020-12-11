package org.example.pumpkin;

import static org.example.pumpkin.lang.StrUtil.lenientFormat;

import java.util.function.Predicate;


/**
 * The type Verify.
 *
 * @author zhangyunan
 */
public final class Verify {

  private Verify() {
  }

  /**
   * Validate.
   *
   * @param <T>       the type parameter
   * @param value     the value
   * @param predicate the predicate
   */
  public static <T> void validate(T value, Predicate<T> predicate) {
    if (!predicate.test(value)) {
      throw new VerifyException();
    }
  }

  /**
   * Validate.
   *
   * @param <T>          the type parameter
   * @param value        the value
   * @param predicate    the predicate
   * @param errorMessage the error message
   */
  public static <T> void validate(T value, Predicate<T> predicate, String errorMessage) {
    if (!predicate.test(value)) {
      throw new VerifyException(errorMessage);
    }
  }

  /**
   * Verify.
   *
   * @param expression the expression
   */
  public static void verify(boolean expression) {
    if (!expression) {
      throw new VerifyException();
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param errorMessageArgs     the error message args
   */
  public static void verify(
      boolean expression,
      String errorMessageTemplate,
      Object... errorMessageArgs) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, errorMessageArgs));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   */
  public static void verify(boolean expression, String errorMessageTemplate, char p1) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   */
  public static void verify(boolean expression, String errorMessageTemplate, int p1) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   */
  public static void verify(boolean expression, String errorMessageTemplate, long p1) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, Object p1) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, char p1, char p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, int p1, char p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, long p1, char p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, Object p1, char p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, char p1, int p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, int p1, int p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, long p1, int p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, Object p1, int p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, char p1, long p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, int p1, long p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, long p1, long p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, Object p1, long p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, char p1, Object p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, int p1, Object p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression, String errorMessageTemplate, long p1, Object p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   */
  public static void verify(
      boolean expression,
      String errorMessageTemplate,
      Object p1,
      Object p2) {
    if (!expression) {
      throw new VerifyException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   * @param p3                   the p 3
   */
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

  /**
   * Verify.
   *
   * @param expression           the expression
   * @param errorMessageTemplate the error message template
   * @param p1                   the p 1
   * @param p2                   the p 2
   * @param p3                   the p 3
   * @param p4                   the p 4
   */
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

  /**
   * Verify not null t.
   *
   * @param <T>       the type parameter
   * @param reference the reference
   * @return the t
   */
  public static <T> T verifyNotNull(T reference) {
    return verifyNotNull(reference, "expected a non-null reference");
  }

  /**
   * Verify not null t.
   *
   * @param <T>                  the type parameter
   * @param reference            the reference
   * @param errorMessageTemplate the error message template
   * @param errorMessageArgs     the error message args
   * @return the t
   */
  public static <T> T verifyNotNull(
      T reference,
      String errorMessageTemplate,
      Object... errorMessageArgs) {
    verify(reference != null, errorMessageTemplate, errorMessageArgs);
    return reference;
  }

}

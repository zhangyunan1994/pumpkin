package org.example.pumpkin;

import static org.example.pumpkin.lang.StrUtil.lenientFormat;

public final class Preconditions {

  private Preconditions() {
  }

  public static void checkArgument(boolean expression) {
    if (!expression) {
      throw new IllegalArgumentException();
    }
  }

  public static void checkArgument(boolean expression, Object errorMessage) {
    if (!expression) {
      throw new IllegalArgumentException(String.valueOf(errorMessage));
    }
  }

  public static void checkArgument(
      boolean expression,
      String errorMessageTemplate,
      Object... errorMessageArgs) {
    if (!expression) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, errorMessageArgs));
    }
  }

  public static void checkArgument(boolean b, String errorMessageTemplate, char p1) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void checkArgument(boolean b, String errorMessageTemplate, int p1) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void checkArgument(boolean b, String errorMessageTemplate, long p1) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, Object p1) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, char p1, char p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, char p1, int p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, char p1, long p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, char p1, Object p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, int p1, char p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, int p1, int p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, int p1, long p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, int p1, Object p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, long p1, char p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, long p1, int p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, long p1, long p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, long p1, Object p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, Object p1, char p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, Object p1, int p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, Object p1, long p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b, String errorMessageTemplate, Object p1, Object p2) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkArgument(
      boolean b,
      String errorMessageTemplate,
      Object p1,
      Object p2,
      Object p3) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2, p3));
    }
  }

  public static void checkArgument(
      boolean b,
      String errorMessageTemplate,
      Object p1,
      Object p2,
      Object p3,
      Object p4) {
    if (!b) {
      throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2, p3, p4));
    }
  }

  public static void checkState(boolean expression) {
    if (!expression) {
      throw new IllegalStateException();
    }
  }

  public static void checkState(boolean expression, Object errorMessage) {
    if (!expression) {
      throw new IllegalStateException(String.valueOf(errorMessage));
    }
  }

  public static void checkState(
      boolean expression,
      String errorMessageTemplate,
      Object... errorMessageArgs) {
    if (!expression) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, errorMessageArgs));
    }
  }

  public static void checkState(boolean b, String errorMessageTemplate, char p1) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void checkState(boolean b, String errorMessageTemplate, int p1) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void checkState(boolean b, String errorMessageTemplate, long p1) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void checkState(
      boolean b, String errorMessageTemplate, Object p1) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1));
    }
  }

  public static void checkState(
      boolean b, String errorMessageTemplate, char p1, char p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(boolean b, String errorMessageTemplate, char p1, int p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, String errorMessageTemplate, char p1, long p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, String errorMessageTemplate, char p1, Object p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(boolean b, String errorMessageTemplate, int p1, char p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(boolean b, String errorMessageTemplate, int p1, int p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(boolean b, String errorMessageTemplate, int p1, long p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, String errorMessageTemplate, int p1, Object p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, String errorMessageTemplate, long p1, char p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(boolean b, String errorMessageTemplate, long p1, int p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, String errorMessageTemplate, long p1, long p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, String errorMessageTemplate, long p1, Object p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, String errorMessageTemplate, Object p1, char p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, String errorMessageTemplate, Object p1, int p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, String errorMessageTemplate, Object p1, long p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b, String errorMessageTemplate, Object p1, Object p2) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
    }
  }

  public static void checkState(
      boolean b,
      String errorMessageTemplate,
      Object p1,
      Object p2,
      Object p3) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2, p3));
    }
  }

  public static void checkState(
      boolean b,
      String errorMessageTemplate,
      Object p1,
      Object p2,
      Object p3,
      Object p4) {
    if (!b) {
      throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2, p3, p4));
    }
  }

  public static <T extends Object> T checkNotNull(T reference) {
    if (reference == null) {
      throw new NullPointerException();
    }
    return reference;
  }

  public static <T extends Object> T checkNotNull(
      T reference, Object errorMessage) {
    if (reference == null) {
      throw new NullPointerException(String.valueOf(errorMessage));
    }
    return reference;
  }

  public static <T extends Object> T checkNotNull(
      T reference,
      String errorMessageTemplate,
      Object... errorMessageArgs) {
    if (reference == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, errorMessageArgs));
    }
    return reference;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, char p1) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, int p1) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, long p1) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, Object p1) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, char p1, char p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, char p1, int p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, char p1, long p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, char p1, Object p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, int p1, char p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, int p1, int p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, int p1, long p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, int p1, Object p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, long p1, char p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, long p1, int p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, long p1, long p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, long p1, Object p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, Object p1, char p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, Object p1, int p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, Object p1, long p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj, String errorMessageTemplate, Object p1, Object p2) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj,
      String errorMessageTemplate,
      Object p1,
      Object p2,
      Object p3) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2, p3));
    }
    return obj;
  }

  public static <T extends Object> T checkNotNull(
      T obj,
      String errorMessageTemplate,
      Object p1,
      Object p2,
      Object p3,
      Object p4) {
    if (obj == null) {
      throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2, p3, p4));
    }
    return obj;
  }

  /*
   * All recent hotspots (as of 2009) *really* like to have the natural code
   *
   * if (guardExpression) {
   *    throw new BadException(messageExpression);
   * }
   *
   * refactored so that messageExpression is moved to a separate String-returning method.
   *
   * if (guardExpression) {
   *    throw new BadException(badMsg(...));
   * }
   *
   * The alternative natural refactorings into void or Exception-returning methods are much slower.
   * This is a big deal - we're talking factors of 2-8 in microbenchmarks, not just 10-20%. (This is
   * a hotspot optimizer bug, which should be fixed, but that's a separate, big project).
   *
   * The coding pattern above is heavily used in java.util, e.g. in ArrayList. There is a
   * RangeCheckMicroBenchmark in the JDK that was used to test this.
   *
   * But the methods in this class want to throw different exceptions, depending on the args, so it
   * appears that this pattern is not directly applicable. But we can use the ridiculous, devious
   * trick of throwing an exception in the middle of the construction of another exception. Hotspot
   * is fine with that.
   */

  public static int checkElementIndex(int index, int size) {
    return checkElementIndex(index, size, "index");
  }

  public static int checkElementIndex(int index, int size, String desc) {
    // Carefully optimized for execution by hotspot (explanatory comment above)
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
    }
    return index;
  }

  private static String badElementIndex(int index, int size, String desc) {
    if (index < 0) {
      return lenientFormat("%s (%s) must not be negative", desc, index);
    } else if (size < 0) {
      throw new IllegalArgumentException("negative size: " + size);
    } else { // index >= size
      return lenientFormat("%s (%s) must be less than size (%s)", desc, index, size);
    }
  }

  public static int checkPositionIndex(int index, int size) {
    return checkPositionIndex(index, size, "index");
  }

  public static int checkPositionIndex(int index, int size, String desc) {
    // Carefully optimized for execution by hotspot (explanatory comment above)
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(badPositionIndex(index, size, desc));
    }
    return index;
  }

  private static String badPositionIndex(int index, int size, String desc) {
    if (index < 0) {
      return lenientFormat("%s (%s) must not be negative", desc, index);
    } else if (size < 0) {
      throw new IllegalArgumentException("negative size: " + size);
    } else { // index > size
      return lenientFormat("%s (%s) must not be greater than size (%s)", desc, index, size);
    }
  }

  public static void checkPositionIndexes(int start, int end, int size) {
    // Carefully optimized for execution by hotspot (explanatory comment above)
    if (start < 0 || end < start || end > size) {
      throw new IndexOutOfBoundsException(badPositionIndexes(start, end, size));
    }
  }

  private static String badPositionIndexes(int start, int end, int size) {
    if (start < 0 || start > size) {
      return badPositionIndex(start, size, "start index");
    }
    if (end < 0 || end > size) {
      return badPositionIndex(end, size, "end index");
    }
    // end < start
    return lenientFormat("end index (%s) must not be less than start index (%s)", end, start);
  }
}

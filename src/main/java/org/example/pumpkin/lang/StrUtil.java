package org.example.pumpkin.lang;


import static java.util.function.Function.identity;
import static java.util.logging.Level.WARNING;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.example.pumpkin.Preconditions;

/**
 * The type String util.
 *
 * @author zhangyunan
 */
public class StrUtil {

  private static final String NULL_STRING_ERR_MSG = "'value' should be not null.";
  private static final String[] EMPTY_ARRAY = new String[0];

  private StrUtil() {
  }

  public static boolean isNullOrBlank(CharSequence cs) {
    if (cs == null || cs.length() == 0) {
      return true;
    }
    int strLen = cs.length();
    for (int i = 0; i < strLen; ++i) {
      if (!Character.isWhitespace(cs.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  public static boolean isNotBlank(final CharSequence cs) {
    return !isNullOrBlank(cs);
  }

  public static boolean isNullOrEmpty(CharSequence cs) {
    return cs == null || cs.length() == 0;
  }

  public static boolean isNotEmpty(CharSequence cs) {
    return !isNullOrEmpty(cs);
  }


  /**
   * Appends Strings to value
   *
   * @param value   initial String
   * @param appends an array of strings to append
   * @return full String
   */
  public static String append(final String value, final String... appends) {
    return appendArray(value, appends);
  }

  public static String lenientFormat(
      String template, Object... args) {
    template = String.valueOf(template); // null -> "null"

    if (args == null) {
      args = new Object[]{"(Object[])null"};
    } else {
      for (int i = 0; i < args.length; i++) {
        args[i] = lenientToString(args[i]);
      }
    }

    // start substituting the arguments into the '%s' placeholders
    StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
    int templateStart = 0;
    int i = 0;
    while (i < args.length) {
      int placeholderStart = template.indexOf("%s", templateStart);
      if (placeholderStart == -1) {
        break;
      }
      builder.append(template, templateStart, placeholderStart);
      builder.append(args[i++]);
      templateStart = placeholderStart + 2;
    }
    builder.append(template, templateStart, template.length());

    // if we run out of placeholders, append the extra args in square braces
    if (i < args.length) {
      builder.append(" [");
      builder.append(args[i++]);
      while (i < args.length) {
        builder.append(", ");
        builder.append(args[i++]);
      }
      builder.append(']');
    }

    return builder.toString();
  }

  private static String lenientToString(Object o) {
    if (o == null) {
      return "null";
    }
    try {
      return o.toString();
    } catch (Exception e) {
      // Default toString() behavior - see Object.toString()
      String objectToString =
          o.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(o));
      // Logger is created inline with fixed name to avoid forcing Proguard to create another class.
      Logger.getLogger("com.zyndev.hebe.lang.common.base.Strings")
          .log(WARNING, "Exception during lenientFormat for " + objectToString, e);
      return "<" + objectToString + " threw " + e.getClass().getName() + ">";
    }
  }

  /**
   * Append an array of String to value
   *
   * @param value   initial String
   * @param appends an array of strings to append
   * @return full String
   */
  public static String appendArray(final String value, final String[] appends) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    if (appends == null || appends.length == 0) {
      return value;
    }
    StringJoiner joiner = new StringJoiner("");
    for (String append : appends) {
      joiner.add(append);
    }
    return value + joiner.toString();
  }

  /**
   * Get the character at index. This method will take care of negative indexes. The valid value of
   * index is between -(length-1) to (length-1). For values which don't fall under this range
   * Optional.empty will be returned.
   *
   * @param value input value
   * @param index location
   * @return an Optional String if found else empty
   */
  public static Optional<String> at(final String value, int index) {
    if (isNullOrEmpty(value)) {
      return Optional.empty();
    }
    int length = value.length();
    if (index < 0) {
      index = length + index;
    }
    return (index < length && index >= 0) ? Optional.of(String.valueOf(value.charAt(index)))
        : Optional.empty();
  }

  /**
   * Returns an array with strings between start and end.
   *
   * @param value input
   * @param start start
   * @param end   end
   * @return Array containing different parts between start and end.
   */

  public static String[] between(final String value, final String start, final String end) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    Preconditions.checkNotNull(start, "'start' should be not null.");
    Preconditions.checkNotNull(end, "'end' should be not null.");

    String[] parts = value.split(end);
    return Arrays.stream(parts)
        .filter(subPart -> subPart.contains(start))
        .map(subPart -> subPart.substring(subPart.indexOf(start) + start.length()))
        .toArray(String[]::new);
  }

  /**
   * Returns a String array consisting of the characters in the String.
   *
   * @param value input
   * @return character array
   */
  public static String[] chars(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.split("");
  }

  /**
   * Replace consecutive whitespace characters with a single space.
   *
   * @param value input String
   * @return collapsed String
   */
  public static String collapseWhitespace(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.trim().replaceAll("\\s\\s+", " ");
  }

  /**
   * Verifies that the needle is contained in the value. The search is case insensitive
   *
   * @param value  to search
   * @param needle to find
   * @return true if found else false.
   */
  public static boolean contains(final String value, final String needle) {
    return contains(value, needle, false);
  }

  /**
   * Verifies that the needle is contained in the value.
   *
   * @param value         to search
   * @param needle        to find
   * @param caseSensitive true or false
   * @return true if found else false.
   */
  public static boolean contains(final String value, final String needle,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    if (caseSensitive) {
      return value.contains(needle);
    }
    return value.toLowerCase().contains(needle.toLowerCase());
  }

  /**
   * Verifies that all needles are contained in value. The search is case insensitive
   *
   * @param value   input String to search
   * @param needles needles to find
   * @return true if all needles are found else false.
   */
  public static boolean containsAll(final String value, final String[] needles) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return Arrays.stream(needles).allMatch(needle -> contains(value, needle, false));
  }

  /**
   * Verifies that all needles are contained in value
   *
   * @param value         input String to search
   * @param needles       needles to find
   * @param caseSensitive true or false
   * @return true if all needles are found else false.
   */
  public static boolean containsAll(final String value, final String[] needles,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return Arrays.stream(needles).allMatch(needle -> contains(value, needle, caseSensitive));
  }

  /**
   * Verifies that one or more of needles are contained in value. This is case insensitive
   *
   * @param value   input
   * @param needles needles to search
   * @return boolean true if any needle is found else false
   */
  public static boolean containsAny(final String value, final String[] needles) {
    return containsAny(value, needles, false);
  }

  /**
   * Verifies that one or more of needles are contained in value.
   *
   * @param value         input
   * @param needles       needles to search
   * @param caseSensitive true or false
   * @return boolean true if any needle is found else false
   */
  public static boolean containsAny(final String value, final String[] needles,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return Arrays.stream(needles).anyMatch(needle -> contains(value, needle, caseSensitive));
  }

  /**
   * Count the number of times substr appears in value
   *
   * @param value  input
   * @param subStr to search
   * @return count of times substring exists
   */
  public static long countSubstr(final String value, final String subStr) {
    return countSubstr(value, subStr, true, false);
  }

  /**
   * Count the number of times substr appears in value
   *
   * @param value            input
   * @param subStr           search string
   * @param caseSensitive    whether search should be case sensitive
   * @param allowOverlapping boolean to take into account overlapping
   * @return count of times substring exists
   */
  public static long countSubstr(final String value, final String subStr,
      final boolean caseSensitive,
      boolean allowOverlapping) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return countSubstr(caseSensitive ? value : value.toLowerCase(),
        caseSensitive ? subStr : subStr.toLowerCase(),
        allowOverlapping, 0L);
  }

  /**
   * Test if value ends with search. The search is case sensitive.
   *
   * @param value  input string
   * @param search string to search
   * @return true or false
   */
  public static boolean endsWith(final String value, final String search) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return endsWith(value, search, value.length(), true);
  }

  /**
   * Test if value ends with search.
   *
   * @param value         input string
   * @param search        string to search
   * @param caseSensitive true or false
   * @return true or false
   */
  public static boolean endsWith(final String value, final String search,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return endsWith(value, search, value.length(), caseSensitive);
  }

  /**
   * Test if value ends with search.
   *
   * @param value         input string
   * @param search        string to search
   * @param position      position till which you want to search.
   * @param caseSensitive true or false
   * @return true or false
   */
  public static boolean endsWith(final String value, final String search, final int position,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    int remainingLength = position - search.length();
    if (caseSensitive) {
      return value.indexOf(search, remainingLength) > -1;
    }
    return value.toLowerCase().indexOf(search.toLowerCase(), remainingLength) > -1;
  }

  /**
   * Ensures that the value begins with prefix. If it doesn't exist, it's prepended. It is case
   * sensitive.
   *
   * @param value  input
   * @param prefix prefix
   * @return string with prefix if it was not present.
   */
  public static String ensureLeft(final String value, final String prefix) {
    return ensureLeft(value, prefix, true);
  }

  /**
   * Ensures that the value begins with prefix. If it doesn't exist, it's prepended.
   *
   * @param value         input
   * @param prefix        prefix
   * @param caseSensitive true or false
   * @return string with prefix if it was not present.
   */
  public static String ensureLeft(final String value, final String prefix,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    if (caseSensitive) {
      return value.startsWith(prefix) ? value : prefix + value;
    }
    String _value = value.toLowerCase();
    String _prefix = prefix.toLowerCase();
    return _value.startsWith(_prefix) ? value : prefix + value;
  }

  /**
   * Decodes data encoded with MIME base64
   *
   * @param value The data to decode
   * @return decoded data
   */
  public static String base64Decode(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
  }

  /**
   * Encodes data with MIME base64.
   *
   * @param value The data to encode
   * @return The encoded String
   */
  public static String base64Encode(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * Convert binary unicode (16 digits) string to string chars
   *
   * @param value The value to decode
   * @return The decoded String
   */
  public static String binDecode(final String value) {
    return decode(value, 16, 2);
  }

  /**
   * Convert string chars to binary unicode (16 digits)
   *
   * @param value The value to encode
   * @return String in binary format
   */
  public static String binEncode(final String value) {
    return encode(value, 16, 2);
  }

  /**
   * Convert decimal unicode (5 digits) string to string chars
   *
   * @param value The value to decode
   * @return decoded String
   */
  public static String decDecode(final String value) {
    return decode(value, 5, 10);
  }

  /**
   * Convert string chars to decimal unicode (5 digits)
   *
   * @param value The value to encode
   * @return Encoded value
   */
  public static String decEncode(final String value) {
    return encode(value, 5, 10);
  }

  /**
   * Ensures that the value ends with suffix. If it doesn't, it's appended. This operation is case
   * sensitive.
   *
   * @param value  The input String
   * @param suffix The substr to be ensured to be right
   * @return The string which is guarenteed to start with substr
   */
  public static String ensureRight(final String value, final String suffix) {
    return ensureRight(value, suffix, true);
  }

  /**
   * Ensures that the value ends with suffix. If it doesn't, it's appended.
   *
   * @param value         The input String
   * @param suffix        The substr to be ensured to be right
   * @param caseSensitive Use case (in-)sensitive matching for determining if value already ends
   *                      with suffix
   * @return The string which is guarenteed to start with substr
   */
  public static String ensureRight(final String value, final String suffix, boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return endsWith(value, suffix, caseSensitive) ? value : append(value, suffix);
  }

  /**
   * Returns the first n chars of String
   *
   * @param value The input String
   * @param n     Number of chars to return
   * @return The first n chars
   */
  public static Optional<String> first(final String value, final int n) {
    return Optional.ofNullable(value).filter(v -> !v.isEmpty()).map(v -> v.substring(0, n));
  }

  /**
   * Return the first char of String
   *
   * @param value The input String
   * @return The first char
   */
  public static Optional<String> head(final String value) {
    return first(value, 1);
  }

  /**
   * Formats a string using parameters
   *
   * @param value  The value to be formatted
   * @param params Parameters to be described in the string
   * @return The formatted string
   */
  public static String format(final String value, String... params) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    Pattern p = Pattern.compile("\\{(\\w+)}");
    Matcher m = p.matcher(value);
    String result = value;
    while (m.find()) {
      int paramNumber = Integer.parseInt(m.group(1));
      if (params == null || paramNumber >= params.length) {
        throw new IllegalArgumentException("params does not have value for " + m.group());
      }
      result = result.replace(m.group(), params[paramNumber]);
    }
    return result;
  }

  /**
   * Convert hexadecimal unicode (4 digits) string to string chars
   *
   * @param value The value to decode
   * @return The decoded String
   */
  public static String hexDecode(final String value) {
    return decode(value, 4, 16);
  }

  /**
   * Convert string chars to hexadecimal unicode (4 digits)
   *
   * @param value The value to encode
   * @return String in hexadecimal format.
   */
  public static String hexEncode(final String value) {
    return encode(value, 4, 16);
  }

  /**
   * The indexOf() method returns the index within the calling String of the first occurrence of the
   * specified value, starting the search at fromIndex. Returns -1 if the value is not found.
   *
   * @param value         The input String
   * @param needle        The search String
   * @param offset        The offset to start searching from.
   * @param caseSensitive boolean to indicate whether search should be case sensitive
   * @return Returns position of first occurrence of needle.
   */
  public static int indexOf(final String value, final String needle, int offset,
      boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    if (caseSensitive) {
      return value.indexOf(needle, offset);
    }
    return value.toLowerCase().indexOf(needle.toLowerCase(), offset);
  }

  /**
   * Tests if two Strings are inequal
   *
   * @param first  The first String
   * @param second The second String
   * @return true if first and second are not equal false otherwise
   */
  public static boolean unequal(final String first, final String second) {
    return !Objects.equals(first, second);
  }

  /**
   * Tests if two Strings are inequal
   *
   * @param first  The first String
   * @param second The second String
   * @return true if first and second are not equal false otherwise
   * @deprecated use unequal instead
   */
  public static boolean inequal(final String first, final String second) {
    return !Objects.equals(first, second);
  }

  /**
   * Inserts 'substr' into the 'value' at the 'index' provided.
   *
   * @param value  The input String
   * @param substr The String to insert
   * @param index  The index to insert substr
   * @return String with substr added
   */
  public static String insert(final String value, final String substr, final int index) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    Preconditions.checkNotNull(substr, NULL_STRING_ERR_MSG);
    if (index > value.length()) {
      return value;
    }
    return append(value.substring(0, index), substr, value.substring(index));
  }

  /**
   * Verifies if String is uppercase
   *
   * @param value The input String
   * @return true if String is uppercase false otherwise
   */
  public static boolean isUpperCase(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    for (int i = 0; i < value.length(); i++) {
      if (Character.isLowerCase(value.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Verifies if String is lower case
   *
   * @param value The input String
   * @return true if String is lowercase false otherwise
   */
  public static boolean isLowerCase(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    for (int i = 0; i < value.length(); i++) {
      if (Character.isUpperCase(value.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Return the last n chars of String
   *
   * @param value The input String
   * @param n     Number of chars to return
   * @return n Last characters
   */
  public static String last(final String value, int n) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    if (n > value.length()) {
      return value;
    }
    return value.substring(value.length() - n);
  }

  /**
   * Returns a new string of a given length such that the beginning of the string is padded.
   *
   * @param value  The input String
   * @param pad    The pad
   * @param length Length of the String we want
   * @return Padded String
   */
  public static String leftPad(final String value, final String pad, final int length) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    Preconditions.checkNotNull(pad, NULL_STRING_ERR_MSG);
    if (value.length() > length) {
      return value;
    }
    return append(repeat(pad, length - value.length()), value);
  }

  /**
   * Checks whether Object is String
   *
   * @param value The input String
   * @return true if Object is a String false otherwise
   */
  public static boolean isString(final Object value) {
    if (Objects.isNull(value)) {
      throw new IllegalArgumentException("value can't be null");
    }
    return value instanceof String;
  }

  /**
   * This method returns the index within the calling String object of the last occurrence of the
   * specified value, searching backwards from the offset. Returns -1 if the value is not found. The
   * search starts from the end and case sensitive.
   *
   * @param value  The input String
   * @param needle The search String
   * @return Return position of the last occurrence of 'needle'.
   */
  public static int lastIndexOf(final String value, final String needle) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return lastIndexOf(value, needle, value.length(), true);
  }

  /**
   * This method returns the index within the calling String object of the last occurrence of the
   * specified value, searching backwards from the offset. Returns -1 if the value is not found. The
   * search starts from the end and case sensitive.
   *
   * @param value         The input String
   * @param needle        The search String
   * @param caseSensitive true or false
   * @return Return position of the last occurrence of 'needle'.
   */
  public static int lastIndexOf(final String value, final String needle, boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return lastIndexOf(value, needle, value.length(), caseSensitive);
  }

  /**
   * This method returns the index within the calling String object of the last occurrence of the
   * specified value, searching backwards from the offset. Returns -1 if the value is not found.
   *
   * @param value         The input String
   * @param needle        The search String
   * @param offset        The index to start search from
   * @param caseSensitive whether search should be case sensitive
   * @return Return position of the last occurrence of 'needle'.
   */
  public static int lastIndexOf(final String value, final String needle, final int offset,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    Preconditions.checkNotNull(needle, NULL_STRING_ERR_MSG);
    if (caseSensitive) {
      return value.lastIndexOf(needle, offset);
    }
    return value.toLowerCase().lastIndexOf(needle.toLowerCase(), offset);
  }

  /**
   * Removes all spaces on left
   *
   * @param value The input String
   * @return String without left border spaces
   */
  public static String leftTrim(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.replaceAll("^\\s+", "");
  }

  /**
   * Returns length of String. Delegates to java.lang.String length method.
   *
   * @param value The input String
   * @return Length of the String
   */
  public static int length(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.length();
  }

  /**
   * Return a new String starting with prepends
   *
   * @param value    The input String
   * @param prepends Strings to prepend
   * @return The prepended String
   */
  public static String prepend(final String value, final String... prepends) {
    return prependArray(value, prepends);
  }

  /**
   * Return a new String starting with prepends
   *
   * @param value    The input String
   * @param prepends Strings to prepend
   * @return The prepended String
   */
  public static String prependArray(final String value, final String[] prepends) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    if (prepends == null || prepends.length == 0) {
      return value;
    }
    StringJoiner joiner = new StringJoiner("");
    for (String prepend : prepends) {
      joiner.add(prepend);
    }
    return joiner.toString() + value;
  }

  /**
   * Remove empty Strings from string array
   *
   * @param strings Array of String to be cleaned
   * @return Array of String without empty Strings
   */
  public static String[] removeEmptyStrings(String[] strings) {
    if (Objects.isNull(strings)) {
      throw new IllegalArgumentException("Input array should not be null");
    }
    return Arrays.stream(strings).filter(str -> str != null && !str.trim().isEmpty())
        .toArray(String[]::new);
  }

  /**
   * Returns a new String with the prefix removed, if present. This is case sensitive.
   *
   * @param value  The input String
   * @param prefix String to remove on left
   * @return The String without prefix
   */
  public static String removeLeft(final String value, final String prefix) {
    return removeLeft(value, prefix, true);
  }

  /**
   * Returns a new String with the prefix removed, if present.
   *
   * @param value         The input String
   * @param prefix        String to remove on left
   * @param caseSensitive ensure case sensitivity
   * @return The String without prefix
   */
  public static String removeLeft(final String value, final String prefix,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    Preconditions.checkNotNull(prefix, NULL_STRING_ERR_MSG);
    if (caseSensitive) {
      return value.startsWith(prefix) ? value.substring(prefix.length()) : value;
    }
    return value.toLowerCase().startsWith(prefix.toLowerCase()) ? value.substring(prefix.length())
        : value;
  }

  /**
   * Remove all non word characters.
   *
   * @param value The input String
   * @return String without non-word characters
   */
  public static String removeNonWords(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.replaceAll("[^\\w]+", "");
  }

  /**
   * Returns a new string with the 'suffix' removed, if present. Search is case sensitive.
   *
   * @param value  The input String
   * @param suffix The suffix to remove
   * @return The String without suffix!
   */
  public static String removeRight(final String value, final String suffix) {
    return removeRight(value, suffix, true);
  }

  /**
   * Returns a new string with the 'suffix' removed, if present.
   *
   * @param value         The input String
   * @param suffix        The suffix to remove
   * @param caseSensitive whether search should be case sensitive or not
   * @return The String without suffix!
   */
  public static String removeRight(final String value, final String suffix,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    Preconditions.checkNotNull(suffix, NULL_STRING_ERR_MSG);
    return endsWith(value, suffix, caseSensitive) ? value
        .substring(0, value.toLowerCase().lastIndexOf(suffix.toLowerCase())) : value;
  }

  /**
   * Remove all spaces and replace for value.
   *
   * @param value The input String
   * @return String without spaces
   */
  public static String removeSpaces(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.replaceAll("\\s", "");
  }

  /**
   * Returns a repeated string given a multiplier.
   *
   * @param value      The input String
   * @param multiplier Number of repeats
   * @return The String repeated
   */
  public static String repeat(final String value, final int multiplier) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return Stream.generate(() -> value).limit(multiplier).collect(joining());
  }

  /**
   * Replace all occurrences of 'search' value to 'newvalue'. Uses String replace method.
   *
   * @param value         The input
   * @param search        The String to search
   * @param newValue      The String to replace
   * @param caseSensitive whether search should be case sensitive or not
   * @return String replaced with 'newvalue'.
   */
  public static String replace(final String value, final String search, final String newValue,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    Preconditions.checkNotNull(search, NULL_STRING_ERR_MSG);
    if (caseSensitive) {
      return value.replace(search, newValue);
    }
    return Pattern.compile(search, Pattern.CASE_INSENSITIVE).matcher(value)
        .replaceAll(Matcher.quoteReplacement(newValue));
  }

  /**
   * Reverse the input String
   *
   * @param value The input String
   * @return Reversed String
   */
  public static String reverse(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return new StringBuilder(value).reverse().toString();
  }

  /**
   * Returns a new string of a given length such that the ending of the string is padded.
   *
   * @param value  The input String
   * @param length Max length of String.
   * @param pad    Character to repeat
   * @return Right padded String
   */
  public static String rightPad(final String value, String pad, final int length) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    if (value.length() > length) {
      return value;
    }
    return append(value, repeat(pad, length - value.length()));
  }

  /**
   * Remove all spaces on right.
   *
   * @param value The String
   * @return String without right boarders spaces.
   */
  public static String rightTrim(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.replaceAll("\\s+$", "");
  }

  /**
   * Truncate the string securely, not cutting a word in half. It always returns the last full
   * word.
   *
   * @param value  The input String
   * @param length Max size of the truncated String
   * @param filler String that will be added to the end of the return string. Example: '...'
   * @return The truncated String
   */
  public static String safeTruncate(final String value, final int length, final String filler) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    if (length == 0) {
      return "";
    }
    if (length >= value.length()) {
      return value;
    }

    String[] words = words(value);
    StringJoiner result = new StringJoiner(" ");
    int spaceCount = 0;
    for (String word : words) {
      if (result.length() + word.length() + filler.length() + spaceCount > length) {
        break;
      } else {
        result.add(word);
        spaceCount++;
      }
    }
    return append(result.toString(), filler);
  }

  /**
   * Alias to String split function. Defined only for completeness.
   *
   * @param value The input String
   * @param regex The delimiting regular expression
   * @return String Array
   */
  public static String[] split(final String value, final String regex) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.split(regex);
  }

  /**
   * Splits a String to words
   *
   * @param value The input String
   * @return Words Array
   */
  public static String[] words(final String value) {
    return words(value, "\\s+");
  }

  /**
   * Splits a String to words by delimiter, \s+ by default
   *
   * @param value     The input String
   * @param delimiter delimiter for splitting input String
   * @return words array
   */
  public static String[] words(final String value, final String delimiter) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.split(delimiter);
  }


  /**
   * Truncate the unsecured form string, cutting the independent string of required position.
   *
   * @param value  Value will be truncated unsecurely.
   * @param length Size of the returned string.
   * @param filler Value that will be added to the end of the return string. Example: '...'
   * @return String truncated unsafely.
   */
  public static String truncate(final String value, final int length, final String filler) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    if (length == 0) {
      return "";
    }
    if (length >= value.length()) {
      return value;
    }
    return append(value.substring(0, length - filler.length()), filler);
  }

  /**
   * Converts all HTML entities to applicable characters.
   *
   * @param encodedHtml The encoded HTML
   * @return The decoded HTML
   */
  public static String htmlDecode(final String encodedHtml) {
    Preconditions.checkNotNull(encodedHtml, NULL_STRING_ERR_MSG);
    String[] entities = encodedHtml.split("&\\W+;");
    return Arrays.stream(entities).map(e -> HtmlEntities.decodedEntities.get(e)).collect(joining());
  }

  /**
   * Convert all applicable characters to HTML entities.
   *
   * @param html The HTML to encode
   * @return The encoded data
   */
  public static String htmlEncode(final String html) {
    Preconditions.checkNotNull(html, NULL_STRING_ERR_MSG);
    return html.chars().mapToObj(c -> "\\u" + String.format("%04x", c).toUpperCase())
        .map(HtmlEntities.encodedEntities::get).collect(joining());
  }

  /**
   * It returns a string with its characters in random order.
   *
   * @param value The input String
   * @return The shuffled String
   */
  public static String shuffle(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    String[] chars = chars(value);
    Random random = new Random();
    for (int i = 0; i < chars.length; i++) {
      int r = random.nextInt(chars.length);
      String tmp = chars[i];
      chars[i] = chars[r];
      chars[r] = tmp;
    }
    return Arrays.stream(chars).collect(joining());
  }

  /**
   * Alias of substring method
   *
   * @param value The input String
   * @param begin Start of slice.
   * @param end   End of slice.
   * @return The String sliced!
   */
  public static String slice(final String value, int begin, int end) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.substring(begin, end);
  }

  /**
   * Convert a String to a slug
   *
   * @param value The value to slugify
   * @return The slugified value
   */
  public static String slugify(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    String transliterated = transliterate(collapseWhitespace(value.trim().toLowerCase()));
    return Arrays.stream(words(transliterated.replace("&", "-and-"), "\\W+")).collect(joining("-"));
  }

  /**
   * Remove all non valid characters.
   *
   * @param value The input String
   * @return String without non valid characters.
   */
  public static String transliterate(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    String result = value;
    Set<Entry<String, List<String>>> entries = Ascii.ascii.entrySet();
    for (Map.Entry<String, List<String>> entry : entries) {
      for (String ch : entry.getValue()) {
        result = result.replace(ch, entry.getKey());
      }
    }
    return result;
  }

  /**
   * Surrounds a 'value' with the given 'prefix' and 'suffix'.
   *
   * @param value  The input String
   * @param prefix prefix. If suffix is null then prefix is used
   * @param suffix suffix
   * @return The String with surround substrs!
   */
  public static String surround(final String value, final String prefix, final String suffix) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    String _prefix = Optional.ofNullable(prefix).orElse("");
    return append(_prefix, value, Optional.ofNullable(suffix).orElse(_prefix));
  }

  /**
   * Transform to camelCase
   *
   * @param value The input String
   * @return String in camelCase.
   */
  public static String toCamelCase(final String value) {
    if (value == null || value.length() == 0) {
      return "";
    }
    String str = toStudlyCase(value);
    return str.substring(0, 1).toLowerCase() + str.substring(1);
  }

  /**
   * Transform to StudlyCaps.
   *
   * @param value The input String
   * @return String in StudlyCaps.
   */
  public static String toStudlyCase(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    String[] words = collapseWhitespace(value.trim()).split("\\s*(_|-|\\s)\\s*");
    return Arrays.stream(words).filter(w -> !w.trim().isEmpty()).map(StrUtil::upperFirst)
        .collect(joining());
  }

  /**
   * Return tail of the String
   *
   * @param value The input String
   * @return String tail
   */
  public static Optional<String> tail(final String value) {
    return Optional.ofNullable(value).filter(v -> !v.isEmpty()).map(v -> last(v, v.length() - 1));
  }

  /**
   * Decamelize String
   *
   * @param value The input String
   * @param chr   string to use
   * @return String decamelized.
   */
  public static String toDecamelize(final String value, final String chr) {
    String camelCasedString = toCamelCase(value);
    String[] words = camelCasedString.split("(?=\\p{Upper})");
    return Arrays.stream(words).map(String::toLowerCase)
        .collect(joining(Optional.ofNullable(chr).orElse(" ")));
  }

  /**
   * Transform to kebab-case.
   *
   * @param value The input String
   * @return String in kebab-case.
   */
  public static String toKebabCase(final String value) {
    return toDecamelize(value, "-");
  }

  /**
   * Transform to snake_case.
   *
   * @param value The input String
   * @return String in snake_case.
   */
  public static String toSnakeCase(final String value) {
    return toDecamelize(value, "_");
  }

  public static String decode(final String value, final int digits, final int radix) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return Arrays.stream(value.split("(?<=\\G.{" + digits + "})"))
        .map(data -> String.valueOf(Character.toChars(Integer.parseInt(data, radix))))
        .collect(joining());
  }

  public static String encode(final String value, final int digits, final int radix) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.chars().mapToObj(ch -> leftPad(Integer.toString(ch, radix), "0", digits))
        .collect(joining());
  }

  /**
   * Join concatenates all the elements of the strings array into a single String. The separator
   * string is placed between elements in the resulting string.
   *
   * @param strings   The input array to concatenate
   * @param separator The separator to use
   * @return Concatenated String
   */
  public static String join(final String[] strings, final String separator)
      throws IllegalArgumentException {
    if (strings == null) {
      throw new IllegalArgumentException("Input array 'strings' can't be null");
    }
    if (separator == null) {
      throw new IllegalArgumentException("separator can't be null");
    }
    StringJoiner joiner = new StringJoiner(separator);
    for (String el : strings) {
      joiner.add(el);
    }
    return joiner.toString();
  }

  /**
   * Converts the first character of string to upper case and the remaining to lower case.
   *
   * @param input The string to capitalize
   * @return The capitalized string
   */
  public static String capitalize(final String input) throws IllegalArgumentException {
    if (input == null) {
      throw new IllegalArgumentException("input can't be null");
    }
    if (input.length() == 0) {
      return "";
    }
    return head(input).map(String::toUpperCase)
        .map(h -> tail(input).map(t -> h + t.toLowerCase()).orElse(h)).get();
  }

  /**
   * Converts the first character of string to lower case.
   *
   * @param input The string to convert
   * @return The converted string
   */
  public static String lowerFirst(final String input) throws IllegalArgumentException {
    if (input == null) {
      throw new IllegalArgumentException("input can't be null");
    }
    if (input.length() == 0) {
      return "";
    }

    return head(input).map(String::toLowerCase).map(h -> tail(input).map(t -> h + t).orElse(h))
        .get();
  }

  /**
   * Verifies whether String is enclosed by encloser
   *
   * @param input    The input String
   * @param encloser String which encloses input String
   * @return true if enclosed false otherwise
   */
  public static boolean isEnclosedBetween(final String input, final String encloser) {
    return isEnclosedBetween(input, encloser, encloser);
  }

  /**
   * Verifies whether String is enclosed by encloser
   *
   * @param input         The input String
   * @param leftEncloser  String which encloses input String at left start
   * @param rightEncloser String which encloses input String at the right end
   * @return true if enclosed false otherwise
   */
  public static boolean isEnclosedBetween(final String input, final String leftEncloser,
      String rightEncloser) {
    if (input == null) {
      throw new IllegalArgumentException("input can't be null");
    }
    if (leftEncloser == null) {
      throw new IllegalArgumentException("leftEncloser can't be null");
    }
    if (rightEncloser == null) {
      throw new IllegalArgumentException("rightEncloser can't be null");
    }
    return input.startsWith(leftEncloser) && input.endsWith(rightEncloser);
  }

  /**
   * Converts the first character of string to upper case.
   *
   * @param input The string to convert.
   * @return Returns the converted string.
   */
  public static String upperFirst(String input) {
    if (input == null) {
      throw new IllegalArgumentException("input can't be null");
    }
    return head(input).map(String::toUpperCase).map(h -> tail(input).map(t -> h + t).orElse(h))
        .get();
  }

  /**
   * Removes leading whitespace from string.
   *
   * @param input The string to trim.
   * @return Returns the trimmed string.
   */
  public static Optional<String> trimStart(final String input) {
    return Optional.ofNullable(input).filter(v -> !v.isEmpty()).map(StrUtil::leftTrim);
  }

  /**
   * Removes leading characters from string.
   *
   * @param input The string to trim.
   * @param chars The characters to trim.
   * @return Returns the trimmed string.
   */
  public static Optional<String> trimStart(final String input, String... chars) {
    return Optional.ofNullable(input).filter(v -> !v.isEmpty()).map(v -> {
      String pattern = String.format("^[%s]+", join(chars, "\\"));
      return v.replaceAll(pattern, "");
    });
  }

  /**
   * Removes trailing whitespace from string.
   *
   * @param input The string to trim.
   * @return Returns the trimmed string.
   */
  public static Optional<String> trimEnd(final String input) {
    return Optional.ofNullable(input).filter(v -> !v.isEmpty()).map(StrUtil::rightTrim);
  }

  /**
   * Removes trailing characters from string.
   *
   * @param input The string to trim.
   * @param chars The characters to trim.
   * @return Returns the trimmed string.
   */
  public static Optional<String> trimEnd(final String input, String... chars) {
    return Optional.ofNullable(input).filter(v -> !v.isEmpty()).map(v -> {
      String pattern = String.format("[%s]+$", join(chars, "\\"));
      return v.replaceAll(pattern, "");
    });
  }

  /**
   * Counts the number of occurrences of each character in the string
   *
   * @param input The input string
   * @return A map containing the number of occurrences of each character in the string
   */
  public static Map<Character, Long> charsCount(String input) {
    if (isNullOrEmpty(input)) {
      return Collections.emptyMap();
    }

    return input.chars().mapToObj(c -> (char) c).collect(groupingBy(identity(), counting()));
  }

  /**
   * Checks if string is empty.  This is a null safe check and will return true when string is
   * null.
   *
   * @param input The input string
   * @return true if input string is null or empty
   */
  public static boolean isBlank(String input) {
    return input == null || input.isEmpty();
  }

  /**
   * Changes passed in string to all lower case and adds underscore between words.
   *
   * @param input The input string
   * @return the input string in all lower case with underscores between words
   */
  public static String underscored(final String input) {
    if (input == null || input.length() == 0) {
      return "";
    }

    return input.trim().replaceAll("([a-z\\d])([A-Z]+)", "$1_$2").replaceAll("[-\\s]+", "_")
        .toLowerCase();
  }

  /**
   * Aggregates the contents of n strings into a single list of tuples.
   *
   * @param inputs A list of strings.
   * @return A list of strings if none of the strings in the input is null or empty. An empty list
   * otherwise.
   */
  public static List<String> zip(String... inputs) {
    if (inputs.length == 0) {
      return Collections.emptyList();
    }
    OptionalInt min = Arrays.stream(inputs).mapToInt(str -> str == null ? 0 : str.length()).min();
    if (!min.isPresent()) {
      return Collections.emptyList();
    }
    return IntStream.range(0, min.getAsInt())
        .mapToObj(elementIndex -> Arrays.stream(inputs)
            .map(input -> String.valueOf(input.charAt(elementIndex)))
            .collect(joining()))
        .collect(toList());
  }

  /**
   * Split lines to an array
   *
   * @param input The input String
   * @return lines in an array
   */
  public static String[] lines(String input) {
    if (input == null) {
      return EMPTY_ARRAY;
    }
    return input.split("\r\n?|\n");
  }

  /**
   * Converts a underscored or camelized string into an dasherized one.
   *
   * @param input The input String
   * @return dasherized String.
   */
  public static String dasherize(String input) {
    return toKebabCase(input);
  }

  /**
   * Converts an underscored, camelized, or dasherized string into a humanized one. Also removes
   * beginning and ending whitespace.
   *
   * @param input The input String
   * @return humanized version of String
   */
  public static String humanize(final String input) {
    if (input == null || input.length() == 0) {
      return "";
    }
    return upperFirst(underscored(input).replaceAll("_", " "));
  }

  /**
   * Returns a copy of the string in which all the case-based characters have had their case
   * swapped.
   *
   * @param input Input string
   * @return String with all the case swapped
   */
  public static String swapCase(String input) {
    if (input == null || input.length() == 0) {
      return "";
    }
    StringBuilder resultBuilder = new StringBuilder();
    for (char ch : input.toCharArray()) {
      if (Character.isUpperCase(ch)) {
        resultBuilder.append(Character.toLowerCase(ch));
      } else {
        resultBuilder.append(Character.toUpperCase(ch));
      }
    }
    return resultBuilder.toString();
  }

  /**
   * Returns a string representation of the number passed in where groups of three digits are
   * delimited by comma
   *
   * @param number Input number
   * @return formatted String
   */
  public static String formatNumber(long number) {
    String stringRepresentation = Long.toString(number);
    StringBuilder sb = new StringBuilder();
    int bound = stringRepresentation.length() - 1;
    String delimiter = ",";
    int counter = 0;
    for (int i = bound; i >= 0; i--) {
      char c = stringRepresentation.charAt(i);
      if (i != bound && counter % 3 == 0) {
        sb.append(delimiter);
      }
      sb.append(c);
      counter++;
    }
    return sb.reverse().toString();
  }

  public static String[] chop(String input, int step) {
    if (input == null || input.length() == 0) {
      return EMPTY_ARRAY;
    }
    if (step == 0) {
      return new String[]{input};
    }
    int strLength = input.length();
    int iterations = strLength % step == 0 ? strLength / step : strLength / step + 1;
    return IntStream.iterate(0, i -> i + step)
        .limit(iterations)
        .mapToObj(i -> input.substring(i, (i + step) < strLength ? i + step : strLength))
        .toArray(String[]::new);
  }

  /**
   * Converts a String into its Start Case version https://en.wikipedia.org/wiki/Letter_case#Stylistic_or_specialised_usage
   *
   * @param input The input String
   * @return Start Case String
   */
  public static String startCase(final String input) {
    Preconditions.checkNotNull(input, NULL_STRING_ERR_MSG);
    // split into a word when we encounter a space, or an underscore, or a dash, or a switch from lower to upper case
    String[] words = words(input, "\\s|_|-|(?<=[a-z])(?=[A-Z])");
    return Arrays.stream(words).filter(w -> !w.trim().isEmpty())
        .map(w -> upperFirst(w.toLowerCase())).collect(joining(" "));
  }

  public static String escapeRegExp(final String input) {
    Preconditions.checkNotNull(input, NULL_STRING_ERR_MSG);
    return input.replaceAll("[\\\\\\^\\$\\*\\+\\-\\?\\.\\|\\(\\)\\{\\}\\[\\]]", "\\\\$0");
  }

  private static long countSubstr(String value, String subStr, boolean allowOverlapping,
      long count) {
    int position = value.indexOf(subStr);
    if (position == -1) {
      return count;
    }
    int offset;
    if (!allowOverlapping) {
      offset = position + subStr.length();
    } else {
      offset = position + 1;
    }
    return countSubstr(value.substring(offset), subStr, allowOverlapping, ++count);
  }

}

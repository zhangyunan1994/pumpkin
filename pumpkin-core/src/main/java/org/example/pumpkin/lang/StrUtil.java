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

  public static String[] chars(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.split("");
  }

  public static String collapseWhitespace(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.trim().replaceAll("\\s\\s+", " ");
  }

  public static boolean contains(final String value, final String needle) {
    return contains(value, needle, false);
  }

  public static boolean contains(final String value, final String needle,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    if (caseSensitive) {
      return value.contains(needle);
    }
    return value.toLowerCase().contains(needle.toLowerCase());
  }

  public static boolean containsAll(final String value, final String[] needles) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return Arrays.stream(needles).allMatch(needle -> contains(value, needle, false));
  }

  public static boolean containsAll(final String value, final String[] needles,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return Arrays.stream(needles).allMatch(needle -> contains(value, needle, caseSensitive));
  }

  public static boolean containsAny(final String value, final String[] needles) {
    return containsAny(value, needles, false);
  }

  public static boolean containsAny(final String value, final String[] needles,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return Arrays.stream(needles).anyMatch(needle -> contains(value, needle, caseSensitive));
  }

  public static long countSubstr(final String value, final String subStr) {
    return countSubstr(value, subStr, true, false);
  }

  public static long countSubstr(final String value, final String subStr,
      final boolean caseSensitive,
      boolean allowOverlapping) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return countSubstr(caseSensitive ? value : value.toLowerCase(),
        caseSensitive ? subStr : subStr.toLowerCase(),
        allowOverlapping, 0L);
  }

  public static boolean endsWith(final String value, final String search) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return endsWith(value, search, value.length(), true);
  }

  public static boolean endsWith(final String value, final String search,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return endsWith(value, search, value.length(), caseSensitive);
  }

  public static boolean endsWith(final String value, final String search, final int position,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    int remainingLength = position - search.length();
    if (caseSensitive) {
      return value.indexOf(search, remainingLength) > -1;
    }
    return value.toLowerCase().indexOf(search.toLowerCase(), remainingLength) > -1;
  }

  public static String ensureLeft(final String value, final String prefix) {
    return ensureLeft(value, prefix, true);
  }

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

  public static String base64Decode(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
  }

  public static String base64Encode(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
  }

  public static String binDecode(final String value) {
    return decode(value, 16, 2);
  }

  public static String binEncode(final String value) {
    return encode(value, 16, 2);
  }

  public static String decDecode(final String value) {
    return decode(value, 5, 10);
  }

  public static String decEncode(final String value) {
    return encode(value, 5, 10);
  }

  public static String ensureRight(final String value, final String suffix) {
    return ensureRight(value, suffix, true);
  }

  public static String ensureRight(final String value, final String suffix, boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return endsWith(value, suffix, caseSensitive) ? value : append(value, suffix);
  }

  public static Optional<String> first(final String value, final int n) {
    return Optional.ofNullable(value).filter(v -> !v.isEmpty()).map(v -> v.substring(0, n));
  }

  public static Optional<String> head(final String value) {
    return first(value, 1);
  }

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

  public static String hexDecode(final String value) {
    return decode(value, 4, 16);
  }

  public static String hexEncode(final String value) {
    return encode(value, 4, 16);
  }

  public static int indexOf(final String value, final String needle, int offset,
      boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    if (caseSensitive) {
      return value.indexOf(needle, offset);
    }
    return value.toLowerCase().indexOf(needle.toLowerCase(), offset);
  }

  public static boolean unequal(final String first, final String second) {
    return !Objects.equals(first, second);
  }

  public static boolean inequal(final String first, final String second) {
    return !Objects.equals(first, second);
  }

  public static String insert(final String value, final String substr, final int index) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    Preconditions.checkNotNull(substr, NULL_STRING_ERR_MSG);
    if (index > value.length()) {
      return value;
    }
    return append(value.substring(0, index), substr, value.substring(index));
  }

  public static boolean isUpperCase(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    for (int i = 0; i < value.length(); i++) {
      if (Character.isLowerCase(value.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  public static boolean isLowerCase(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    for (int i = 0; i < value.length(); i++) {
      if (Character.isUpperCase(value.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  public static String last(final String value, int n) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    if (n > value.length()) {
      return value;
    }
    return value.substring(value.length() - n);
  }

  public static String leftPad(final String value, final String pad, final int length) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    Preconditions.checkNotNull(pad, NULL_STRING_ERR_MSG);
    if (value.length() > length) {
      return value;
    }
    return append(repeat(pad, length - value.length()), value);
  }

  public static boolean isString(final Object value) {
    if (Objects.isNull(value)) {
      throw new IllegalArgumentException("value can't be null");
    }
    return value instanceof String;
  }

  public static int lastIndexOf(final String value, final String needle) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return lastIndexOf(value, needle, value.length(), true);
  }

  public static int lastIndexOf(final String value, final String needle, boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return lastIndexOf(value, needle, value.length(), caseSensitive);
  }

  public static int lastIndexOf(final String value, final String needle, final int offset,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    Preconditions.checkNotNull(needle, NULL_STRING_ERR_MSG);
    if (caseSensitive) {
      return value.lastIndexOf(needle, offset);
    }
    return value.toLowerCase().lastIndexOf(needle.toLowerCase(), offset);
  }

  public static String leftTrim(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.replaceAll("^\\s+", "");
  }

  public static int length(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.length();
  }

  public static String prepend(final String value, final String... prepends) {
    return prependArray(value, prepends);
  }

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

  public static String[] removeEmptyStrings(String[] strings) {
    if (Objects.isNull(strings)) {
      throw new IllegalArgumentException("Input array should not be null");
    }
    return Arrays.stream(strings).filter(str -> str != null && !str.trim().isEmpty())
        .toArray(String[]::new);
  }

  public static String removeLeft(final String value, final String prefix) {
    return removeLeft(value, prefix, true);
  }

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

  public static String removeNonWords(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.replaceAll("[^\\w]+", "");
  }

  public static String removeRight(final String value, final String suffix) {
    return removeRight(value, suffix, true);
  }

  public static String removeRight(final String value, final String suffix,
      final boolean caseSensitive) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    Preconditions.checkNotNull(suffix, NULL_STRING_ERR_MSG);
    return endsWith(value, suffix, caseSensitive) ? value
        .substring(0, value.toLowerCase().lastIndexOf(suffix.toLowerCase())) : value;
  }

  public static String removeSpaces(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.replaceAll("\\s", "");
  }

  public static String repeat(final String value, final int multiplier) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return Stream.generate(() -> value).limit(multiplier).collect(joining());
  }

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

  public static String reverse(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return new StringBuilder(value).reverse().toString();
  }

  public static String rightPad(final String value, String pad, final int length) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    if (value.length() > length) {
      return value;
    }
    return append(value, repeat(pad, length - value.length()));
  }

  public static String rightTrim(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.replaceAll("\\s+$", "");
  }

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

  public static String[] split(final String value, final String regex) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.split(regex);
  }

  public static String[] words(final String value) {
    return words(value, "\\s+");
  }

  public static String[] words(final String value, final String delimiter) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.split(delimiter);
  }


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

  public static String htmlDecode(final String encodedHtml) {
    Preconditions.checkNotNull(encodedHtml, NULL_STRING_ERR_MSG);
    String[] entities = encodedHtml.split("&\\W+;");
    return Arrays.stream(entities).map(e -> HtmlEntities.decodedEntities.get(e)).collect(joining());
  }

  public static String htmlEncode(final String html) {
    Preconditions.checkNotNull(html, NULL_STRING_ERR_MSG);
    return html.chars().mapToObj(c -> "\\u" + String.format("%04x", c).toUpperCase())
        .map(HtmlEntities.encodedEntities::get).collect(joining());
  }

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

  public static String slice(final String value, int begin, int end) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    return value.substring(begin, end);
  }

  public static String slugify(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    String transliterated = transliterate(collapseWhitespace(value.trim().toLowerCase()));
    return Arrays.stream(words(transliterated.replace("&", "-and-"), "\\W+")).collect(joining("-"));
  }

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

  public static String surround(final String value, final String prefix, final String suffix) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    String _prefix = Optional.ofNullable(prefix).orElse("");
    return append(_prefix, value, Optional.ofNullable(suffix).orElse(_prefix));
  }

  public static String toCamelCase(final String value) {
    if (value == null || value.length() == 0) {
      return "";
    }
    String str = toStudlyCase(value);
    return str.substring(0, 1).toLowerCase() + str.substring(1);
  }

  public static String toStudlyCase(final String value) {
    Preconditions.checkNotNull(value, NULL_STRING_ERR_MSG);
    String[] words = collapseWhitespace(value.trim()).split("\\s*(_|-|\\s)\\s*");
    return Arrays.stream(words).filter(w -> !w.trim().isEmpty()).map(StrUtil::upperFirst)
        .collect(joining());
  }

  public static Optional<String> tail(final String value) {
    return Optional.ofNullable(value).filter(v -> !v.isEmpty()).map(v -> last(v, v.length() - 1));
  }

  public static String toDecamelize(final String value, final String chr) {
    String camelCasedString = toCamelCase(value);
    String[] words = camelCasedString.split("(?=\\p{Upper})");
    return Arrays.stream(words).map(String::toLowerCase)
        .collect(joining(Optional.ofNullable(chr).orElse(" ")));
  }

  public static String toKebabCase(final String value) {
    return toDecamelize(value, "-");
  }

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

  public static boolean isEnclosedBetween(final String input, final String encloser) {
    return isEnclosedBetween(input, encloser, encloser);
  }

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

  public static String upperFirst(String input) {
    if (input == null) {
      throw new IllegalArgumentException("input can't be null");
    }
    return head(input).map(String::toUpperCase).map(h -> tail(input).map(t -> h + t).orElse(h))
        .get();
  }

  public static Optional<String> trimStart(final String input) {
    return Optional.ofNullable(input).filter(v -> !v.isEmpty()).map(StrUtil::leftTrim);
  }

  public static Optional<String> trimStart(final String input, String... chars) {
    return Optional.ofNullable(input).filter(v -> !v.isEmpty()).map(v -> {
      String pattern = String.format("^[%s]+", join(chars, "\\"));
      return v.replaceAll(pattern, "");
    });
  }

  public static Optional<String> trimEnd(final String input) {
    return Optional.ofNullable(input).filter(v -> !v.isEmpty()).map(StrUtil::rightTrim);
  }

  public static Optional<String> trimEnd(final String input, String... chars) {
    return Optional.ofNullable(input).filter(v -> !v.isEmpty()).map(v -> {
      String pattern = String.format("[%s]+$", join(chars, "\\"));
      return v.replaceAll(pattern, "");
    });
  }

  public static Map<Character, Long> charsCount(String input) {
    if (isNullOrEmpty(input)) {
      return Collections.emptyMap();
    }

    return input.chars().mapToObj(c -> (char) c).collect(groupingBy(identity(), counting()));
  }

  public static boolean isBlank(String input) {
    return input == null || input.isEmpty();
  }

  public static String underscored(final String input) {
    if (input == null || input.length() == 0) {
      return "";
    }

    return input.trim().replaceAll("([a-z\\d])([A-Z]+)", "$1_$2").replaceAll("[-\\s]+", "_")
        .toLowerCase();
  }

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

  public static String[] lines(String input) {
    if (input == null) {
      return EMPTY_ARRAY;
    }
    return input.split("\r\n?|\n");
  }

  public static String dasherize(String input) {
    return toKebabCase(input);
  }

  public static String humanize(final String input) {
    if (input == null || input.length() == 0) {
      return "";
    }
    return upperFirst(underscored(input).replaceAll("_", " "));
  }

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

package org.example.pumpkin.codec.binary;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Base64 {

  private Base64() {
  }

  protected static final byte PAD_DEFAULT = '=';

  private static final byte[] DECODE_TABLE = {
      //   0   1   2   3   4   5   6   7   8   9   A   B   C   D   E   F
      -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, // 00-0f
      -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, // 10-1f
      -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, // 20-2f + - /
      52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, // 30-3f 0-9
      -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, // 40-4f A-O
      15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, // 50-5f P-Z _
      -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, // 60-6f a-o
      41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51                      // 70-7a p-z
  };

  public static boolean isBase64(byte octet) {
    return octet == PAD_DEFAULT || (octet >= 0 && octet < DECODE_TABLE.length
        && DECODE_TABLE[octet] != -1);
  }

  public static boolean isBase64(final String base64) {
    return isBase64(base64.getBytes(StandardCharsets.UTF_8));
  }

  public static boolean isBase64(final byte[] arrayOctet) {
    for (int i = 0; i < arrayOctet.length; i++) {
      if (!isBase64(arrayOctet[i]) && !isWhiteSpace(arrayOctet[i])) {
        return false;
      }
    }
    return true;
  }

  public static byte[] encode(byte[] source) {
    return Base64Encoder.getEncoder().encode(source);
  }

  public static byte[] encode(String source) {
    return Base64Encoder.getEncoder().encode(source.getBytes());
  }

  public static byte[] encode(String source, Charset charset) {
    return Base64Encoder.getEncoder().encode(source.getBytes(charset));
  }

  public static String encodeToString(byte[] source) {
    return encodeToString(source, StandardCharsets.UTF_8);
  }

  public static String encodeToString(byte[] source, Charset charset) {
    return new String(Base64Encoder.getEncoder().encode(source), charset);
  }

  public static String encodeToString(String source) {
    return encodeToString(source.getBytes(), StandardCharsets.UTF_8);
  }

  public static String encodeToString(String source, Charset charset) {
    return encodeToString(source.getBytes(charset), charset);
  }

  public static byte[] encodeUrlSafe(byte[] source) {
    return Base64Encoder.getUrlEncoder().encode(source);
  }

  public static String encodeUrlSafeToString(byte[] source) {
    return encodeUrlSafeToString(source, StandardCharsets.UTF_8);
  }

  public static String encodeUrlSafeToString(byte[] source, Charset charset) {
    return new String(Base64Encoder.getUrlEncoder().encode(source), charset);
  }

  protected static boolean isWhiteSpace(final byte byteToCheck) {
    switch (byteToCheck) {
      case ' ':
      case '\n':
      case '\r':
      case '\t':
        return true;
      default:
        return false;
    }
  }

}

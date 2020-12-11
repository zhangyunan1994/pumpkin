package org.example.pumpkin.codec.digest;

import java.nio.charset.Charset;
import java.security.MessageDigest;

public class MD5 {

  private MD5() {
  }

  private static final char[] HEX_ARRAY = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      'a', 'b', 'c', 'd', 'e', 'f'};

  public static String md5(byte[] btInput) {
    try {
      MessageDigest mdInst = MessageDigest.getInstance("MD5");
      mdInst.update(btInput);
      byte[] md = mdInst.digest();
      int j = md.length;
      char[] str = new char[j * 2];
      int k = 0;
      for (byte byte0 : md) {
        str[k++] = HEX_ARRAY[byte0 >>> 4 & 0xf];
        str[k++] = HEX_ARRAY[byte0 & 0xf];
      }
      return new String(str);
    } catch (Exception e) {
      return null;
    }
  }

  public static String md5(String param) {
    return md5(param.getBytes());
  }

  public static String md5(String param, Charset charset) {
    return md5(param.getBytes(charset));
  }
}

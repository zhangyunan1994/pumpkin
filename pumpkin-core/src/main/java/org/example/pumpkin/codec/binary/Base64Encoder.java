package org.example.pumpkin.codec.binary;

import java.util.Arrays;

class Base64Encoder {

  public static Encoder getEncoder() {
    return Encoder.RFC4648;
  }

  public static Encoder getUrlEncoder() {
    return Encoder.RFC4648_URLSAFE;
  }

  public static Encoder getMimeEncoder() {
    return Encoder.RFC2045;
  }

  public static class Encoder {

    private final byte[] newline;
    private final int linemax;
    private final boolean isURL;
    private final boolean doPadding;

    private Encoder(boolean isURL, byte[] newline, int linemax, boolean doPadding) {
      this.isURL = isURL;
      this.newline = newline;
      this.linemax = linemax;
      this.doPadding = doPadding;
    }

    private static final char[] STANDARD_ENCODE = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };

    private static final char[] URL_SAFE_ENCODE = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
    };

    private static final int MIMELINEMAX = 76;
    private static final byte[] CRLF = new byte[]{'\r', '\n'};

    static final Encoder RFC4648 = new Encoder(false, null, -1, true);
    static final Encoder RFC4648_URLSAFE = new Encoder(true, null, -1, true);
    static final Encoder RFC2045 = new Encoder(false, CRLF, MIMELINEMAX, true);

    private int outLength(int srclen) {
      int len = 0;
      if (doPadding) {
        len = 4 * ((srclen + 2) / 3);
      } else {
        int n = srclen % 3;
        len = 4 * (srclen / 3) + (n == 0 ? 0 : n + 1);
      }
      if (linemax > 0)                                  // line separators
      {
        len += (len - 1) / linemax * newline.length;
      }
      return len;
    }

    public byte[] encode(byte[] src) {
      int len = outLength(src.length);          // dst array size
      byte[] dst = new byte[len];
      int ret = encode0(src, 0, src.length, dst);
      if (ret != dst.length) {
        return Arrays.copyOf(dst, ret);
      }
      return dst;
    }

    private int encode0(byte[] src, int off, int end, byte[] dst) {
      char[] base64 = isURL ? URL_SAFE_ENCODE : STANDARD_ENCODE;
      int sp = off;
      int slen = (end - off) / 3 * 3;
      int sl = off + slen;
      if (linemax > 0 && slen > linemax / 4 * 3) {
        slen = linemax / 4 * 3;
      }
      int dp = 0;
      while (sp < sl) {
        int sl0 = Math.min(sp + slen, sl);
        for (int sp0 = sp, dp0 = dp; sp0 < sl0; ) {
          int bits = (src[sp0++] & 0xff) << 16 |
              (src[sp0++] & 0xff) << 8 |
              (src[sp0++] & 0xff);
          dst[dp0++] = (byte) base64[(bits >>> 18) & 0x3f];
          dst[dp0++] = (byte) base64[(bits >>> 12) & 0x3f];
          dst[dp0++] = (byte) base64[(bits >>> 6) & 0x3f];
          dst[dp0++] = (byte) base64[bits & 0x3f];
        }
        int dlen = (sl0 - sp) / 3 * 4;
        dp += dlen;
        sp = sl0;
        if (dlen == linemax && sp < end) {
          for (byte b : newline) {
            dst[dp++] = b;
          }
        }
      }
      if (sp < end) {               // 1 or 2 leftover bytes
        int b0 = src[sp++] & 0xff;
        dst[dp++] = (byte) base64[b0 >> 2];
        if (sp == end) {
          dst[dp++] = (byte) base64[(b0 << 4) & 0x3f];
          if (doPadding) {
            dst[dp++] = '=';
            dst[dp++] = '=';
          }
        } else {
          int b1 = src[sp++] & 0xff;
          dst[dp++] = (byte) base64[(b0 << 4) & 0x3f | (b1 >> 4)];
          dst[dp++] = (byte) base64[(b1 << 2) & 0x3f];
          if (doPadding) {
            dst[dp++] = '=';
          }
        }
      }
      return dp;
    }
  }


}

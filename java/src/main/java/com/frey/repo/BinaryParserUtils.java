package com.frey.repo;

/**
 * Created by yuanshuai on 2015/6/1.
 */

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by yuanshuai on 2015/6/1.
 */
public class BinaryParserUtils {
  /**
   * @param p
   * @param off
   * @param len
   * @return
   */
  public static final long byte2long(byte[] p, int off, int len) {
    long ret = 0;
    int done = off + len;
    for (int i = off; i < done; i++)
      ret = ((ret << 8) & 0xffffffff) + (p[i] & 0xff);
    return ret;
  }

  public static final long byte2UnixTime(byte[] p, int off, int len) {
    return byte2long(p, off, len);
  }

  public static final String byte2DateTime(byte[] p, int off, int len) {
    return DateUtil.unixTimeToDateString(byte2long(p, off, len));
  }

  public static final int byte2int(byte[] p, int offset, int length) {
    return (int) byte2long(p, offset, length);
  }

  public static final char byte2Char(byte[] p, int offset) {
    return (char) (((p[offset] & 0xFF) << 8) | (p[offset + 1] & 0xFF));
  }

  public static final short byte2Short(byte[] p, int offset, int length) {
    return (short) byte2long(p, offset, length);
    //        return (short) (((p[offset] & 0xFF) << 8) | (p[offset+1] & 0xFF));
  }

  @SuppressWarnings("null")
  public static final BigInteger byte2BigInteger(byte[] p, int off, int len) {
    byte[] bytes = new byte[len];
    int j = 0;
    for (int i = off; i < off + len; i++) {
      bytes[j] = p[i];
      j++;
    }
    BigInteger b = new BigInteger(bytes);
    return b;

  }

  public static final byte[] getByte(byte[] p, int off, int len) {
    byte[] bytes = new byte[len];
    int j = 0;
    for (int i = off; i < off + len; i++) {
      bytes[j] = p[i];
      j++;
    }
    return bytes;

  }

  public static final String byteToIp(byte[] p, int off, int len) {
    long ipaddress = byte2long(p, off, len);
    StringBuilder sb = new StringBuilder();
    sb.append(String.valueOf((ipaddress >>> 24)));
    sb.append(".");
    sb.append(String.valueOf((ipaddress & 0x00FFFFFF) >>> 16));
    sb.append(".");
    sb.append(String.valueOf((ipaddress & 0x0000FFFF) >>> 8));
    sb.append(".");
    sb.append(String.valueOf((ipaddress & 0x000000FF)));
    return sb.toString();
  }

  public static final String byteToMAC(byte[] bytes, int off, int len) {
    StringBuilder sb = new StringBuilder();
    int done = off + len;
    for (int i = off; i < done; i++) {
      int temp = 0xFF & bytes[i];
      if (temp < 10) {
        sb.append("0" + temp);
      } else {
        sb.append(Integer.toHexString(temp));
      }
      if (i != done - 1) {
        sb.append(":");
      }
    }
    return sb.toString();
  }

  public static final char byteToChar(byte b) {
    return (char) b;
  }

  public static byte[] longToBytes(long x) {
    ByteBuffer buffer = ByteBuffer.allocate(8);
    buffer.putLong(0, x);
    return buffer.array();
  }

  public static String byteToString(byte[] b, int off, int len) {
    //        String hs = "";
    StringBuffer hs = new StringBuffer();
    String stmp = "";
    for (int n = 0; n < off + len; n++) {
      stmp = (Integer.toHexString(b[n] & 0XFF));
      if (stmp.length() == 1) {
        hs.append("0").append(stmp);
      } else {
        hs.append(stmp);
      }
    }
    return hs.toString();
  }

  public static final String byteToIpv6(byte[] bytes, int off, int len) {
    byte[] b = getByte(bytes, off, len);
    byte[] unsignedBytes = Arrays.copyOfRange(b, 1, b.length);
    if (b.length == 16) {
      unsignedBytes = b;
    }
    try {
      String ip = InetAddress.getByAddress(unsignedBytes).toString();
      return ip.substring(ip.indexOf('/') + 1).trim();
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  }
}

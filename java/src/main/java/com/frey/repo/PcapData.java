package com.frey.repo;

/**
 * @author zhouqisheng
 *         数据包头
 */
public class PcapData {
  private int time_s;//时间戳（秒）
  private String time_s_str;
  private int time_ms;//时间戳（微妙）
  private int pLength;//抓包长度
  private int length;//实际长度
  private byte[] content;//数据

  public String getTime_s_str() {
    return time_s_str;
  }

  public void setTime_s_str(String time_s_str) {
    this.time_s_str = time_s_str;
  }

  public int getTime_s() {
    return time_s;
  }

  public void setTime_s(int time_s) {
    this.time_s = time_s;
    this.time_s_str = DateUtil.unixTimeToDateString(time_s);
  }

  public int getTime_ms() {
    return time_ms;
  }

  public void setTime_ms(int time_ms) {
    this.time_ms = time_ms;
  }

  public int getpLength() {
    return pLength;
  }

  public void setpLength(int pLength) {
    this.pLength = pLength;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  @Override public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("time_s=").append(this.time_s);
    s.append("\ntime_ms=").append(this.time_ms);
    s.append("\npLength=").append(this.pLength);
    s.append("\nlength=").append(this.length);
    return null;
  }
}

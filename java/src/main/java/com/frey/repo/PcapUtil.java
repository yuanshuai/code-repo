package com.frey.repo;

import org.pcap4j.core.*;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;

import java.io.EOFException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

public class PcapUtil {
  public static void main(String[] args) {
    InetAddress addr = null;
    try {
      addr = InetAddress.getByName("10.67.7.51");
      PcapNetworkInterface nif = Pcaps.getDevByAddress(addr);
      int snapLen = 65536;
      PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
      int timeout = 10000;
      PcapHandle handle = nif.openLive(snapLen, mode, timeout);

      Packet packet = handle.getNextPacketEx();
      Thread.sleep(10000);
      handle.close();

      IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
      Inet4Address srcAddr = ipV4Packet.getHeader().getSrcAddr();
      System.out.println(srcAddr);

    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (PcapNativeException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    } catch (NotOpenException e) {
      e.printStackTrace();
    } catch (EOFException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }
    
}

package com.frey.repo;

import com.alibaba.fastjson.JSON;
import org.pcap4j.core.*;
import org.pcap4j.packet.*;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.packet.namednumber.IpNumber;

import java.io.IOException;

/**
 * Created by v1daddy on 16/8/6.
 */
public class Main {
  public static void main(String[] args)
      throws IOException, IllegalRawDataException, PcapNativeException, NotOpenException,
      InterruptedException {
    String pcapFilePath = "/Users/v1daddy/code/git/code-repo/java/src/main/resources/demo1.pcap";

    PcapHandle pcapHandle = Pcaps.openOffline(pcapFilePath, PcapHandle.TimestampPrecision.NANO);

    pcapHandle.loop(-1, new PacketListener() {
      public void gotPacket(Packet packet) {
        EthernetPacket ethernetPacket = packet.get(EthernetPacket.class);
        if (ethernetPacket.getHeader().getType().compareTo(EtherType.IPV4) == 0) {
          IpV4Packet ipV4Packet = ethernetPacket.getPayload().get(IpV4Packet.class);

          System.out.println("ip version: " + ipV4Packet.getHeader().getVersion().toString());

          System.out.println("protocol: " + ipV4Packet.getHeader().getProtocol().toString());

          System.out.println("src add: " + ipV4Packet.getHeader().getSrcAddr().getHostAddress());
          System.out.println("dst add: " + ipV4Packet.getHeader().getDstAddr().getHostAddress());

          if (ipV4Packet.getHeader().getProtocol().compareTo(IpNumber.TCP) == 0) {
            TcpPacket tcpPacket = ipV4Packet.getPayload().get(TcpPacket.class);
            System.out.println(JSON.toJSONString(tcpPacket.getHeader()));

          } else if (ipV4Packet.getHeader().getProtocol().compareTo(IpNumber.UDP) == 0) {
            UdpPacket udpPacket = ipV4Packet.getPayload().get(UdpPacket.class);
            System.out.println(JSON.toJSONString(udpPacket.getHeader()));
          }
        } else if (ethernetPacket.getHeader().getType().compareTo(EtherType.IPV6) == 0) {
          IpV6Packet ipV6Packet = ethernetPacket.getPayload().get(IpV6Packet.class);
          System.out.println("ip version: " + ipV6Packet.getHeader().getVersion().toString());
          System.out.println("src add: " + ipV6Packet.getHeader().getSrcAddr().getHostAddress());
          System.out.println("dst add: " + ipV6Packet.getHeader().getDstAddr().getHostAddress());
        }

        System.out.println();

      }
    });

    pcapHandle.close();

/*

    List<PcapData> pcapDataList = pcap.getData();

    for (PcapData pcapData : pcapDataList) {
      byte[] content = pcapData.getContent();

      System.out.println(content.length);

      int ipVersion = BinaryParserUtils.byte2int(content, 14, 1);

      AbstractPacket packet = null;

      if (ipVersion == 4) {
        IpV4Packet ipV4Packet = IpV4Packet.newPacket(content, 14, content.length-14);
        IpV4Packet.IpV4Header ipV4Header = ipV4Packet.getHeader();
//        ipV4Header.getProtocol().valueAsString()
      } else {
        packet = IpV6Packet.newPacket(content, 14, content.length-14);
      }

      System.out.println(JSON.toJSONString(packet));

    }


    System.out.println(JSON.toJSONString(pcap));

*/

  }

}

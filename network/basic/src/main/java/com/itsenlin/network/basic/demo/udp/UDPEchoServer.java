package com.itsenlin.network.basic.demo.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPEchoServer {
  private static final int ECHOMAX = 255;

  public static void main(String[] args) throws IOException {
    int port = 9999;
    if (args.length == 1) {
      port = Integer.parseInt(args[0]);
    }

    DatagramSocket socket = new DatagramSocket(port);
    DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

    while (true) {
      socket.receive(packet);
      System.out
          .println("Handling client at: " + packet.getAddress().getHostAddress() + ", on port: " + packet.getPort());

      System.out.println("\nReceived: " + new String(packet.getData()));
      socket.send(packet);
      packet.setLength(ECHOMAX);
    }
  }
}

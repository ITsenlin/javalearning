package com.itsenlin.network.basic.demo.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPEchoClientTimeout {
  private static final int TIMEOUT = 3000;
  private static final int MAX_RETRY = 5;

  public static void main(String[] args) throws IOException {
    if (args.length < 2 || args.length > 3) {
      throw new IllegalArgumentException("Parameters: <Server> <word> [<port>]");
    }

    InetAddress serverAddress = InetAddress.getByName(args[0]);

    byte[] bytesToSend = args[1].getBytes();
    int serverPort = (args.length == 3) ? Integer.parseInt(args[2]) : 9999;

    DatagramSocket socket = new DatagramSocket();

    socket.setSoTimeout(TIMEOUT);

    DatagramPacket sendPack = new DatagramPacket(bytesToSend, bytesToSend.length, serverAddress, serverPort);
    DatagramPacket receivePack = new DatagramPacket(new byte[bytesToSend.length], bytesToSend.length);

    int tries = 0;
    boolean receivedResponse = false;

    do {
      socket.send(sendPack);
      try {
        socket.receive(receivePack);
        if (!receivePack.getAddress().equals(serverAddress)) {
          throw new IOException("Received packet from an Unknown source!");
        }
        System.out.println("Received: " + new String(receivePack.getData()));
        receivedResponse = true;
      } catch (IOException e) {
        tries++;
        System.out.println("Timeout, " + (MAX_RETRY - tries) + "more retry...");
        e.printStackTrace();
      }
    } while (!receivedResponse && tries < MAX_RETRY);
    socket.close();
  }
}

package com.itsenlin.network.basic.demo.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPEchoServer {
  private static final int BUF_SIZE = 1024;
  public static void main(String[] args) throws IOException {
    int servPort = 8888;
    if (args.length == 1) {
      servPort = Integer.parseInt(args[0]);
    }

    ServerSocket serverSocket = new ServerSocket(servPort);
    int receiveMsgSize;
    byte[] receiveBuf = new byte[BUF_SIZE];

    while (true) {
      Socket socket = serverSocket.accept();
      SocketAddress clientAddr = socket.getRemoteSocketAddress();
      System.out.println("Handling client at " + clientAddr);

      InputStream is = socket.getInputStream();
      OutputStream os = socket.getOutputStream();

      while ((receiveMsgSize = is.read(receiveBuf)) != -1) {
        os.write(receiveBuf, 0, receiveMsgSize);
      }
      socket.close();
    }
  }
}

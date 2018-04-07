package com.itsenlin.network.nio.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPEchoClientNIO {
  public static void main(String[] args) throws IOException {
    if (args.length < 2 || args.length > 3) {
      throw new IllegalArgumentException("Parameters: <server> <word> [<port>]");
    }

    String server = args[0];
    byte[] data = args[1].getBytes();
    int port = (args.length == 3) ? Integer.parseInt(args[2]) : 8888;

    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.configureBlocking(false);

    if (!socketChannel.connect(new InetSocketAddress(server, port))) {
      while (!socketChannel.finishConnect()) {
        System.out.print(".");
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    ByteBuffer writeBuf = ByteBuffer.wrap(data);
    ByteBuffer readBuf = ByteBuffer.allocate(data.length);

    System.out.println("Connected to server...sending echo msg");

    int totalBytesRcvd = 0;
    int bytesRcvd;

    while (totalBytesRcvd < data.length) {

      if (writeBuf.hasRemaining()) {
        socketChannel.write(writeBuf);
      }
      if ((bytesRcvd = socketChannel.read(readBuf)) == -1) {
        throw new SocketException("Connection closed prematurely");
      }
      totalBytesRcvd += bytesRcvd;
    }

    System.out.println("Received: " + new String(readBuf.array()));
    socketChannel.close();
  }
}

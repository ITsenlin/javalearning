package com.itsenlin.network.multithread.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TCPEchoClientParallel {
  private static Executor executor = Executors.newFixedThreadPool(10);

  public static void main(String[] args) throws IOException {
    if (args.length < 2 || args.length > 3) {
      throw new IllegalArgumentException("Parameters: <server> <word> [<port>]");
    }

    String server = args[0];
    byte[] data = args[1].getBytes();
    int port = (args.length == 3) ? Integer.parseInt(args[2]) : 8888;

    for (int i = 0; i < 10; i++) {
      executor.execute(() -> {
        try (Socket socket = new Socket(server, port);) {

          System.out.println("Connected to server...sending echo msg");
          InputStream is = socket.getInputStream();
          OutputStream os = socket.getOutputStream();

          os.write(data);

          int totalBytesRcvd = 0;
          int bytesRcvd;

          while (totalBytesRcvd < data.length) {
            if ((bytesRcvd = is.read(data, totalBytesRcvd, data.length - totalBytesRcvd)) == -1) {
              throw new SocketException("Connection closed prematurely");
            }

            totalBytesRcvd += bytesRcvd;
          }

          System.out.println("Received: " + new String(data));
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    }
  }
}

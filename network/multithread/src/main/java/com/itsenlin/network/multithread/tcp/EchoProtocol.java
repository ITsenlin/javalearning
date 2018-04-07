package com.itsenlin.network.multithread.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoProtocol implements Runnable {
  private static final int BUF_SIZE = 1024;

  private Socket clientSocket;
  private Logger logger;

  public EchoProtocol(Socket socket, Logger logger) {
    this.clientSocket = socket;
    this.logger = logger;
  }

  public static void handleEchoClient(Socket socket, Logger logger) {

    try {
      InputStream is = socket.getInputStream();
      OutputStream os = socket.getOutputStream();

      int receiveMsgSize;
      int totalBytesReceived = 0;
      byte[] echoBuf = new byte[BUF_SIZE];

      while ((receiveMsgSize = is.read(echoBuf)) != -1) {
        os.write(echoBuf, 0, receiveMsgSize);
        totalBytesReceived += receiveMsgSize;
      }

      logger.info("Client " + socket.getRemoteSocketAddress() + ", echoed " + totalBytesReceived + " bytes");

    } catch (IOException e) {
      logger.log(Level.WARNING, "Exception in echo protocol", e);
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void run() {
    handleEchoClient(clientSocket, logger);
  }
}

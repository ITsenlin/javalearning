package com.itsenlin.network.multithread.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class TCPEchoServerExecutor {
  private static final int BUF_SIZE = 1024;
  private static final Logger logger = Logger.getLogger(TCPEchoServerExecutor.class.getName());
  private static Executor executor = Executors.newCachedThreadPool();
  public static void main(String[] args) throws IOException {
    int servPort = 8888;
    if (args.length == 1) {
      servPort = Integer.parseInt(args[0]);
    }

    ServerSocket serverSocket = new ServerSocket(servPort);

    while (true) {
      Socket socket = serverSocket.accept();
      executor.execute(new EchoProtocol(socket, logger));
    }
  }
}

package com.itsenlin.network.nio.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class TCPServerSelector {
  private static final int BUF_SIZE = 1024;
  private static final int TIMEOUT = 3000;

  public static void main(String[] args) throws IOException {
    int servPort = 8888;
    if (args.length == 1) {
      servPort = Integer.parseInt(args[0]);
    }

    Selector selector = Selector.open();

//    for (String arg : args) {
      ServerSocketChannel listnChannel = ServerSocketChannel.open();
      listnChannel.socket().bind(new InetSocketAddress(servPort));
      listnChannel.configureBlocking(false);
      listnChannel.register(selector, SelectionKey.OP_ACCEPT);
//    }

    TCPProtocol protocol = new EchoSelectorProtocol(BUF_SIZE);

    while (true) {
      if (selector.select(TIMEOUT) == 0) {
        System.out.print(".");
        continue;
      }

      Iterator<SelectionKey> iteratorKey = selector.selectedKeys().iterator();
      while (iteratorKey.hasNext()) {
        SelectionKey key = iteratorKey.next();

        if (key.isAcceptable()) {
          protocol.handleAccept(key);
        }

        if (key.isReadable()) {
          protocol.handleRead(key);
        }

        if (key.isValid() && key.isWritable()) {
          protocol.handleWrite(key);
        }

        iteratorKey.remove();
      }
    }
  }
}

package com.itsenlin.network.nio.tcp;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class EchoSelectorProtocol implements TCPProtocol {
  private int bufSize;

  public EchoSelectorProtocol(int bufSize) {
    this.bufSize = bufSize;
  }

  @Override
  public void handleAccept(SelectionKey key) throws IOException {
    SocketChannel channel = ((ServerSocketChannel)key.channel()).accept();
    channel.configureBlocking(false);
    channel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
  }

  @Override
  public void handleWrite(SelectionKey key) throws IOException {
    ByteBuffer byteBuffer = (ByteBuffer)key.attachment();
    byteBuffer.flip();
    SocketChannel channel = (SocketChannel)key.channel();
    channel.write(byteBuffer);
    System.out.println(byteBuffer.array());
    if (!byteBuffer.hasRemaining()) {
      key.interestOps(SelectionKey.OP_READ);
    }
    byteBuffer.compact();
  }

  @Override
  public void handleRead(SelectionKey key) throws IOException {
    SocketChannel channel = (SocketChannel)key.channel();
    ByteBuffer byteBuffer = (ByteBuffer)key.attachment();
    long bytesRead = channel.read(byteBuffer);
    if (bytesRead == -1) {
      channel.close();
    } else if (bytesRead > 0) {
      key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
      System.out.println(byteBuffer.array());
    }
  }
}

package com.itsenlin.websocket.demo.javaclient;

import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class ClientFromTomcat {
  public static void main(String[] args) {
    try {
      WebSocketContainer container = ContainerProvider
          .getWebSocketContainer(); // 获取WebSocket连接器，其中具体实现可以参照websocket-api.jar的源码,Class.forName("org.apache.tomcat.websocket.WsWebSocketContainer");
      String uri = "ws://localhost:8080/wsserver";
      Session session = container.connectToServer(Client.class, new URI(uri)); // 连接会话
      session.getBasicRemote().sendText("123132132131"); // 发送文本消息
      session.getBasicRemote().sendText("4564546");
      session.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

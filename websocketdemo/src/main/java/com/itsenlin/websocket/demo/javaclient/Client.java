package com.itsenlin.websocket.demo.javaclient;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class Client {

  @OnOpen
  public void onOpen(Session session) {
    System.out.println("Connected to endpoint: " + session.getBasicRemote());
  }

  @OnMessage
  public void onMessage(String message) {
    System.out.println(message);
  }

  @OnClose
  public void onClose() {
    System.out.println("Closed!");
  }

  @OnError
  public void onError(Throwable t) {
    t.printStackTrace();
  }
}
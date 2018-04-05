package com.itsenlin.javaspi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SPIApp {
  public static void main(String[] args) {
    ServiceLoader<Hello> loader = ServiceLoader.load(Hello.class);
    Iterator<Hello> iterator = loader.iterator();
    while (iterator.hasNext()) {
      Hello hello = iterator.next();
      System.out.println(hello.sayHello("jack"));
    }
  }
}

# 简介
SPI(Service Provider Interface)，java语言在jdk层提供的通用的接口与实现分离机制。用户可以使用此机制很方便的替换不同的实现。

# 实现方式

* 创建接口，一般是iterface类
* 另一个用户在开发相应实现时：
  * 创建文件：META-INF/services/<interface类全名>
  * 在<interface类命名>命名的文件中将实现类全名记入
* 使用时使用java提供的api  ServiceLoader
  ```$java
  ServiceLoader<Hello> loader = ServiceLoader.load(Hello.class);
  Iterator<Hello> iterator = loader.iterator();
  while (iterator.hasNext()) {
    Hello hello = iterator.next();
    System.out.println(hello.sayHello("jack"));
  }
  ```
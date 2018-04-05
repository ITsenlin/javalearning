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
  
# 典型应用场景
1. common-logging    apache最早提供的日志的门面接口。只有接口，没有实现。具体方案由各提供商实现， 发现日志提供商是通过扫描 META-INF/services/org.apache.commons.logging.LogFactory配置文件，通过读取该文件的内容找到日志提工商实现类。只要我们的日志实现里包含了这个文件，并在文件里制定 LogFactory工厂接口的实现类即可。    
2. jdbc    jdbc4.0以前， 开发人员还需要基于Class.forName("xxx")的方式来装载驱动，jdbc4也基于spi的机制来发现驱动提供商了，可以通过META-INF/services/java.sql.Driver文件里指定实现类的方式来暴露驱动提供者.
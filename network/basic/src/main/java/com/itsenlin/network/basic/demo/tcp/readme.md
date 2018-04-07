# TCP客户端开发步骤
* 创建一个Socket实例：构造函数向指定的远程主机和端口（即TCP服务端）建立TCP连接
* 通过套接字的输入、输出流（I/O streams）进程通信：一个Socket连接实例包括一个InputStream和一个OutputStream。
* 使用Socket类的close方法关闭连接。

# TCP服务端开发步骤
* 创建一个ServerSocket实例并指定本地端口。此套接字的功能是侦听该指定端口收到的连接。
* 重复执行下面步骤
  * 调用ServerSocket的accept()方法以获取下一个客户端连接。基于客户端连接，创建一个Socket实例，并由accept()方法返回。
  * 使用返回的Socket实例的InputStream和OutputStream与客户端通信。
  * 通信完成后，调用Socket类的close()方法关闭连接。
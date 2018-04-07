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
  
  
# UDP客户端开发步骤
* 创建一个DatagramSocket实例，可以选择对本地地址和端口号进行设置。
* 使用DatagramSocket类的send()和receive()方法来发送和接收DatagramPacket实例，进行通信。
* 通信完成后，使用DatagramSocket类的close()方法关闭套接字。

>注意：与Socket类不同DatagramSocket实例在创建时不需要指定目的地址

# UDP服务端开发步骤
* 创建一个DatagramSocket实例，可以选择本地端口号，并可以选择指定本地地址。此时，服务器已经准备好从任何客户端接收数据报文。
* 使用DatagramSocket的receive()方法来接收一个DatagramPacket实例。当receive()方法返回时，数据报文就包含了客户端的地址，这样我们就知道了回复信息的地址。
* 使用DatagramSocket类的send()和receive()方法来发送和接收DatagramPacket实例，进行通信。
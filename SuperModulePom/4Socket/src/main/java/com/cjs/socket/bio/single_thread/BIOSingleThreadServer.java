package com.cjs.socket.bio.single_thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 阻塞IO模型下的多线程模型.
 *
 * 缺点: 服务器只能处理一个请求, 在处理请求的时候不会接收来自其他客户端的请求.
 */
public class BIOSingleThreadServer {
    public static void main(String[] args) {
        byte[] buffer = new byte[1024];
        try {
            ServerSocket socket = new ServerSocket(2000);
            while (true) {
                System.out.println("服务器开始接受连接...");
                //   第一次阻塞[等待连接], 客户端还没连接服务器, 由于服务器调用了accept方法, 会导致主线程一直被block;
                // 直到客户端请求连接服务器.
                Socket accept = socket.accept();
                System.out.println("服务器收到连接...");

                System.out.println("服务器等待数据...");
                //   第二次阻塞[等待数据], 客户端收到连接, 等待客户端发数据, 如果客户端没发数据, 那么服务器将一直阻塞等待客户端发送数据.
                int bufferSize = accept.getInputStream().read(buffer);
                if (bufferSize > 0) {
                    System.out.println("服务器接收到数据...");
                    System.out.println("数据为: " + new String(buffer, 0, bufferSize));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

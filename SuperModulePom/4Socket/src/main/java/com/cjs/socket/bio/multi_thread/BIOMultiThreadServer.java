package com.cjs.socket.bio.multi_thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO[同步阻塞I/O模型]的多线程实现, 为每一个Socket连接创建一个线程来处理.
 *
 *  弊端:
 *    1. 如果有大量的请求连接到我们的服务器, 却不发送消息, 那么服务器也会为这些连接请求创建一个单独的线程; 这样会导致连接数过大对服务器造成巨大的压力.
 *  优点:
 *    1. 解决了单线程BIO无法处理并发的弱点.
 *
 *  NIO两者兼备; 在单线程运行的状态下也能处理并发的请求.
 */
public class BIOMultiThreadServer {
    public static void main(String[] args) {
        final byte[] buffer = new byte[1024];
        try {
            ServerSocket socket = new ServerSocket(2000);
            while (true) {
                System.out.println("服务器开始接受连接...");
                // 改进: 每个线程处理一个连接请求.
                final Socket accept = socket.accept();
                new Thread(() -> {
                    System.out.println(Thread.currentThread() + ":服务器收到连接...");

                    System.out.println(Thread.currentThread() + ":服务器等待数据...");
                    int bufferSize = 0;
                    try {
                        bufferSize = accept.getInputStream().read(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (bufferSize > 0) {
                        System.out.println(Thread.currentThread() + ":服务器接收到数据...");
                        System.out.println(Thread.currentThread() + ":数据为: " + new String(buffer, 0, bufferSize));
                    }
                }).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

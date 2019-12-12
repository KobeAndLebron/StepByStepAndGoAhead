package com.cjs.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * NIO模式服务器的多线程处理.
 *
 * 缺点: 在语言级不断的轮询数据是否准备就绪, 效率不高.
 */
public class NIOServer {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(2000));

            while (true) {
                // 立即返回, 不会被阻塞.
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel == null) {
                    // 表示没人连接
                    System.out.println("正在等待客户端请求连接...");
                    Thread.sleep(5000);
                } else {
                    System.out.println("当前接收到客户端请求连接...");
                }

                // 缺点: 用户还没来得及输入数据, 服务器就会去接收新的请求, 这样会导致旧的连接丢失.
                /**
                 * 解决方案: 将{@code socketChannel}缓存起来, 每次等待客户端消息时, 都会轮询之前所有已经建立连接的socket.
                 * 这样完美解决了BIO单线程无法处理并发请求的问题, 并且解决了连接丢失的问题.
                 */
                if (socketChannel != null) {
                    // 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    byteBuffer.flip(); // 切换模式  写-->读
                    // 立即返回, 不会被阻塞.
                    int effective = socketChannel.read(byteBuffer);
                    if (effective != 0) {
                        String content = Charset.forName("utf-8").decode(byteBuffer).toString();
                        System.out.println(content);
                    } else {
                        System.out.println("当前未收到客户端消息");
                    }
                }
            }


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}

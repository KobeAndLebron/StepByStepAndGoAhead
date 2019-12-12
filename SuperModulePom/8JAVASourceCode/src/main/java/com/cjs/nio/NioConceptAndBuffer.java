package com.cjs.nio;

import java.nio.IntBuffer;

/**
 * IO是面向流(Stream)编程的, 流是字节的载体. 分为输入流和输出流, 一个流不可能既是输入流, 又是输出流.
 * NIO是面向块或缓冲区(Block)编程的. 与Stream不同的是, 一个流只可能是InputStream或OutputStream, 而Channel是双向的, 因此它更能够反应操作系统的底层真实情况.
 *
 * IO是阻塞的.
 * NIO是非阻塞的.
 *
 * -------------------------------------
 *
 * 三个核心概念: Selector-选择器 Channel-通道 Buffer-缓冲区.
 *
 * Channel: 读取/写入数据的抽象, 所有数据的读写都是通过Buffer来进行的, 永远不会出现直接读/写Channel的情况.
 *
 * Buffer: 本身是一块内存, 底层实现是一个数组. 数据的读/写都是通过Buffer来实现的[通过flip来切换读写模式].
 *
 * Selector: 允许一个单独的线程同时监视多个Channel, 然后用一个单独的线程来"选择"已经就绪的通道. 为单线程处理多个通道提供了可能.
 *
 * 三个概念的关系: 一个Thread有一个Selector, Selector连接多个Channel[靠事件切换, 可实现一个线程处理大量连接的场景], 每个Channel对应一个Buffer.
 *
 * ------------------------------------
 *
 * Buffer实现:
 *  通过一个数组加三个重要变量来实现:
 *      position: 下一个能读/写的index, 初始值为0.
 *      limit: 不能写/读的第一个index, 初始值等于capacity.
 *      capacity: 容量, 不可变.
 *      这三个变量的不变量关系: 0 <= mark <= position <= limit <= capacity.
 *  三个变量的具体变换关系查看下面的程序.
 *
 */
public class NioConceptAndBuffer {
    public static void main(String[] args) {
        // 底层的数据结构: int[] hb = new int[10];
        IntBuffer intBuffer = IntBuffer.allocate(10);
        // p: position. l: limit. c: capacity.
        // Buffer流程1: 初始化状态, p = 0, l = c = 10.
        System.out.println("Initial state: " + intBuffer);

        for (int i = 0; i < 5; i++) {
            // Buffer流程2: 写intBuffer, p++.
            intBuffer.put(i);
        }

        // p=5.
        System.out.println("After 5 put: " + intBuffer);

        intBuffer.flip();

        // Buffer流程3: 切换到读模式, p=0, l=p.
        System.out.println("After flipping: " + intBuffer);

        // remaining = l - p;
        while (intBuffer.remaining() > 0) {
            System.out.println("Read: " + intBuffer);
            // Buffer流程4: 读intBuffer, p++.
            System.out.println(intBuffer.get());
        }

        // Buffer流程5: 读完数据, p == l == 5.
        System.out.println("Final State: " + intBuffer);

        // 此时position=limit, 已经不允许再放入数据. 抛出异常.
        intBuffer.put(1);
        // 需要clear后才能进行下一阶段的写/读.

        // Buffer流程6: 清空Buffer: p=0, l=c. 可以进入写一个写Buffer的流程了.
        // NOTE: 只是改变了下标, 并未改变内部数组的内容.
        intBuffer.clear();
    }
}

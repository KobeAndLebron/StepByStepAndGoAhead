package com.cjs.block_queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列
 *    用于生产者消费者模式 线程池 消息中间件
 *
 * ----------
 *
 * 阻塞队列API:
 *  1. 抛出异常类型.
 *  2. 返回True/False. offer/
 *  3. 阻塞型方法: put/take.
 *  4. 超时型API
 */
public class BlockingQueueDemo {
    private static BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

    public static void main(String[] args) throws InterruptedException {
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        blockingQueue.put("d");
        // System.out.println(blockingQueue.add("d"));

        System.out.println(blockingQueue.element());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

    }
}

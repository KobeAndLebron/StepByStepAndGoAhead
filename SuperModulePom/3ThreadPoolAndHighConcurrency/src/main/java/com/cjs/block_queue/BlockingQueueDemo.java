package com.cjs.block_queue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * 阻塞队列继承关系
 * BlockingQueue<E> extends Queue<E>
 * Queue<E> extends Collection<E>
 *
 * ----------
 *
 * 阻塞队列
 *    用于生产者消费者模式 线程池 消息中间件
 *
 * ----------
 *
 * 阻塞队列API:
 *  1. 抛出异常类型.
 *  2. 返回True/False. offer/
 *  3. 阻塞型方法: put/take. 这两个方法实现生产者消费者模式
 *  4. 超时型API
 *
 *  ----------
 *
 *  ArrayBlockingQueue实现:
 *    1. 数组: final Object[] items;
 *    2. ReentrantLock: final ReentrantLock lock = new ReentrantLock(fair);
 *    3. 两个Lock的Condition: notEmpty(Condition for waiting takes) 和 notFull;
 *    notEmpty = lock.newCondition(); notFull = lock.newCondition();
 *
 *    notEmpty: take时队列为空时, 调用notEmpty.await(); 在put时队列未满时, 调用notEmpty.signal().
 *    notFull则相反.
 *
 */
public class BlockingQueueDemo {
    private static BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

    public static void main(String[] args) throws InterruptedException {
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        // blockingQueue.put("d");
        // System.out.println(blockingQueue.add("d"));

        System.out.println(blockingQueue.element());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());

        Producer producer = new Producer(blockingQueue);
        Consumer consumer = new Consumer(blockingQueue);

        //创建5个生产者，5个消费者
        for (int i = 0; i < 10; i++) {
            if (i < 5) {
                new Thread(producer, "producer" + i).start();
            } else {
                new Thread(consumer, "consumer" + (i - 5)).start();
            }
        }

        Thread.sleep(1000);
        producer.shutDown();
        consumer.shutDown();
        Thread.sleep(2000);
        System.out.println("---end ---");


    }

    public static class Producer implements Runnable {
        BlockingQueue<String> blockingQueue;
        Random random = new Random();
        boolean shutDownFlag = false;

        public Producer(BlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            while (!shutDownFlag) {
                int i = random.nextInt(100);
                try {
                    blockingQueue.put(i + "");
                    System.out.println("Producer " + Thread.currentThread().getName() + " produce " + i);
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void shutDown() {
            this.shutDownFlag = true;
        }
    }

    public static class Consumer implements Runnable {
        BlockingQueue<String> blockingQueue;
        boolean shutDownFlag = false;

        public Consumer(BlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            while (!shutDownFlag) {
                try {
                    String take = blockingQueue.take();
                    System.out.println("Consumer " + Thread.currentThread().getName() + " consume " + take);
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        public void shutDown() {
            this.shutDownFlag = true;
        }
    }
}

package com.cjs.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异步编排
 */
public class CompletableFutureTest {
    public static ExecutorService ex = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture.runAsync(() ->{
            System.out.println("当前线程：" + Thread.currentThread().getName());
            int i = 10 / 2;
            System.out.println("运行结果: " + i);
        }, ex);

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            int i = 10 / 2;
            System.out.println("运行结果: " + i);
            return i;
        }, ex);

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            int i = 10 / 1;
            System.out.println("运行结果: " + i);
            return i;
        }, ex).whenComplete((res, exc) -> {
            System.out.println("whenComplete线程ID： " + Thread.currentThread().getName());
            System.out.println("输出结果: " + res + ", 异常: " + exc);
        }).exceptionally((exc) -> {
            return 11;
        });

        Integer integer = future1.get();
        System.out.println("Main结果" + ": " + integer);

        线程串行化();
    }


    public static void 线程串行化() throws ExecutionException, InterruptedException {
        System.out.println("线程串行化 start...");
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            int i = 10 / 1;
            System.out.println("运行结果: " + i);
            return i;
        }, ex).thenApply((lastRes) -> { // 当一个线程A依赖另一个线程B，A既能感应到上一步B返回结果， A还有返回结果。
            System.out.println("线程串行化 线程ID： " + Thread.currentThread().getName());
            System.out.println("输出结果: " + lastRes);
            return lastRes - 1;
        }).exceptionally((exc) -> {
            return 10;
        });
        System.out.println("线程串行化 end...  结果" + future1.get());
    }
}

package com.cjs.gc;

/**
 *
 * VM启动参数:
 *    -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+UseParallelGC
 *
 *  -------
 *
 *  [Full GC (Ergonomics) [PSYoungGen: 544K->0K(9216K)] [ParOldGen: 6152K->6525K(10240K)] 6696K->6525K(19456K), [Metaspace: 2956K->2956K(1056768K)], 0.0051927 secs] [Times: user=0.03 sys=0.00, real=0.00 secs]
 *
 * --------
 * 
 * FullGC发生的原因, Ergonomics[人体工程学]:
 *   当垃圾收集器发现讲过一次MinorGC后, 如果晋升到老生代的平均大小大于老生代的剩余大小，
 * 则会进行一次FullGC. 因为虚拟机估算出下次分配可能会发生无法分配的问题，于是提前预测到可能的问题，提前发生一次full gc。
 *
 *  -------
 *
 *  FullGC的时候回将YoungGen OldGen MetaSpace都进行回收[除CMS收集器的Major GC外].
 */
public class FullGCLogAnalyze {
    public static void main(String[] args) {
        // size个字节为1MB.
        int size = 1024 * 1024;

        byte[] myAlloc1 = new byte[2 * size];
        System.out.println("1");
        byte[] myAlloc2 = new byte[2 * size];
        System.out.println("2");
        byte[] myAlloc3 = new byte[2 * size];
        System.out.println("3");
        byte[] myAlloc4 = new byte[2 * size];
        System.out.println("4");

        System.out.println("Hello World");
    }
}

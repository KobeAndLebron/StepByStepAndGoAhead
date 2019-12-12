package com.cjs.gc;

/**
 * VM启动参数:
 *   -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC
 *
 *   堆内存20M, 新生代10M, Eden区8M, S0和S1各1M.
 *
 *   使用CMS收集器, 以更短的回收停顿时间为目标, 将用时最长的并发标记和并发清理过程与用户线程并发进行.
 *
 * ------------------
 *
 * 老年代的CMS默认情况下是和ParNew收集器使用的.
 * 在HotSpot vm中, 除了CMS垃圾收集器外, 其余老年代收集器都会对整个堆进行回收, 包括新生代.
 *
 * -----------------
 *
 * Major触发时机:
 *  1. 阈值检查机制: 当OldGen区内存超过一定比例时[-XX:CMSInitiatingOccupancyFraction], 会触发MajorGC.
 *  2. 动态检查机制：JVM会根据最近的回收历史，估算下一次老年代被耗尽的时间，快到这个时间的时候就启动一个并发周期. 这个机制为了避免
 *  并发模式失败的问题. 设置UseCMSInitiatingOccupancyOnly这个参数可以将这个特性关闭.
 *
 * ----------------
 *
 * CMS问题:
 *
 * 1. Concurrent mode failure:
 * 因为并发清清除阶段和用户线程是同步进行的，所以CMS会预留一部分空间供YoungGen晋升上的对象使用【内存担保机制或大对象】，
 * 当预留空间不足以容纳晋升对象时，就会触发一次`Full GC`， 使用Serial Old收集器候补.
 * 解决办法:
 *  1. 增大老年代内存, 使得预留空间足够防止MajorGC期间晋升的对象.
 *  2. 减少CMSInitiatingOccupancyFraction, 提前触发MajorGC, 但这也会增加MajorGC的评率.
 *
 *
 * 2. 内存碎片问题. 通过参数配置来解决:
 *  -XX:+UseCMSCompactAtFullCollection, 在FullGc的时候, 开启碎片整理.
 *  -XX:CMSFullGCsBeforeCompaction ：设置执行多少次不压缩的full GC后来一次带压缩的GC，默认为0（每次都压缩).
 *
 * -----------------
 *
 *  其余详细内容和调优看有道云笔记的内容.
 */
public class CMSGC {
    public static void main(String[] args) {
        // size个字节为1MB.
        int size = 1024 * 1024;

        byte[] myAlloc1 = new byte[4 * size];
        System.out.println("1");
        byte[] myAlloc2 = new byte[3 * size];
        System.out.println("2");
        byte[] myAlloc3 = new byte[4 * size];
        System.out.println("3");
        byte[] myAlloc4 = new byte[2 * size];
        System.out.println("4");

        System.out.println("Hello World");
    }
}
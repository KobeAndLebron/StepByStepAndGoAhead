package com.cjs.gc;

/**
 * 配置JVM启动参数:
 *  -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 *
 * -----------------------
 *
 * 得到MinorGc的日志:
 *
 * 1
 * 2
 * [GC (Allocation Failure) [PSYoungGen: 5445K->592K(9216K)] 5445K->4696K(19456K), 0.0027097 secs]
 * [Times: user=0.02 sys=0.00, real=0.00 secs]
 * 3
 *
 *
 * 解释:
 *  GC: Minor GC, 又称Young GC.
 *  Allocation Failure: 触发GC的原因, 表示因Eden快满而放不下新对象的情况[YGC时机].
 *  [PSYoungGen: 5445K->592K(9216K)]:
 *      PSYoungGen: Parallel Scavenge收集器, 以吞吐量优先的垃圾收集器.
 *      5445K->592K(9216K): YGC前Young Generation的大小变化, 相减为释放的大小. 5335-592=4743[正好差不多4个字节, 说明释放了4个字节, 但是这四个字节又不可能被垃圾回收, 所以直接将2个2字节数组放入了OldGen].
 *        9216k: 年轻代的内存大小, YoungGen=S0+S1+Eden.
 *        为何少1k? 因为任意时刻总有一个survivor为空, 所以年轻代的实际大小要减1k.
 *
 *      5445K->4696K(19456K): YGC前总堆内存的大小, 相减为总堆释放的大小. 5445-4696=749.
 *
 * -----------------------
 *
 *
 *  执行完毕打印内存占用比例:
 *      Heap
 *  PSYoungGen      total 9216K, used 3987K [0x00000007bf600000, 0x00000007c0000000, 0x00000007c0000000)
 *   eden space 8192K, 41% used [0x00000007bf600000,0x00000007bf950cd8,0x00000007bfe00000)
 *   from space 1024K, 57% used [0x00000007bfe00000,0x00000007bfe94010,0x00000007bff00000)
 *   to   space 1024K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007c0000000)
 *  ParOldGen       total 10240K, used 4104K [0x00000007bec00000, 0x00000007bf600000, 0x00000007bf600000)
 *   object space 10240K, 40% used [0x00000007bec00000,0x00000007bf002020,0x00000007bf600000)
 *  Metaspace       used 2933K, capacity 4496K, committed 4864K, reserved 1056768K
 *   class space    used 320K, capacity 388K, committed 512K, reserved 1048576K
 *
 *  解释: eden + (from or to) = 8192 + 1024 = 9216. 等于Young Gen的总大小.
 *       ParOldGen total 10240k, 总内存为10M, 算法: Xmx - Xmn = 20 - 10 = 10M.
 *                 used 4104K, 已使用4104K-2个2字节数组. 上述得到两个值4743和749, 相减得4104[YoungGen Promote to OldGen的大小, 内存担保机制].
 *                 原因: 前者代表GC期间YoungGen释放的内存大小, 后者代表GC期间堆释放的内存大小; 年轻代的内存除了真正被清除的内存, 还有一部分被promote到Old Gen, 相减则为年轻代移入老年代的大小, 即老年代已使用内存大小.
 *
 * -------------
 *
 * 内存担保机制:
 *    1. 进行Minor GC的时候，把Survivor无法容纳的对象直接放入OldGen。
 *    2. 新对象大小大于Eden区的大小, 直接将对象放入到OldGen.[很少发生]
 *
 * -------------
 *
 * 当不指定垃圾收集器的相关参数时, 使用`-XX:+UseParallelGC`, 即Parallel Scavenge + Parallel Old的组合.
 */
public class YGCLogAnalyze {
    public static void main(String[] args) throws InterruptedException {
        // size个字节为1MB.
        int size = 1024 * 1024;

        byte[] myAlloc1 = new byte[2 * size];
        System.out.println("1");
        byte[] myAlloc2 = new byte[2 * size];
        System.out.println("2");
        // 当分配三的时候, 发现Eden已经放不下了, 此时触发Minor GC, 在GC期间, 由于
        // S0容纳不下2M的空间, 所以根据`内存担保机制`, 会把1和2直接移入老年代.
        byte[] myAlloc3 = new byte[3 * size];
        System.out.println("3");

        System.out.println("Hello World");
    }
}

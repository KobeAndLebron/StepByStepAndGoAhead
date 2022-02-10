package com.cjs.memory_link;

import java.util.LinkedList;
import java.util.List;

/**
 * 内存泄漏: 不再会被程序使用的对象的内存不能被GC.
 *  长期的内存泄漏累积下来最终会导致内存溢出, 但是内存溢出的原因有很多. 比如堆内存溢出 栈内存溢出 永久代溢出等.
 *
 * 常见导致原因:
 *  1. ThreadLocal+稳定运行的线程池, 只要线程存在, 那么ThreadLocalMap就会存在, 每个ThreadLocal变量对应的对象将不会被回收,
 *  即使他们已经不再使用.
 *  2. 实现Closeable接口方法的对象未关闭,(Stream: 文件 网络, 未关闭的数据库连接).
 *  3. 对象分配在了JVM的GC不可达的区域, 比如本地方法分配的内存({@linkplain sun.misc.Unsafe#allocateMemory(long)}.
 *  4. 热部署的时候, 会动态加载类, 如果限制JVM的GC不对class进行回收, 那么将会内存泄漏.
 *  5. 长生命周期的对象持有短生命周期的引用，就很可能会出现内存泄露.
 *
 *
 *  ----------------
 *  解决办法:
 *      1. 定位到Java进程的PID.
 *      2. 使用jmap命令获取内存映射图: jmap -dump:live,format=b,file=heap.hprof ${PID}
 *          2.1 或者使用jmap -hive ${PID}, 获取实时的对象实例情况.
 *      4. 使用jhat命令来分析jmap生成的文件: jhat heap.hprof
 *      5. 通过以上步骤, 就可以看到每个类实例的实例数量及占用的总内存大小(字节). 对于占用内存过大的类或者实例数过多的类, 找到代码位置, 进行分析.
 *
 */
public class MemoryLinkExample {
    private static final List<String> list = new LinkedList<>();

    public static void main(String[] args) {
        String temp = "temp";
        list.add(temp);
        // Temp不会被回收. 长生命周期的引用持有短生命周期的引用.
        temp = null;
    }
}

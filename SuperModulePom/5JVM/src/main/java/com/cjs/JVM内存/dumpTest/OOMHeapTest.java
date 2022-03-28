package com.cjs.JVM内存.dumpTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 线上出现内存占用过高的问题：
 * 1. jmap -histo pid | head -20， 获取内存占用最大的20个类，这个对线上影响较小。
 * 2. jmap -dump:format=b,file=xxx pid，线上系统，内存特别大，jmap执行期间会对进程产生很大影响，甚至卡顿（电商不适合）
 *      1：设定了参数HeapDump，OOM的时候会自动产生堆转储文件 ：  -XX:+HeapDumpOnOutOfMemoryError
 *      2：<font color='red'>很多服务器备份（高可用），停掉这台服务器对其他服务器不影响</font>
 *      > 我有很多服务器的备份，当时定位这台服务器有问题，先将这台服务器和其他服务器隔离开，再用jmap导出，然后观察。
 *      3：在线定位(一般小点儿公司用不到)，arthas工具。 暂时不了解 TODO
 */
public class OOMHeapTest {
    public static void main(String[] args){
        oom();
    }
    
    private static void oom(){
        Map<String, Pilot> map = new HashMap<String, Pilot>();
        Object[] array = new Object[1000000];
        for(int i=0; i<1000000; i++){
            String d = new Date().toString();
            Pilot p = new Pilot(d, i);
            map.put(i+"rosen jiang", p);
            array[i]=p;
        }
    }
}
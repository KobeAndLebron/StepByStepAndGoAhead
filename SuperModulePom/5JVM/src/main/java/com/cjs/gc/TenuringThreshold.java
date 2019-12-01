package com.cjs.gc;

/**
 * -Xmx200M -Xmn50m -XX:TargetSurvivorRatio=60 -XX:+PrintGCDetails -XX:+PrintGCDateStamps
 * -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:MaxTenuringThreshold=3 -XX:+PrintTenuringDistribution
 *
 * YoungGen50M, 按默认比例8:1:1, Eden区为40M, S0和S1均为5M.
 *
 * ---------
 *
 * -XX:+PrintGCDateStamps 打印出GC发生的时间点.
 *
 * -XX:MaxTenuringThreshold: 对象经过多少次MinorGC后晋升到老年代的最大阈值, 在JVM以4个bit存储在对象头中, 所以
 * 最大值为15.
 * 但这也并非意味着一定达到15次才进入Old; 例如, 当Survivor不够的时候, 便会触发内存担保机制.
 *
 * -XX:TargetSurvivorRatio：计算期望S区存活大小(Desired survivor size)的参数。默认值为50，即50%。
 * 当一个S区中所有的age对象的大小如果大于等于Desired survivor size[按从小到大排序]，则会重新计算threshold，
 * 以age和MaxTenuringThreshold两者的最小值为准。
 *
 * -XX:+PrintTenuringDistribution 按age大小依次打印出Survivor每个age所占内存大小.
 *
 * ----------
 *
 *  动态计算TenuringThreshold:
 *      将Survivor的对象按age从小到大排列, 然后当age的累计大小超过Desired Survivor Size时, 就会重新计算Threshold.
 *      Desired Survivor Size = Surivovor size * -XX+TargetSurvivorRatio.
 *
 *  TheuringThreshold不变的缺点:
 *      过小会导致大量的短期对象晋升到OldGen, 引起频繁的MajorGc, 从而使分带回收失去意义.
 *      过大会导致原本应该晋升的对象一直停留在Survivor区, 最后会触发内存担保机制, Eden+Svuvivor中对象将不再依据年龄全部提升到老年代，这样对象老化的机制就失效了.
 *
 *      因为相同应用在不同时间的表现不同, 会导致对象的生命周期发生波动.
 *
 * ----------
 *
 *  CMS只能与ParNew组合, 不能和Serial组合(-XX:+UseSerialGC), 否则会冲突: Conflicting collector combinations in option list
 *
 * ------------
 */
@SuppressWarnings("ALL")
public class TenuringThreshold {
    public static int GC_NUMBER = 0;

    public static void main(String[] args) throws InterruptedException {
        int size = 1024 * 1024;
        // main方法作为主线程，变量不会被回收
        byte[] byte1 = new byte[size];
        byte[] byte2 = new byte[size];

        // byte1和byte2的age+1, 等于1.
        YGC(40, size);
        Thread.sleep(3000);

        // byte1和byte2的age+1, 等于2.
        YGC(40, size);
        Thread.sleep(3000);

        // byte1和byte2的age+1, 等于3. 达到TenuringThreshold(3), 将在下一次GC晋升.
        YGC(40, size);
        Thread.sleep(3000);

        // byte1和byte2晋升到OldGen.
        YGC(40, size);
        Thread.sleep(3000);

        // 达到TargetSurvivorRatio这个比例指定的值,即5M(S区)*60%(TargetSurvivorRatio)=3M(Desired survivor size)
        byte[] byte4 = new byte[size];
        byte[] byte5 = new byte[size];
        byte[] byte6 = new byte[size];

        // 这次ygc时, Eden区的对象会挪到Survivor, 由于S区age=1的大小已经占用达到了60%(-XX:TargetSurvivorRatio=60),
        // 所以会重新计算对象晋升的min(age, MaxTenuringThreshold) = 1, 会在下一次GC前将threshold大于等于1的移入OldGen.
        YGC(40, size);
        Thread.sleep(3000);

        // 由于前一次ygc时算出age=1, 所以这一次再ygc时, byte4, byte5, byte6就要晋升到Old,
        // 而不需要等MaxTenuringThreshold这么多次, 此次ygc后, 对象全部晋升到old.
        YGC(40, size);
        Thread.sleep(3000);

        System.out.println("GC end!");
    }

    // 塞满Eden区，局部变量会被回收, 作为触发GC的小工具.
    private static void YGC(int edenSize, int size) {
        System.out.println("第" + ++GC_NUMBER + "次Minor GC");
        for (int i = 0; i < edenSize; i++) {
            byte[] byte1m = new byte[size];
        }
        System.out.println();
    }


}

package com.cjs.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CAS Compare And Swap, 比较并交换, 是CPU的一个指令, 且是一个原子操作.
 * 原理: 比较当前工作内存中的值和主内存中的值, 如果相同则执行操作. 否则返回False.
 * JAVA中通过 java.util.concurrent.atomic 包下面的原子类进行CAS操作(atomic包下的类调用Unsafe类中的JNI方法来调用)
 *
 *  -----------
 *
 *  AtomicInteger为什么能保证原子性: Unsafe+CAS.
 *  Unsafe: 用于获取内存中volatile变量的值. 位于rt.jar包下的sun.misc包内; 用于调用操作系统的CAS原语.
 *  CAS: 如果当前工作内存的值与主内存的值不一样, 则证明值已经被其他线程修改, 此时将再次获取主内存的值[可能刚获取到主内存的值后线程又被挂起, 所以需要循环比较, 即自旋锁],
 *  然后再进行比较, 直到相等才将主内存的value加一.
 *
 *  使用到的Unsafe核心方法:
 *   public final int getAndAddInt(Object atomicIntegerRef, long valueOffset, int increment) {
 *          int var5;
 *          do {
 *              // 获取主内存atomicInteger对象中的value的值.
 *              var5 = this.getIntVolatile(atomicIntegerRef, valueOffset);
 *
 *              // 执行完此步骤, 线程还是有可能被挂起.
 *
 *              // 当主内存中的值和当前工作内存的值相同时, 则证明此变量没有被修改过, 执行var5+1,
 *              // 否则说明此变量被其他线程修改过了, 返回false, 继续循环.
 *          } while(!this.compareAndSwapInt(atomicIntegerRef, valueOffset, var5, var5 + increment));
 *
 *          return var5;
 *      }
 *
 *  ----------
 *
 *  Synchronized和CAS的区别:
 *      Synchronized: 加锁, 悲观锁, 线程会阻塞, 降低应用并发性; JMV级别的原子性.
 *      CAS: 不会加锁, 乐观锁, 线程不被阻塞; 硬件级别的原子性[操作系统的一个指令, 原语.].
 *
 *      悲观锁: 假定会发生冲突, 访问的时候都要先加锁. 保证同一时刻只有线程获得锁, 读读也会阻塞.
 *      乐观锁: 假定不会发生冲突, 只有在提交的时候才会检测冲突. 读读不会阻塞.
 *
 *      补充: Mysql中的乐观锁用MVCC实现. 悲观锁通过Select *** for update/lock in share mode(写锁/读锁).
 *
 *  -----------
 *
 *
 * CAS缺点:
 *  1. 如果CAS失败了[线程冲突严重], 则产生自旋时间过长, 会产生CPU使用率飙升的情况. 进而导致吞吐量下降.
 *  2. CAS只能保证一个共享变量的线程安全, 如果需要保证多个共享变量的线程安全则需要锁.
 *  3. ABA问题.
 *
 *   -------------
 *
 * ABA问题({@link ABAProblemDemo}:
 *    假设存在线程1和线程2共同操作共享变量var, 刚开始线程1和线程2都读到工作内存var的值为A(do while循环内),
 *  由于线程1的优先级高, CPU将先执行线程1, 将工作内存的值更新为B, 更新完B之后, 还是线程1获得CPU, 又将工作内存的值更新为A, 线程1结束.
 *  这时候线程2获得调度, 发现A==A, do while循环直接结束. 即线程2不知道这个变化过程.
 *
 *  如何解决ABA问题{@link ABAResolutionDemo}:
 *    通过AtomicStampedReference来解决, 给每一个引用都加一个版本号. 让线程之间能感受到到引用的变化.
 *
 *  ------------
 *
 *
 */
public class CASAndAtomic {
    private static int value = 0;

    public static void main(String[] args) throws NoSuchFieldException {
        // 原子引用.
        AtomicReference<User> userAtomicReference = new AtomicReference<>();

        User user1 = new User(1, "1");
        User user2 = new User(2, "2");

        userAtomicReference.set(user1);

        // true
        System.out.println(userAtomicReference.compareAndSet(user1, user2) + ":" + userAtomicReference.get());
        // false
        System.out.println(userAtomicReference.compareAndSet(user1, user2) + ":" + userAtomicReference.get());
    }

    private static final class User {
        private int age;
        private String name;

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
        }
    }
}

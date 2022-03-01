package com.cjs.lock.cas;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CAS(无锁算法), 全称Compare And Swap, 比较与交换, 是CPU的一个指令(硬件原语), 且是一个原子操作.
 * 原理, 通过以下三个值来实现:
 *  1. 工作内存的值.
 *  2. 主内存中的值.
 *  3. 要更新的值.
 * 比较当前工作内存中的值和主内存中的值, 如果相同则执行更新操作. 否则返回False. 返回False后可以报错也可以重试, 当重试的时候就是自旋锁.
 *
 *  -----------
 *
 *  Java.util.concurrent包中原子类就是通过 Unsafe+CAS+自旋 来实现.
 *
 *  Unsafe: 用于获取内存中volatile变量的值. 位于rt.jar包下的sun.misc包内; 用于调用操作系统的CAS指令.
 *  CAS: 如果当前工作内存的值与主内存的值不一样, 则证明值已经被其他线程修改, 此时返回False. 一样则予以更新.
 *  自旋: 如果CAS返回False, 则更新自己工作内存的值为主内存中的值, 再次进行CAS, 直到CAS返回true.[可能刚获取到主内存的值后线程又被挂起, 所以需要循环比较, 即自旋锁]
 *
 *  三者在Unsafe的体现:
 *  // valueOffset, value在AtomicInteger中的偏移量.
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
 *      Synchronized[悲观锁, 重量级锁]: 加锁, 线程会阻塞, 降低应用并发性; JMV级别的原子性.
 *      CAS[乐观锁, 轻量级锁]: 不会加锁, 线程不被阻塞; 硬件级别的原子性[操作系统的一个指令, 原子操作].
 *      线程切换会引起用户态和内核态的切换. TODO 重点
 *
 *      悲观锁: 对数据的修改持悲观态度, 认为自己在修改数据的时候一定有别的线程来修改数据, 因此访问的时候都要先加锁,
 *      保证同一时刻只有一个线程获得锁, 读读也会阻塞.  [适用于写多读少的情况]
 *      乐观锁: 对数据的修改持乐观态度, 认为自己在使用数据的时候不会有别的线程修改数据, 只有在更新数据的时候才会检测冲突.
 *      读读不会阻塞.  [适用于读多写少的情况]
 *
 *      补充: Mysql中的乐观锁用MVCC实现. 悲观锁通过Select *** for update/lock in share mode(写锁/读锁).
 *
 *  -----------
 *
 * CAS优点: 不用对同步资源加锁; 其余线程不用进入阻塞状态, 避免了线程状态的切换[挂起->恢复]. 适用于同步代码块执行时间很短的场景.
 *
 * CAS缺点:
 *  1. 如果锁被占用的时间很长, 那么自旋的线程只会白白浪费处理器资源, 会产生CPU使用率飙升的情况. 进而导致吞吐量下降.
 *  2. CAS只能保证一个共享变量的线程安全, 如果需要保证多个共享变量的线程安全则需要锁.
 *  3. ABA问题.
 *
 *   -------------
 *
 * ABA问题({@link ABAProblemDemo}:
 *    假设存在线程1和线程2共同操作共享变量var, 刚开始线程1和线程2都读到主内存var的值为A(do while循环内),
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


Keep your eyes on the stars and your feet on the ground!!!  

# 重要知识点目录  

# 1. JVM  
## 1.1 [YGC日志分析](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/YGCLogAnalyze.java)  
> 包括内存担保机制的解释。
## 1.2 [FULLGC日志分析](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/FullGCLogAnalyze.java)  
## 1.3 TenuringThreshold动态调整策略 [程序](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/TenuringThreshold.java) [程序对应日志分析](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/TenuringThreshold.log)
## 1.4 [JVM四大引用介绍见及示例演示-强软弱虚](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/reference/FourTypesOfReference.java)
### 1.4.1 ThreadLocalMap中的Entry的key使用到虚引用.
## 1.5 内存泄漏
### 1.5.1 ThreadLocal使用不当造成内存泄漏.
### 1.5.2 [内存泄漏原因及解决办法](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/memory_link/MemoryLinkExample.java)
## 1.6 类加载机制
### 1.6.1 [类的初始化时机及顺序](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/类加载/类加载过程/InitializationOfClass.java)  
> 其余(双亲委派等)见有道云笔记.  
## 1.7 [CMS垃圾收集器的工作流程-简单描述](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/CMSGC.java) [程序对应日志分析](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/CMSGC.log)  
> 详细流程及调优见有道云笔记。
## 1.8 G1收集器 TODO

# 2. 算法
## 2.1 [位图法, 用来解决大量整数的排序 去重 查找问题](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/%E6%B5%B7%E9%87%8F%E6%95%B0%E6%8D%AE/%E4%BD%8D%E5%9B%BE%E6%B3%95.java)
## 2.1.1 [布隆过滤器, 用来处理海量数据的查重问题](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/海量数据/TestBloomFilter.java)

## 2.2 排序  
### 2.2.1 外部排序(out-place)  
> [归并排序](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/sort/MergeSort.java), 包括Master公式-求递归函数的时间复杂度.  

### 2.2.2 内部排序(in-place)
> [快速排序]()
> [堆排序](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/sort/HeapSort.java)  
> 冒泡 选择 插入排序的时间复杂度均为O(n*n), 空间复杂度均为O(1), 但是选择排序不稳定.

## 2.3 [海量数据相关算法](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/海量数据/README.MD)

## 2.4 [Hash算法](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/海量数据/HashAlgorithm.java)

## 2.5 搜索
### 2.5.1 [搜索矩阵](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/lettcode/medium/Search2DMatrix.java)

## 2.6 面试常用
### 2.6.1 [判断链表是否有环, 且返回环的位置](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/linkedlist/CycleLinkedList.java)
### 2.6.2 [峰值类相关题目](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/leetcode/medium/FindInMountainArray.java)
> 数组先从小到大, 再从大大小. 求峰值, 或找出目标值的index.

# 3. 并发
![并发框架概述](https://github.com/KobeAndLebron/YoudaoNoteFileStorage/blob/master/concurrent/%E5%B9%B6%E5%8F%91%E9%9B%86%E5%90%88%E6%A6%82%E8%BF%B0.png)  
## 3.1 [CAS和Java中的CAS实现](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/cas/CASAndAtomic.java)  
## 3.1.1 [CAS引起的ABA问题](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/cas/ABAProblemDemo.java)  
## 3.1.2 [ABA解决方案](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/cas/ABAResolutionDemo.java)-by AtomicStampedReference
## 3.1.3 [自旋锁简单实现](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/cas/SpinLockImpl.java)
> 总结: CAS是一种无锁并发算法(乐观锁), 在同步代码时间较短的时候(比上下文切换的时间小), 并发量比synchronized高, 因为CAS不会使获取锁失败的线程进入阻塞状态, 避免了上下文切换的开销.

## 3.2 [锁的分类](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/lockCategories.text) 
对应图片: https://awps-assets.meituan.net/mit-x/blog-images-bundle-2018b/7f749fc8.png
### 3.2.1 [Synchronized和Lock的区别](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/synchronized_lock/synchronized和lock的区别.text)
### 3.2.2 Synchronized
#### 3.2.2.1 [Synchronized的原理及优化](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/synchronized_lock/SynchronizedTheory.java)
> TODO 锁消除 锁粗化 

### 3.2.3 ReentrantLock
#### 3.2.3.1 [自己实现的ReentrantLock(公平+可重入版本锁版本)](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/rerntrant_lock/MyFairLock.java)
#### 3.2.3.2 [ReentrantLock的Condition原理实现](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/rerntrant_lock/ConditionImpl.text)
#### 3.2.3.3 [ReentrantLock的可重入例子](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/RecursiveReentrantLock.java)
#### 3.2.3.4 [NonReentrantLock的死锁例子](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/NonRecursiveLock.java)
#### 3.2.3.5 使用案例
> 1. [生产者消费者模式](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/rerntrant_lock/example/PC_By_Lock.java)
> 2. [三个线程交替打印字符串](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/rerntrant_lock/example/PrintAlternately.java), Condition精准控制线程执行顺序。

## 3.3 阻塞队列
### 3.3.1 [ArrayBlockingQueue的简单使用及原理](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/block_queue/BlockingQueueDemo.java)
### 3.3.2 [延时队列的简单使用及原理](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/block_queue/BlockingQueueDemo.java) 
### 3.3.3 手动实现一个阻塞队列 TODO
### 3.3.4 [延时队列的使用场景](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/block_queue/TestDelayQueue.java)
> 延时队列的其他实现: 
> 1. Redis: 使用sortSet实现, score放失效的时间, member放元素, 取元素的时候使用srangebyscore,获取过期时间大于当前时间的即可.
> 2. RabbitMQ的延时队列.
> 3. 时间轮算法, 还不了解.


## 3.3 线程池
### 3.3.1 线程池实现原理 
> 查看有道云笔记

## 3.4 线程安全类
### 3.4.1 CopyOnWriteList
> 使用ReentrantLock让写写互斥；通常情况下效率比较低，但是当遍历操作远远大于写操作时，效率较高。  
> 写操作实现：1.将原数组copy一份。 2.创建新数组。 3.将值写入新数组。 4. 将新数组赋给原数组。  这样读的时候也可以写，最大限度减少同步的时间。  
> 体现的思想: 1. 读写分离. 2. 保证最终一致性(当需要实时读的时候, 如果写操作完成, 但是此时引用尚未执行新数组, 这时候读的还是原数组的数据).
> Collections.synchronizedList, 效率低下。 读读也会互斥。

### 3.4.2 ConcurrentHashMap

## 3.5 JUC工具类
### 3.5.1 [根据AQS实现CountDownLatch](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/example/MyCountDownLatch.java)
> CountDownLatch, 在所有线程执行完毕后执行**某些线程而非一个**。 A synchronization aid that allows **one or more threads** to wait until a set of operations being performed in other threads completes.
### 3.5.2 CyclicBarrier
> [士兵案例-CyclicBarrier的复用](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom//3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/example/CyclicBarrierExample.java)
> CyclicBarrier, 所有线程之间相互等待, 直到到达一个同步点, 才一起继续往下执行. A synchronization aid that allows **a set of threads to all wait for each other** to reach a common barrier point.
### 3.5.3 Semaphore
> TODO

### 3.5.4 AQS[具体实现查看MyFairLock]

>   AQS的核心思想: 如果请求(acquire)的共享资源(volatile state)空闲, 则将当前请求资源的线程设置为有效的工作线程(exclusiveOwnerThread), 并将资源设置为锁定状态. 
    如果共享资源被锁定(获取锁失败), 则需要一套线程阻塞等待机制及唤醒时锁分配机制, 这个机制通过CLH队列(FIFO)实现, 即将暂时获取不到锁的线程加入到队列中。
>   请求资源时, 用CAS(compareAndSetState方法)来原子设置state.
>
>   AQS定义了两种资源共享模式：
>
> 1. Exclusive（独占）：只有一个线程能运行，入ReentrantLock，又分为公平锁和非公平锁。
> 2. Share（共享）：多个线程可同时执行，如Semaphore/CountDownLatch。Semaphore、CountDownLatch、 CyclicBarrier、ReadWriteLock。
>
>   AQS底层使用模版方法模式: 
>
> 1. 使用者继承AbstractQueuedSynchronizer并重写指定的方法。（这些重写方法很简单，无非是对于共享资源state的获取和释放）.
>
>    ```java
>    isHeldExclusively()//该线程是否正在独占资源。只有用到condition才需要去实现它。
>    tryAcquire(int)//独占方式。尝试获取资源，成功则返回true，失败则返回false。
>    tryRelease(int)//独占方式。尝试释放资源，成功则返回true，失败则返回false。
>    tryAcquireShared(int)//共享方式。尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
>    tryReleaseShared(int)//共享方式。尝试释放资源，成功则返回true，失败则返回false。
>    ```
>
> 2. 将AQS组合在自定义同步组件的实现中，子类来调用其acquire/release的方法来获取/释放资源，这俩方法会调用使用者重写的方法。
> 查看案例[对AQS的简单实现](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom//3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/rerntrant_lock/MyReentrantLock.java)


## 3.6 [ThreadLocal的原理](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/ThreadLocal/ThreadLocalPractice.java)   
> 使用虚引用目的及使用不当会造成内存泄漏

## 3.7 Volatile及JMM模型
> TODO

## 3.8 [线程间通信](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/example/线程间通信.md)

## 3.9 其余
### 3.9.1 上下文切换
> 详细查看有道云笔记, 简要: 主要要保存CPU寄存器和程序计数器的状态, 然后将 恢复线程的状态赋给CPU.

### 3.9.2 [死锁代码示例及解决方案](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/dead_lock/DeadLockExample1.java)
> 包括JAVA本地线程和操作系统本地线程的对应关系.

### 3.9.2 [Join的实现原理](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/join_study/JoinImplementationNote.java)
> [自己实现的Join](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/join_study/MyJoin.java)  
> [为什么尽量不要用Thread对象的wait/notify来进行线程间通信](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/join_study/notice.txt)  
> [Join相关的多线程运行题](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/join_study/Join相关多线程运行题.java)  

## 3.10 异步编排
### 3.10.1 异步编排的使用案例及场景 TODO
### 3.10.2 异步编排的原理 TODO

# 4. 数据库
## 4.1 [Explain返回结果中的type和Extra解释及索引失效原则](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/2YD_JavaAndSqlOptimize/src/main/java/com/cjs/goHead/mysql/type_extra_in_explain.sql)
## 4.2 乐观锁机制, 采用MVCC的实现
> 涉及快照读, 原理在有道云笔记.
## 4.3 [MySql的锁](https://www.cnblogs.com/rjzheng/p/9950951.html)

# 5. JAVA IO及网络模型
## 5.1 JavaIO模型

# 6. JDK1.8新特性
## 6.1 函数式接口-Lambda表达式
## 6.2 Stream表达式
# TODO

# 7. 分布式篇
## 7.1 分布式事务
## 7.2 分布式锁
### 7.2.1 Redis分布式锁基本原理
> [RedisLock单机锁原理](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/9OpenSourceFramework/redis/com/cjs/gohead/source/redis/RedLock.java)    
> [加锁的lua脚本](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/9OpenSourceFramework/redis/com/cjs/gohead/source/redis/redis_lock.lua)  
## 7.3 分布式缓存
## 7.4 分布式ID
## 7.5 微服务
## 7.6 ELK
## 7.7 [负载均衡算法](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/海量数据/LoadBalance.java)
> 从各个角度描述了负载均衡的技术, 并实现了 随机 轮询 加权轮询三个简单的负载均衡算法.
## 7.8 分布式分区算法
## 7.9 分布式共识算法 RAFT TODO
## 7.10 分布式一致性算法 paxo TODO
- [ ] 包名较混乱。  
## 7.11 限流
### 7.11.1 [滑动窗口限流算法]((https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/海量数据/SlideWindow.java)) 

## 7.12 Redis在JAVA代码中的使用
### 7.12.1 [连接池线上怎么配置, 连接池预热优化](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom\9OpenSourceFramework\redis\com\cjs\gohead\source\redis\RedisPoolPractice.java)
### 7.12.2 [RedisCluster连接方式](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom\9OpenSourceFramework\redis\com\cjs\gohead\source\redis\JedisClusterTest.java)
### 7.12.2 [Codis连接方式](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom\9OpenSourceFramework\redis\com\cjs\gohead\source\redis\CodisClientHA.java)
### 7.12.3 [Sentinel连接方式](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom\9OpenSourceFramework\redis\com\cjs\gohead\source\redis\JedisSentinelTest.java)

# 8. 设计模式
## 8.1 责任链模式
> Servlet中责任链的实现, 造轮子. [FilterChain实现](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/9OpenSourceFramework/过滤器的实现/com/cjs/core/FilterChain.java)  
> 与AOP中责任链模式的实现大同小异.

- [x] Sentinel的使用场景及解析.
- [x] 限流算法. 理解漏桶算法和滑动时间窗口算法的原理

- [ ] G1垃圾收集器以及线上定位内存泄漏的问题.
- [ ] 数据库间隙锁 undolog redolog
- [ ] 动态代理的cglib实现及事务的实现原理.


- [ ] 设计模式-消息订阅模式的应用.
- [ ] completableFuture的使用场景.

我们将商品模块作为开发的第一步，然后支付模块由于要对接第三方的接口，有人专门去负责处理，其他模块并行处理。

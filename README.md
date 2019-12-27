
Keep your eyes on the stars and your feet on the ground!!!  

# 重要知识点目录  

# 1. JVM  
## 1.1 [YGC日志分析](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/YGCLogAnalyze.java)
## 1.2 [FULLGC日志分析](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/FullGCLogAnalyze.java)  
## 1.3 TenuringThreshold动态调整策略 [程序](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/TenuringThreshold.java) [程序对应日志分析](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/TenuringThreshold.log)
## 1.4 [JVM四大引用介绍见及示例演示-强软弱虚](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/reference/FourTypesOfReference.java)
### 1.4.1 ThreadLocalMap中的Entry使用到虚引用.
## 1.5 内存泄漏
### 1.5.1 ThreadLocal使用不当造成内存泄漏.
## 1.6 类加载机制
### 1.6.1 [类的初始化时机及顺序](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/类加载/类加载过程/InitializationOfClass.java)  
> 其余(双亲委派等)见有道云笔记.  
## 1.7 [CMS垃圾收集器的工作流程-简单描述](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/CMSGC.java) [程序对应日志分析](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/CMSGC.log)  
> 详细流程及调优见有道云笔记。
## 1.8 G1收集器 TODO

# 2. 算法
## 2.1 [位图法, 用来解决大量数据的排序 去重 查找问题](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/%E6%B5%B7%E9%87%8F%E6%95%B0%E6%8D%AE/%E4%BD%8D%E5%9B%BE%E6%B3%95.java)
## 2.1.1 [布隆过滤器, 用来处理海量数据的查重问题](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/海量数据/TestBloomFilter.java)

## 2.2 排序  
### 2.2.1 外部排序(out-place)  
> [递归排序](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/sort/MergeSort.java), 包括Master公式-求递归函数的时间复杂度.  

### 2.2.2 内部排序(in-place)
> [快速排序]()
> [堆排序](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/sort/HeapSort.java)

## 2.3 [海量数据相关算法](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/海量数据/README.MD)

## 2.4 [Hash算法](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/海量数据/HashAlgorithm.java)

# 3. 并发
![并发框架概述](https://github.com/KobeAndLebron/YoudaoNoteFileStorage/blob/master/concurrent/%E5%B9%B6%E5%8F%91%E9%9B%86%E5%90%88%E6%A6%82%E8%BF%B0.jpg)  
## 3.1 [CAS和Java中的CAS实现](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/cas/CASAndAtomic.java)  
## 3.1.1 [CAS引起的ABA问题](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/cas/ABAProblemDemo.java)  
## 3.1.2 [ABA解决方案](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/cas/ABAResolutionDemo.java)-by AtomicStampedReference
## 3.1.3 [自旋锁简单实现](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/cas/SpinLockImpl.java)

## 3.2 [锁的分类](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/lockCategories.text) 
对应图片: https://awps-assets.meituan.net/mit-x/blog-images-bundle-2018b/7f749fc8.png
### 3.2.1 [Synchronized和Lock的区别](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/synchronized_lock/)
### 3.2.2 Synchronized
#### 3.2.2.1 Synchronized的原理[锁消除 锁粗化] TODO

### 3.2.3 ReentrantLock
#### 3.2.3.1 [自己实现的ReentrantLock(公平+可重入版本锁版本)](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/rerntrant_lock/MyFairLock.java)
#### 3.2.3.2 [ReentrantLock的Condtion原理实现](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/rerntrant_lock/ConditionImpl.text)
#### 3.2.2.3 使用案例
> 1. [生产者消费者模式](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/rerntrant_lock/example/PC_By_Lock.java)
> 2. [三个线程交替打印字符串](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/rerntrant_lock/example/PrintAlternately.java), Condition精准控制线程执行顺序。

## 3.3 阻塞队列
### 3.3.2 手动实现一个阻塞队列 TODO

## 3.3 线程池
### 3.3.1 线程池实现原理 TODO

## 3.4 线程安全类
### 3.4.1 CopyOnWriteList
> 使用ReentrantLock让写写互斥；通常情况下效率比较低，但是当遍历操作远远大于写操作时，效率较高。  
> 写操作实现：1.将原数组copy一份。2.创建新数组。 3.将值写入新数组。 4. 将新数组赋给原数组。  这样读的时候也可以写，最大限度减少同步的时间。  
> Collections.synchronizedList, 效率低下。 读读也会互斥。

### 3.4.2 ConcurrentHashMap

## 3.5 JUC工具类
### 3.5.1 [根据AQS实现CountDownLatch](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/example/MyCountDownLatch.java)
> CountDownLatch, 在所有线程执行完毕后执行**某些线程而非一个**。 A synchronization aid that allows **one or more threads** to wait until a set of operations being performed in other threads completes.
### 3.5.2 CyclicBarrier
### 3.5.3 Semaphore

## 3.6 [ThreadLocal的原理](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/ThreadLocal/ThreadLocalPractice.java)   
> 使用虚引用目的及使用不当会造成内存泄漏

## 3.7 Volatile及JMM模型

# 4. 数据库
## 4.1 [Explain返回结果中的type和Extra解释及索引失效原则](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/2YD_JavaAndSqlOptimize/src/main/java/com/cjs/goHead/mysql/type_extra_in_explain.sql)
## 4.2 MVCC的实现
## 4.3 [MySql的锁](https://www.cnblogs.com/rjzheng/p/9950951.html)

# 5. JAVA IO
## 5.1 JavaIO模型
# TODO
- [ ] 包名较混乱。  

-----------

G1 GC
类加载机制[差自定义类加载器] xx  
内存泄漏
Redis布隆过滤器 穿透 雪崩 击穿 xx
分布式事务+分布式锁
TreeMap

Hash算法: 相同输入相同输出, 不同输入均匀分布. 通过两个HashMap设计一个getRandom add remove方法 
Hash分片算法(Redis)

Hystrix生产配置
Spring生命周期+扩展方式+AOP实现方式

TCP三次握手四次挥手
[volatile CPU Cache的理解](https://juejin.im/post/5c6b99e66fb9a049d51a1094#heading-38) 线程间通信.


---------------Discard

# 临时计划
- [ ] 周三: G1 GC+线程池. 内存泄漏+类加载机制.                         一个都未完成... 加深了解了CMS.
- [ ] 周四: NIO及Redis网络模型+TCP四次握手三次挥手+HTTPS HTTP.         还剩TCP握挥.
- [ ] 周五: Redis集群复习. 这是简历的第一块大内容.
- Codis集群的温故知新.

- [ ] 周六: SpringCloud+分布式事务+分布式锁+丰富简历内容. 
- [ ] 周日: 复习所有内容, 晚上投递简历. + [HashMap ConcurrentHashMap TreeMap-红黑树, 高频点, 一定要搞懂].

- 周末两天完成了1. Hystrix基础的学习 2. 各种轮询算法 Hash分片算法. 3. 完成简历.

------------
- [ ] 周一: Hystrix的高阶用法. 线程池的实现原理. - 线程池的实现原理.
- [ ] 周二: 1.白天KMX架构 2. 晚上分布式锁 分布式事务.
- [ ] 周三: [HashMap ConcurrentHashMap TreeMap-红黑树, 高频点, 一定要搞懂] Synchronized的实现方式
- [ ] 周四: 1.[volatile CPU Cache的理解](https://juejin.im/post/5c6b99e66fb9a049d51a1094#heading-38) 线程间通信. 
- [ ] 周五: 1.G1 GC. 2. TCP挥握
- [周六周日] Spring源码学习
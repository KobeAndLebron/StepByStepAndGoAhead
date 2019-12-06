
Keep your eyes on the stars and your feet on the ground!!!  

# 重要知识点目录  

# 1. JVM  
## 1.1 [YGC日志分析](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/YGCLogAnalyze.java)
## 1.2 [FULLGC日志分析](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/FullGCLogAnalyze.java)  
## 1.3 TenuringThreshold动态调整策略 [程序](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/TenuringThreshold.java) [程序对应日志分析](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/gc/TenuringThreshold.log)
## 1.4 [JVM四大引用介绍见及示例演示-强软弱虚](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/5JVM/src/main/java/com/cjs/reference/FourTypesOfReference.java)

# 2. 算法
## 2.1 [位图法, 用来解决大量数据的排序 去重 查找问题](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/1DataStructureAndAlgorithm/src/main/java/%E6%B5%B7%E9%87%8F%E6%95%B0%E6%8D%AE/%E4%BD%8D%E5%9B%BE%E6%B3%95.java)

# 3. 并发
## 3.1 [CAS和Java中的CAS实现](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/cas/CASAndAtomic.java)  
## 3.1.1 [CAS引起的ABA问题](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/cas/ABAProblemDemo.java)  
## 3.1.2 [ABA解决方案](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/cas/ABAResolutionDemo.java)-by AtomicStampedReference
## 3.1.3 [自旋锁简单实现](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/cas/SpinLockImpl.java)

## 3.2 [锁的分类](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/lockCategories.text) 
对应图片: https://awps-assets.meituan.net/mit-x/blog-images-bundle-2018b/7f749fc8.png
### 3.2.1 [Synchronized和Lock的区别](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/synchronized_lock/)
### 3.2.2 Synchronized

### 3.2.3 ReentrantLock
#### 3.2.3.1 [自己实现的ReentrantLock(公平+可重入版本锁版本)](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/rerntrant_lock/MyFairLock.java)
#### 3.2.3.2 [ReentrantLock的Condtion原理实现](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/rerntrant_lock/ConditionImpl.text)
#### 3.2.2.3 使用案例
> 1. [生产者消费者模式]
> 2. [三个线程交替打印字符串](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/lock/rerntrant_lock/example/PrintAlternately.java), Condition精准控制线程执行顺序。

## 3.3 阻塞队列

## 3.3 线程池

## 3.4 线程安全类
### 3.4.1 CopyOnWriteList
> 使用ReentrantLock让写写互斥；通常情况下效率比较低，但是当遍历操作远远大于写操作时，效率较高。  
> 写操作实现：1.将原数组copy一份。2.创建新数组。 3.将值写入新数组。 4. 将新数组赋给原数组。  这样读的时候也可以写，最大限度减少同步的时间。  
> Collections.synchronizedList, 效率低下。 读读也会互斥。

### 3.4.2 ConcurrentHashMap

## 3.5 JUC工具类
### 3.5.1 [根据AQS实现CountDownLatch](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/3ThreadPoolAndHighConcurrency/src/main/java/com/cjs/example/MyCountDownLatch.java)
> CountDownLatch, 在所有线程执行完毕后执行**某些线程而非一个**。 A synchronization aid that allows **one or more threads** to wait until
 a set of operations being performed in other threads completes.

# 4. 数据库
## 4.1 [Explain返回结果中的type和Extra解释及索引失效原则](https://github.com/KobeAndLebron/StepByStepAndGoAhead/blob/master/SuperModulePom/2YD_JavaAndSqlOptimize/src/main/java/com/cjs/goHead/mysql/type_extra_in_explain.sql)

# TODO
- [ ] 包名较混乱。  

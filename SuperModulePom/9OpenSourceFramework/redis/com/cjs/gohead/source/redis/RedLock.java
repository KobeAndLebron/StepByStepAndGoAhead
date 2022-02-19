package com.cjs.gohead.source.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * Redis分布式锁的特征:
 *  1. 安全性: 独享/互斥性, 分布式锁需要在不同节点的不同线程互斥, 这是最根本的. 即在任意时刻, 只有一个客户端持有锁.
 *  2. 活性A: 无死锁, 即持有锁的客户端崩溃或者网格被分裂, 锁仍然能被获取(给key设置一个ttl).
 *           可重入性: 同一个节点上的同一个线程, 如果获取了锁之后, 也可以再次获取这个锁. 避免死锁.
 *  3. 活性B: 容错. 只要大部分Redis节点都活着, 客户端就可以获取和释放锁.
 *  4. 高可用: 加锁和解锁需要高效, 同时也需要保证高可用防止分布式锁失效, 可以增加降级.
 *  5. 支持阻塞和非阻塞:
 *      阻塞: 获取不到锁, 自旋等待.
 *      非阻塞: 获取不到锁, 返回错误结果.
 *  6. 支持公平和非公平(一般来说实现的少).
 *
 *
 *  单点故障: 如果Redis挂了, 会导致分布式锁服务挂掉.
 *  主从复制: 因为Redis的主从复制是异步的, 所以这种方式也是不安全的:
 *      1. 客户端从Master获取到锁.
 *      2. 在Master将锁同步到slave之前, master宕掉了.
 *      3. Slave节点被晋升为Master节点.
 *      4. 客户端B取得了同一个资源被客户端A已经获取到的另外一个锁。安全失效！
 */
@Slf4j
public class RedLock {

    public static RedissonClient getRedissonClient() {
        //1、创建配置(单节点模式）
        Config config = new Config();
        config.useSingleServer().setAddress("redis://r-wz95ias87w5tv0m6c7pd.redis.rds.aliyuncs.com:6379").setPassword("Nft!@#4562019Test")
            .setDatabase(9);

        //2、根据Config创建出RedissonClient实例
        //Redis url should start with redis:// or rediss://
        return Redisson.create(config);
    }


    public static void main(String[] args) {
        RedissonClient redissonClient = getRedissonClient();

        // 获取一把锁，只要锁的名字一样，就是同一把锁
        RLock myLock = redissonClient.getLock("my-lock");

        // // // // 加锁细节
        // 第一版本：直接使用set redisKey redisValue NX, 如果代码在运行到finally之前断电，没执行删锁逻辑， 会导致死锁。
        // 解决方案：setNX的时候设置过期时间，且setNX和设置过期时间必须是原子操作。
        // 第二版本：业务超时，会导致锁过期；业务超时后，直接使用del key的方式进行删锁，会删除别人正在持有的锁。
        // 解决方案：设置value的时候使用UUID, 代表正在持有锁的线程, 只有自己能释放自己的锁.
        // 最终执行命令, set key {randomValue} NX PX 30000, 即设置过期时间必须和加锁是同步的, 保证原子性(避免死锁).

        // 可重入锁使用, hset {lockKey} {randomValue} {已经重入次数}, 并利用lua脚本保证hset nx和ttl的.
        // 查看redis_lock.lua脚本.

        // 加锁
        myLock.lock();      //阻塞式等待。默认加的锁都是30s

        // // // //加锁后执行需要俩逻辑:
        // 1）锁的自动续期，如果业务超长，运行期间自动锁上新的30s。不用担心业务时间长导致锁自动过期被删掉.
        // // 也就是开门狗线程: 防止同步代码的执行时间大于锁的持有时间, 而导致同步代码没有执行完, 锁就被释放了.
        // // 具体做法: 每隔10s对锁重新设置失效时间为30s.
        // 2）加锁的业务只要运行完成，就不会给当前锁续期，即使不手动解锁（假设刚lock完机器就宕机），锁默认会在30s内自动过期，不会产生死锁问题

        // 最佳实践： 使用lock.lock(10, TimeUnit.SECONDS)进行上锁，锁时间一定要大于业务执行时间，这样可以省掉续期的定时任务。

        //问题：在锁时间到了以后，不会自动续期

        /**
         * 加锁及续约逻辑: {@linkplain org.redisson.RedissonLock#tryAcquireAsync(long, TimeUnit, long)}
         * 1、如果我们传递了锁的超时时间，就发送给redis执行脚本，进行占锁，默认超时就是 我们制定的时间, 不进行续期操作.
         *
         * 2、如果我们不指定锁超时时间，就使用 lockWatchdogTimeout = 30 * 1000 【看门狗默认时间】作为过期时间加锁,
         * 只要占锁成功，就会启动一个定时任务【这个定时任务重新给锁设置过期时间，新的过期时间就是看门狗的默认时间】,
         * 定时任务每隔10秒(internalLockLeaseTime 【看门狗时间】 / 3 = 10s)都会自动再次续期，续成30秒
         * 续约逻辑: {@linkplain org.redisson.RedissonLock#renewExpiration()}
         */

        // // // //删锁细节:
        // 先去redis查询下保证当前的锁是自己的获取值对比，对比成功删除。【需要保证原子性】
        // 如果get和del不是原子的，会导致当equals生效时，加入此时redis中的UUID失效，
        // 其他进程正好在redis设置了自己的UUID， 此时del将删除其他进程的锁。 锁失效。
        // String lockValue = stringRedisTemplate.opsForValue().get("lock");
        // if (uuid.equals(lockValue)) {
        //     //删除我自己的锁
        //     stringRedisTemplate.delete("lock");
        // }

        // 正确操作, 原子命令释放锁(Lua脚本, 获取 判断和删除三个操作):
        // 使用ThreadLocal来存RandomValue, 这样在del key的方法中, 就可以获取.
        // randomValue和threadId. 来做对应的操作.
        // if redis.call("get",KEYS[1]) == ARGV[1] then
        //   return redis.call("del",KEYS[1])
        // else
        //   return 0
        // end

        try {
            log.debug("加锁成功，执行业务: " + Thread.currentThread().getId());
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //3、解锁  假设解锁代码没有运行，Redisson会不会出现死锁
            log.debug("释放锁: " + Thread.currentThread().getId());
            myLock.unlock();

        }

        return;

    }
}

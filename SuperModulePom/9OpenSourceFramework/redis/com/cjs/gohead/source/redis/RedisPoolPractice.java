package com.cjs.gohead.source.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RedisPoolPractice {
    /**
     * 连接池大概工作原理：
     * minIdle 10,maxIdle 50,total 100
     * 刚开始连接池是空的,当请求过来时,从连接池里面拿连接,new一个用完后放入连接池.
     *
     * 当生成到10个后,系统闲下来了,那么这10个连接会一直在的.
     *
     * 当请求继续过来,连接数继续增加,假如增加到60个连接,那么当系统闲下来的时候,会慢慢释放这10个连接,到到50.
     *
     * @param args
     */
    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        /**
         * 线上连接池配置,以一个例子说明,假设:
         *
         * 假设一次命令时间（borrow|return resource + Jedis执行命令(含网络)）的平均耗时约为1ms,那么一个连接的QPS大约是1000
         * 业务期望的QPS是50000
         *
         * 那么理论上需要的资源池大小是50000 / 1000 = 50个。但事实上这是个理论值,还要考虑到要比理论值预留一些资源,通常来讲maxTotal可以比理论值大一些。
         * maxIdle给50,maxTotal给100.
         * 总之,要根据实际系统的QPS和调用Redis客户端的整体规模来评估每个节点所使用连接池的大小。
         */
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxIdle(50);
        jedisPoolConfig.setMinIdle(5);
        jedisPoolConfig.setTestOnBorrow(false);
        jedisPoolConfig.setTestOnReturn(false);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,"127.0.0.1",6379,3000);
        warmUpJedisPool(jedisPoolConfig,jedisPool);
        try (Jedis jedis = jedisPool.getResource()) {
            //具体的命令
            jedis.set("k1","v1");
        } catch (Exception e) {
            log.error("op key {} error: " + e.getMessage(),e);
        } //注意这里不是关闭连接,在JedisPool模式下,Jedis会被归还给资源池。

        warmUpJedisPool(jedisPoolConfig,jedisPool);
    }

    /**
     * 连接池预热： 如果系统启动完马上就会有很多的请求过来,那么可以给redis连接池做预热,比如快速的创建一些redis连接,执行简单命令,类似ping(),
     * 快速的将连接池里的空闲连接提升到minIdle的数量。
     * 因为即使minIdle设置了10,初始化也是0个连接,当请求过来再new 连接。
     *
     * @param jedisPoolConfig
     * @param pool
     */
    public static void warmUpJedisPool(JedisPoolConfig jedisPoolConfig,JedisPool pool) {
        List<Jedis> minIdleJedisList = new ArrayList<>(jedisPoolConfig.getMinIdle());
        for (int i = 0; i < jedisPoolConfig.getMinIdle(); i++) {
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                minIdleJedisList.add(jedis);
                jedis.ping();
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            } finally { //注意,这里不能马上close将连接还回连接池,否则最后连接池里只会建立1个连接。。
                // jedis.close();
            }
        }
        // 统一将预热的连接还回连接池
        for (int i = 0; i < jedisPoolConfig.getMinIdle(); i++) {
            Jedis jedis = null;
            try {
                jedis = minIdleJedisList.get(i); //将连接归还回连接池
                jedis.close();
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            } finally {
            }
        }
    }
}

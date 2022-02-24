package com.cjs.gohead.source.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class JedisSentinelTest {

    public static void main(String[] args) throws IOException {


        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        String masterName = "mymaster";
        Set<String> sentinels = new HashSet<String>();
        // 监听三个sentinel节点.
        sentinels.add(new HostAndPort("192.168.0.60", 26379).toString());
        sentinels.add(new HostAndPort("192.168.0.60", 26380).toString());
        sentinels.add(new HostAndPort("192.168.0.60", 26381).toString());
        //JedisSentinelPool其实本质跟JedisPool类似，都是与redis主节点建立的连接池
        //JedisSentinelPool并不是说与sentinel建立的连接池，而是通过sentinel发现redis主节点并与其建立连接

        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName, sentinels, config, 3000, null);
        Jedis jedis = null;
        try {
            /**
             * 主节点挂了后, 哨兵重新选举新的Master节点, 客户端可以重新感知到, 但是在选举的过程中, 是不可用的.
             * 1. 主节点下配了从, 这种情况, 主挂了, 到从切上来这段时间, 客户端的部分写入会失败. 主从之前没来得及同步的小部分数据会丢失
             */
            for (int i = 0; i < 100; i++) {
                jedis = jedisSentinelPool.getResource();
                System.out.println(jedis.set("sentinel", "zhuge"));
                System.out.println(jedis.get("sentinel"));

                Thread.sleep(1000);
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            //注意这里不是关闭连接，在JedisPool模式下，Jedis会被归还给资源池。
            if (jedis != null)
                jedis.close();
        }
    }
}
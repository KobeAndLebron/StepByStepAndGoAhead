package com.cjs.gohead.source.redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.JedisClusterCRC16;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * RedisCluster访问示例
 * 简要原理
 * 1. 对key取Hash的操作位于{@linkplain JedisClusterCRC16#getSlot(String)})}, 通过CRC16获取到Hash值, 然后取余:
 * --------------------
 * // optimization with modulo operator with power of 2
 * // equivalent to getCRC16(key) % 16384
 * return getCRC16(key) & (16384 - 1);
 * -----------------------
 *
 * 2. 当 Redis Cluster 的客户端来连接集群时，它也会得到一份集群的槽位配置信息并将其缓存在客户端本地。这样当客户端要查找某个 key 时，
 * 可以直接定位到目标节点。
 * 同时因为槽位的信息可能会存在客户端与服务器不一致的情况，还有纠正机制来实现槽位信息的校验调整。
 *
 */
public class JedisClusterTest {
    
    public static void main(String[] args) throws IOException {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(10);
        config.setMinIdle(5);

        Set<HostAndPort> jedisClusterNode = new HashSet<>();
        // 一般情况下, 任意写一个就可以了， 就可以从配置中获取其他节点。不过一般建议全写。
        jedisClusterNode.add(new HostAndPort("192.168.0.61", 8001));
        jedisClusterNode.add(new HostAndPort("192.168.0.62", 8002));
        jedisClusterNode.add(new HostAndPort("192.168.0.63", 8003));
        jedisClusterNode.add(new HostAndPort("192.168.0.61", 8004));
        jedisClusterNode.add(new HostAndPort("192.168.0.62", 8005));
        jedisClusterNode.add(new HostAndPort("192.168.0.63", 8006));

        JedisCluster jedisCluster = null;
        try {
            //connectionTimeout：指的是连接一个url的连接等待时间 6000
            //soTimeout：指的是连接上一个url，获取response的返回等待时间 5000
            jedisCluster = new JedisCluster(jedisClusterNode, 6000, 5000, 10, "cjs", config);
            System.out.println(jedisCluster.set("cluster", "zhuge"));
            System.out.println(jedisCluster.get("cluster"));
        } catch (Exception e) {
    
            e.printStackTrace();
        } finally {
    
            if (jedisCluster != null)
                jedisCluster.close();
        }
    }
}
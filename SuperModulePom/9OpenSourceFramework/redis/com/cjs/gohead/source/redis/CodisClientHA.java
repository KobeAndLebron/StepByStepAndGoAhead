package com.cjs.gohead.source.redis;

import io.codis.jodis.JedisResourcePool;
import io.codis.jodis.RoundRobinJedisPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import java.util.concurrent.TimeUnit;

/**
 * 常见错误:
 * 1. ERR handle request, slot is not ready, may be offline
 * 2. 主从切换中的数据丢失问题.
 */
@Configuration
public class CodisClientHA {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodisClientHA.class);

    @Value("${codis.zkAddr:localhost}")
    private String zkAddr;

    @Value("${codis.zk.proxy.dir:/jodis/k2db-codis}")
    private String zkProxyDir;

    @Value("${codis.password}")
    private String password;

    @Value("${codis.timeout:30000}")
    private int timeout;

    @Value("${codis.maxActive:1000}")
    private int max_active;

    @Value("${codis.maxIdle:100}")
    private int max_idle;

    @Value("${codis.minIdle:50}")
    private int min_idle;

    @Value("${codis.maxWait:1000}")
    private long max_wait;

    @Bean
    public JedisResourcePool getPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(max_idle);
        poolConfig.setMaxTotal(max_active);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setMaxWaitMillis(max_wait);
        poolConfig.setBlockWhenExhausted(false);
        JedisResourcePool pool = RoundRobinJedisPool.create().poolConfig(poolConfig)
            .curatorClient(zkAddr, timeout).zkProxyDir(zkProxyDir).build();
        LOGGER.info("------------------- Codis connection pool init succeed -------------------");
        return pool;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(CodisClientHA.class);

        JedisResourcePool pool = applicationContext.getBean(JedisResourcePool.class);
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                int cur = 0;
                while (true) {
                    String key = cur++ +"";
                    String value = cur++ +"";
                    try (Jedis jedis = pool.getResource()) {
                        value = jedis.getSet(key, value);
                        LOGGER.info("Thread: [{}]; JedisConn: [{}]; getSetValue:[{}]", Thread.currentThread(), jedis, value);
//                        LOGGER.info("PoolStatus:[{}]", jedis.ping());
                    } catch (JedisException exception) {
                        LOGGER.warn("主从切换中...");
                        // 主从切换写入数据失败的处理措施. TODO 重要
                        System.out.println("将数据先写入到消息队列, 类似死信队列...");
                    } finally {

                    }
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }

    }

}
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

@Configuration
public class CodisClientHA {
    private static final Logger logger = LoggerFactory.getLogger(CodisClientHA.class);

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
        logger.info("------------------- Codis connection pool init succeed -------------------");
        return pool;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(CodisClientHA.class);

        JedisResourcePool pool = applicationContext.getBean(JedisResourcePool.class);
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    int cur = 0;
                    while (true) {
                        try (Jedis jedis = pool.getResource()) {
                            String value = jedis.getSet(cur++ + "", cur++ + "");
                            logger.info("Thread: [{}]; JedisConn: [{}]; getSetValue:[{}]", Thread.currentThread(), jedis, value);
                            logger.info("PoolStatus:[{}]", jedis.ping());
                        }
                    }
                } catch (Exception e) { // 异常则跳出循环，结束线程
                    System.err.println("can not get conn, loop out: ");
                    e.printStackTrace();
                } finally {
                    System.out.println("runner count down");
                }
            }).start();
        }

    }

}
package com.cjs.mq;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.errors.LeaderNotAvailableException;
import org.apache.kafka.common.errors.NotControllerException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Kafka发送流程:
 *
 * 生产者 -> 拦截器 -> 序列化器 -> 分区器 -> Broker.
 *
 * 分区器的分区策略[partitioner.class], 默认采用DefaultPartitioner:
 *      如果指定了分区, 则发往指定分区.
 *      没指定分区但是指定了key, 则取key的hash值然后对分区数(numPartitions)取模.
 *      都没指定则采用round-robin的方式, 将消息(AtomicInteger)依次发往分区.
 *
 *
 * Kafka每个分区都会有一个Leader, 用于接收写请求, 其余节点只负责备份的功能, 没有读写分离.
 *
 * 脑裂: Kafka的二分选举法来避免脑裂问题.
 */
public class MyKafkaProducer {
    private final static String TOPIC_NAME = "test";

    /**
     * 订单
     */
    @Data
    @AllArgsConstructor
    static class Order {
        private int id;

        private Integer orderId;

        private Integer num;

        private double money;
    }

    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        /**
         *[消息可靠性保证- 消息发送端1] : 消息持久化机制
         * （1）acks=0： 表示producer不需要等待任何broker确认收到消息的回复，就可以继续发送下一条消息。性能最高，但是最容易丢消息。 针对大数据的情况, 丢点数据没事.
         * （2）acks=1： 至少要等待leader已经成功将数据写入本地log，但是不需要等待所有follower是否成功写入。就可以继续发送下一
         * 条消息。这种情况下，如果follower没有成功备份数据，而此时leader又挂掉，则消息会丢失。[默认配置]
         * （3）acks=-1或all： 需要等待 min.insync.replicas(默认为1，推荐配置大于等于2, 等于1的时候和acks=1效果一样) 这个参数配置的副本个数都成功写入日志，
         * 这种策略会保证 只要有一个备份存活就不会丢失数据。这是最强的数据保证。一般除非是金融级别，或跟钱打交道的场景才会使用这种配置。
         */
        props.put(ProducerConfig.ACKS_CONFIG, "1");


        /**
         *
         * 发送失败会重试，默认重试间隔100ms，重试能保证消息发送的可靠性，但是也可能造成消息重复发送， 比如网络抖动，所以需要在 接收者那边做好消息接收的幂等性处理.
         * [消息可靠性保证- 消息发送端2]
         * [消息重复消费- 消息发送端]
         *
         * [消息乱序- 消息发送端]
         * 解决方案:
         * 1. 不要重试, 报错之后直接抛出异常, 回滚.
         * 2. 同步场景可以保证百分百.
         *
         *
         * 建议在不考虑乱序的情况下, 开启重试机制, 且在消费端做好幂等处理.
         *
         */
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 300); // 重试间隔为300ms.


        // [本地缓冲区]当Sender线程处理缓慢时, 而生产的数据很快时, 这时中间的缓冲区容量不够, send方法就会被阻塞.
        /**
         * 解决方案: 记录每一条消息的发送间隔, 如果大于100ms, 则需要调大缓冲区的大小.
         */
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432); // 默认32MB.
        // Send方法的最大阻塞时间, 如果Send超过这个时间, 将会抛异常.
        // 通常情况下, Send方法被阻塞是因为BufferMemory满了或者元数据服务不可用了.
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 3000); // ms


        // [优化发送性能]kafka本地线程会从缓冲区取数据，批量发送到broker, 设置批量发送消息的大小，默认值是16384，即16kb，就是说一个batch满了16kb就发送出去
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // 这个配置也会导致, 当消息只有2KB的时候, 达不到16KB(batch.size), 会一直发送不出去.
        // 利用linger.ms来解决:
        /*
        默认值是0，意思就是消息必须立即被发送，但这样会影响性能.
        一般设置10毫秒左右，就是说这个消息发送完后会进入本地的一个batch: 如果10毫秒内，这个batch满了16kb就会随batch一起被发送出去
        如果10毫秒内，batch没满，那么也必须把消息发送出去，不能让消息的发送延迟时间太长
        */
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);


        // 消息是否压缩后才发送, 对机器性能有影响, 减少了数据量和网络宽带. 可用值: lz4, snappy等.
        props.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");

        //把发送的key从字符串序列化为字节数组
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //把发送消息value从字符串序列化为字节数组
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        Producer<String, String> producer = new KafkaProducer<>(props);

        int msgNum = 5;
        final CountDownLatch countDownLatch = new CountDownLatch(msgNum);
        for (int i = 1; i <= msgNum; i++) {
            Order order = new Order(i, 100 + i, 1, 1000.00);
            // 指定发送分区的时候, key不管用, 将直接发往指定分区.
            /*ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(TOPIC_NAME
                    , 0, order.getOrderId().toString(), JSON.toJSONString(order));*/
            // 未指定发送分区，具体发送的分区计算公式：hash(key)%partitionNum
            // 相同Key的消息肯定会被发送到同一分区.
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_NAME,
                order.getOrderId().toString(), JSONObject.toJSONString(order));

            //等待消息发送成功的同步阻塞方法
            /*RecordMetadata metadata = producer.send(producerRecord).get();
            System.out.println("同步方式发送消息结果：" + "topic-" + metadata.topic() + "|partition-"
                    + metadata.partition() + "|offset-" + metadata.offset());*/

            //异步回调方式发送消息
            producer.send(producerRecord, new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception != null) {
                        System.err.println("发送消息失败：" + exception.getStackTrace());

                        // Leader不可用, 原因: Leader选举正在运行.
                        if (exception instanceof LeaderNotAvailableException) {

                        } else if (exception instanceof NotControllerException) { // Controller正在选举中.

                        }
                    }


                    if (metadata != null) {
                        // e.g 异步方式发送消息结果：topic-test|partition-0|offset-9
                        System.out.println("异步方式发送消息结果：" + "topic-" + metadata.topic() + "|partition-"
                            + metadata.partition() + "|offset-" + metadata.offset());
                    }
                    countDownLatch.countDown();
                }
            });

            //送积分 TODO

        }

        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.close();
    }
}
package com.cjs.mq;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.LeaderNotAvailableException;
import org.apache.kafka.common.errors.NotControllerException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

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
    public static void main(String[] args) throws InterruptedException {

        // 同步异步发送消息.
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        /**
         * The number of acknowledgments the producer requires the leader to have received before
         * considering a request complete. This controls the durability of records that are sent.
         * The following settings are allowed:
         *
         * acks=0 the producer will not wait for any acknowledgment from the server at all.
         *
         * acks=1 Leader写成功就成功了, 不用等待Follower的ack; 可能导致数据写入Leader后还没同步,
         * Leader就挂了, 消息可能会丢.
         *
         * acks=ALL 所有In-Sync的Replicas复制成功[由min.insync.replicas配置], 才算成功. 这样保证了只要有一个Replica活着, 消息就不会丢.
         * 保证了消息的可靠性. 等价于ACK=-1
         *
         */
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "1");
        // 本地的消息缓存区.
        // 当Sender线程处理缓慢时, 而生产的数据很快时, 这时中间的缓冲区容量不够, send方法就会被阻塞.
        // 解决方案: 记录每一条消息的发送间隔, 如果大于100ms, 则需要调大缓冲区的大小.
        properties.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG, "33554432"); // bytes
        // Send方法的最大阻塞时间, 如果Send超过这个时间, 将会抛异常.
        // 通常情况下, Send方法被阻塞是因为BufferMemory满了或者元数据服务不可用了.
        properties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 3000); // ms

        // 生产者消费发送失败后重试次数.
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "1000");
        // 每次重试间隔, 默认100ms.
        properties.setProperty(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "2"); // ms

        // 每次发往每个分区的消息的大小; 数据不是一条一条发送, 批量发送可以增大吞吐量.
        properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, "323840");
        // 发送时间限制.
        // 如果消息大小没达到`batch.size`, 但是超过了这个时间也会发送.
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1000); // 默认值为0.

        // 消息是否压缩后才发送, 对机器性能有影响, 减少了数据量和网络宽带. 可用值: lz4, snappy等.
        properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");

        // TODO
        // properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "none");


        KafkaProducer<String, String> myKafkaProducer = new KafkaProducer<>(properties);

        for (int i = 0; i < 10; i++) {
            // 相同Key的消息肯定会被发送到同一分区.
            ProducerRecord<String, String> producerRecord = new ProducerRecord<>("topic1", "aaa" + i, "aaa" + i);
            myKafkaProducer.send(producerRecord, (recordMetadata, e) -> {
                if (e != null) {
                    // Leader不可用, 原因: Leader选举正在运行.
                    if (e instanceof LeaderNotAvailableException) {

                    } else if (e instanceof NotControllerException) { // Controller正在选举中.

                    }
                } else {
                    System.out.println("消息发送到分区:" + (recordMetadata.partition()));
                }
            });

        }

        Thread.sleep(2000);
//        myKafkaProducer.flush();
    }
}

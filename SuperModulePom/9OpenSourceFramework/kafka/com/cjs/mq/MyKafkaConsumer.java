package com.cjs.mq;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

/**
 * 分区和消费者的关系: 多对一. 当分区数和消费者数量相同时, 就会变成一对一的关系.
 *
 * 分区的所有权发生变化时就是分区再均衡. 以下情况会发生分区再均衡:
 *  1. 消费者数量发生变化.[增加或减少-主动关闭/宕机]
 *  2. Partition数量发生变化.
 *
 * 1. Offset[之前版本的Offset信息存在ZK, 新版本存在__consumer_offsets里)
 *  消费者第一次消费: 此时Broker没有这个ConsumerGroup的offset信息, 具体行为由auto.offset.reset控制.
 *  消费者第二次消费: 之后的每次消费都会提交Offset, 涉及到Offset提交策略.
 *
 * 2. Consumer Offset提交策略[enable.auto.commit]:
 *   自动提交[enable.auto.commit=true]:
 *      Consumer会隔一段时间自动提交本地的Offset, 可能会发生消费重复和丢数据的情况.[auto.commit.interval.ms]
 *      消费重复[幂等性]: 数据消费并处理完, 但是Offset还未提交时.
 *          1. 这时候有新的Consumer加入进来, 会导致分区再平衡, 新的Consumer拿到旧的Offset, 再消费一遍[线上BUG, 当时搞青海接数时搞的].
 *          2. 重启了消费者, 重启后也会重复消费.
 *          出现幂等性问题不可怕, 重要的是解决方案:
 *              1. 写Redis, 天然幂等性.
 *              2. 在消息里面加一个全局ID, 消费者拿到这个id去Redis里面查一下, 看之前有吗, 有则消费过, 否则没有.
 *              3. 基于数据库的主键/唯一性约束.
 *      丢数据[可靠性]: Consumer拿到消息还没处理完, 但是offset却自动提交了.
 *          解决方案: 关闭自动提交功能, 手动提交Offset.
 *          手动提交也可能导致重复消费的问题, 因为有可能 处理完消息后手动提交前 机器挂了或者分区再均衡了.
 *   关闭自动提交:
 *      同步提交与异步提交.
 *   手动提交和自动提交都会引起重复消费的问题, 因为无论是手动提交还是自动提交, 在提交Offset前, 都有可能机器挂掉或者
 *   发送分区再均衡的情况, 后者可以通过代码实现来避免[ConsumerRebalanceListener], 前者只能通过保证幂等性的机制来避免.
 *
 */
public class MyKafkaConsumer {
    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = getKafkaConsumer();

        // ConsumerRebalanceListener, 用于因Consumer的数量发生变化而导致分区再平衡, 进而有可能导致
        // Offset尚未提交而引起的重复消费问题.
        consumer.subscribe(Collections.singletonList("topic1"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                System.out.println("分区再平衡Start, 分区分配情况: ");
                System.out.println(partitions.toString());

                consumer.commitSync();

                System.out.println("提交Offset成功, 避免了数据重复消费的情况.");
                System.out.println(partitions.toString());
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                System.out.println("分区再平衡End, 分区分配情况: ");
            }
        });

        while (true) {
            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofSeconds(10));
            for (ConsumerRecord<String, String> record : poll) {
                System.out.println("消息处理完毕, " + "消息所在分区:" + record.partition() +
                    "; 消息ID: " + record.offset() + "; 消息内容:" + record.key() + ":" + record.value());

                // 注释以模拟分区再平衡发生时offset没提交的情况.
                consumer.commitAsync();
            }
        }

        // 优雅关闭, 会直接触发分区再均衡;
        // 不调用这个方法, 然后将进程强制杀死后, 则使用心跳机制来检测消费者是否活着.
        //  consumer.close();
    }

    public static KafkaConsumer<String, String> getKafkaConsumer() {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        // ConsumerGroup的ID.
        // If all the consumer instances have the same consumer group,
        // then the records will effectively be load balanced over the consumer instances.
        // Otherwise each record will be broadcast to all the consumer processes.
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "1123");

        // 如果KafkaBroker在10s内感知不到这个Consumer的心跳, 就会认为这个Consumer挂了. 挂了之后就会Rebalance.
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10 * 1000);
        // 每隔多久发送一次心跳, 一般比Session的TIMEOUT的1/3要小, 尽量小一点.
        properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 1000);

        // 一次Fetch拉下来的最大字节数.
        properties.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 10485760);
        // 一次Poll返回消息的最大条数, 根据吞吐量来定.
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);

        // 一次Fetch拉下来的最小字节数, 设置大点可能会引起延迟.
        properties.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1);
        // 一次Fetch的最大等待时间, 如果服务器在这个时间内没有满足`fetch.min.bytes`的数据, Fetch也会被返回.
        // 通常与上个配置项配合使用.
        properties.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 100);

        // properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, "");

        // 再执行上一次Poll后, 如果30s以后才执行下一次Poll的话, 这个消费者就会被考虑为挂了, 此时就会触发Rebalance.
        // 可以理解为消费能力太弱, 被踢出消费者组了, 交给其他消费者来消费, 结合业务.
        properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 30 * 1000);

        // 当Server没有这个ConsumerGroup的offset信息时, 设置Offset信息的方式.
        // 1. earliest, 自动将Offset设置为最小的offset.
        // 2. latest, 自动将Offset设置为最大的offset.
        // 3. none, 直接报错. NoOffsetForPartitionException.
        // 日志: Resetting offset for partition xxx
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // True表示 每隔段时间在后台提交一次offset.
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        // 消费者自动提交Offset的时间间隔.
        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "10");


        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        return consumer;
    }

}

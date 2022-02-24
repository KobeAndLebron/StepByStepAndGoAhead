package com.cjs.mq;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * 分区和消费者的关系: 多对一. 当分区数和消费者数量相同时, 就会变成一对一的关系.
 *
 * 分区的所有权发生变化时就是分区再均衡. 以下情况会发生分区再均衡:
 * 1. 消费者数量发生变化.[增加或减少-主动关闭/宕机]
 * 2. Partition数量发生变化.
 *
 * 1. Offset[之前版本的Offset信息存在ZK, 新版本存在__consumer_offsets里)
 * 消费者第一次消费: 此时Broker没有这个ConsumerGroup的offset信息, 具体行为由auto.offset.reset控制.
 * 消费者第二次消费: 之后的每次消费都会提交Offset, 涉及到Offset提交策略.
 */
public class MyKafkaConsumer {
    private final static String TOPIC_NAME = "my-replicated-topic";
    // If all the consumer instances have the same consumer group,
    // then the records will effectively be load balanced over the consumer instances.
    // Otherwise each record will be broadcast to all the consumer processes.
    private final static String CONSUMER_GROUP_NAME = "testGroup";

    public static KafkaConsumer<String, String> getKafkaConsumer() {
        Properties props = new Properties();
        // 建议把所有集群都写进去.
        // 写一个集群也行, 从ZK里面会读取到所有集群的信息. 但是这个集群挂了, 就启动不了了.
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // 消费分组名
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_NAME);

        // 是否自动提交(enable.auto.commit)offset，默认就是true
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        /*
         * 消费者offset提交机制:
         *
         * 消费者提交消息会把offset记录提交到__consumer_offsets, 提交过去的时候, key是 consumerGroupId + topic + 分区号 value是offset的值.
         * topic的一个分区在同一个消费组只能被一个消费者消费, 所以在提交offset的时候, 没有并发冲突问题.
         * 因为__consumer_offsets可能会接受很高的并发, kafka默认给其分配50个分区(offsets.topic.num.partitions), 这样可以通过加机器的方式抗大并发.
         */
        /**
         * [zk: localhost:2181(CONNECTED) 5] ls /brokers/topics/__consumer_offsets/partitions
         * [44, 45, 46, 47, 48, 49, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
         * 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43]
         */
        /**
         * 自动提交Offset会丢消息 和 消息重复消费的问题:
         *
         * [消息可靠性保证- 消息消费端]
         * 原因: 消息消费到一半, offset提交了, 假如此时consumer挂机了[或者Rebalance了], 未处理完的数据就丢了, 下次重启就会从最新提交的offset处开始消费.
         * 解决办法: 关闭手动提交
         *
         * [消息重复消费- 消息消费端] 消费端幂等处理
         * 原因: 消息处理到一半, 且这一半已经处理完了, 但还没来得及提交, 服务挂了, 下次重启又回拉取校共同的一批数据进行消费, 此时就会重复处理.
         * 解决办法: 关闭手动提交, 且做好幂等性处理, 因为无论自动还是手动提交, 都会有重复消费的问题.
         */
        // 自动提交offset的间隔时间(auto.commit.interval.ms), 默认为1s.
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

        //props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        /**
         *当有新的ConsumerGroup加入后, 此时offset信息不存在, 此时有三种设置方式(auto.offset.reset):
         *  1. earliest, 第一次从头开始消费，以后按照消费offset记录继续消费，这个需要区别于consumer.seekToBeginning(每次都从头开始消费).
         *  2. latest(默认), 自动将Offset设置为最大的offset, 即只消费自己启动之后发送到topic的消息.
         *  3. none, 直接报错. NoOffsetForPartitionException.
         */
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        /*
         * Broker会通过心跳确认consumer是否有故障, 如果一段时间没收到心跳, 就会通过心跳下发Rebalance的指令, 通知其他consumer进行rebalance操作.
         *
         * 一般比Session的TIMEOUT的1/3要小, 尽量小一点.
         */
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 1000);
        /*
         * 服务端broker多久感知不到一个consumer心跳就认为他故障了，会将其踢出消费组，进行Rebalance,
         * 对应的Partition也会被重新分配给其他consumer，默认是10秒
         */
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10 * 1000);


        //一次poll最大拉取消息的条数，如果消费者处理速度很快，可以设置大点，如果处理速度一般，可以设置小点
        // 根据吞吐量来定.
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);

        /*
        如果两次poll操作间隔超过了这个时间，broker就会认为这个consumer处理能力太弱，
        会将其踢出消费组，将分区分配给别的consumer消费
        */
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 30 * 1000);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);

        return consumer;
    }
    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = getKafkaConsumer();

        consumer.subscribe(Arrays.asList(TOPIC_NAME));
        // 消费指定分区
        // consumer.assign(Arrays.asList(new TopicPartition(TOPIC_NAME, 0)));

        // 常用的消费消息方式: 回溯消费, 从尾部消费, 从指定offset消费.
        //消息回溯消费, 默认从消息的最尾端开始消费.
        /*consumer.assign(Arrays.asList(new TopicPartition(TOPIC_NAME, 0)));
        consumer.seekToBeginning(Arrays.asList(new TopicPartition(TOPIC_NAME, 0)));*/
        //指定offset消费
        /*consumer.assign(Arrays.asList(new TopicPartition(TOPIC_NAME, 0)));
        consumer.seek(new TopicPartition(TOPIC_NAME, 0), 10);*/

        // 另一种消费方式: 从指定时间点开始消费.
        // List<PartitionInfo> topicPartitions = consumer.partitionsFor(TOPIC_NAME);
        // //从1小时前开始消费
        // long fetchDataTime = new Date().getTime() - 1000 * 60 * 60;
        // Map<TopicPartition, Long> map = new HashMap<>();
        // for (PartitionInfo par : topicPartitions) {
        //     map.put(new TopicPartition(TOPIC_NAME, par.partition()), fetchDataTime);
        // }
        // Map<TopicPartition, OffsetAndTimestamp> parMap = consumer.offsetsForTimes(map);
        // for (Map.Entry<TopicPartition, OffsetAndTimestamp> entry : parMap.entrySet()) {
        //     TopicPartition topicPartition = entry.getKey();
        //     OffsetAndTimestamp value = entry.getValue();
        //     if (topicPartition == null || value == null) continue;
        //     Long offset = value.offset();
        //     System.out.println("partition-" + topicPartition.partition() + "|offset-" + offset);
        //     System.out.println();
        //     //根据消费里的timestamp确定offset
        //     consumer.assign(Arrays.asList(topicPartition));
        //     consumer.seek(topicPartition, offset);
        // }

        while (true) {
            /*
             * poll() API 是拉取消息的长轮询
             *
             * 如果1s内拉到消息, 就返回. 如果到1s拉不到消息, 也返回. 实现原理 TODO
             */
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("收到消息：partition = %d,offset = %d, key = %s, value = %s%n", record.partition(),
                    record.offset(), record.key(), record.value());

                consumer.commitSync();
            }



            /*if (records.count() > 0) {
                // 手动同步提交offset，当前线程会阻塞直到offset提交成功
                // 一般使用同步提交，因为提交之后一般也没有什么逻辑代码了
                consumer.commitSync();

                // 手动异步提交offset，当前线程提交offset不会阻塞，可以继续处理后面的程序逻辑
                consumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                        if (exception != null) {
                            System.err.println("Commit failed for " + offsets);
                            System.err.println("Commit failed exception: " + exception.getStackTrace());
                        }
                    }
                });

            }*/
        }


    }
}
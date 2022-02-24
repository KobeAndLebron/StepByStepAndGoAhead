package com.cjs.mq;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

/**
 * 分区再平衡前, 提交offset.
 *
 */
public class MsgConsumer1 {
    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = MsgConsumer.getKafkaConsumer();

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

}

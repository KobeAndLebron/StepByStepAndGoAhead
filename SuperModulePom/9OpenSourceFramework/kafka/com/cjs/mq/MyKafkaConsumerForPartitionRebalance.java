package com.cjs.mq;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;

/**
 * 用来模拟Consumer发生变化时, 分区再平衡情况的发生.
 */
public class MyKafkaConsumerForPartitionRebalance {
    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = MyKafkaConsumer.getKafkaConsumer();

        consumer.subscribe(Collections.singletonList("topic1"));

        while (true) {
            ConsumerRecords<String, String> poll = consumer.poll(Duration.ofSeconds(10));
            for (ConsumerRecord<String, String> record : poll) {
                System.out.println("消息处理完毕, " + "消息所在分区:" + record.partition() +
                    "; 消息ID: " + record.offset() + "; 消息内容:" + record.key() + ":" + record.value());

//                consumer.commitAsync();
            }
        }
    }


}

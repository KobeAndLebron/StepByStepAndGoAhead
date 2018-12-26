package com.cjs.gohead.opensource.storm.simple;

import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.bolt.KafkaBolt;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Created by ChenJingShuai on 18-12-13.
 */
public class SimpleTopology {
    public static void main(String[] args) {
        TopologyBuilder topologyBuilder = new TopologyBuilder();

        KafkaSpoutConfig.Builder<String, String> builder =
                new KafkaSpoutConfig.Builder<>("localhost:9092", "test");
        builder.setProp(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        builder.setProp(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        builder.setProp(ConsumerConfig.GROUP_ID_CONFIG, "kafka-spout2");
        KafkaSpoutConfig<String, String> kafkaSpoutConfig = new KafkaSpoutConfig<>(builder);
        KafkaSpout<String, String> kafkaSpout = new KafkaSpout<>(kafkaSpoutConfig);
        topologyBuilder.setSpout("kafka-spout", kafkaSpout);

        SimplePrintBolt simplePrintBolt = new SimplePrintBolt();
        topologyBuilder.setBolt("simple-print-bolt", simplePrintBolt).noneGrouping("kafka-spout");

        KafkaBolt<String, String> kafkaBolt = new KafkaBolt<>();
        Properties producerProperties = new Properties();
        producerProperties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        producerProperties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaBolt.withProducerProperties(producerProperties);
        kafkaBolt.withTopicSelector("test-for-storm");

        topologyBuilder.setBolt("kafka-bolt", kafkaBolt).noneGrouping("simple-print-bolt", "send-to-kafka");

        Config config = new Config();
        config.setNumWorkers(2);
        config.put(Config.NIMBUS_SEEDS, Arrays.asList("127.0.0.1"));
        config.setDebug(true);

        try {
            StormSubmitter.submitTopology("simple-topology", config, topologyBuilder.createTopology());
        } catch (AlreadyAliveException | InvalidTopologyException | AuthorizationException e) {
            e.printStackTrace();
        }


        /*LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(SimpleTopology.class.getSimpleName(), config, topologyBuilder.createTopology());
        while(true);*/
    }
}
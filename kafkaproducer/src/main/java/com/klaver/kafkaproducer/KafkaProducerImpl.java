package com.klaver.kafkaproducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.UUID;

public class KafkaProducerImpl {

    private Producer<String, String> producer = null;

    public void producerInitialize(String kafkaServerLocation) {
        Properties kafkaProducerPro = new Properties();
        kafkaProducerPro.put("metadata.broker.list", kafkaServerLocation);
        kafkaProducerPro.put("request.required.acks", "1");
        kafkaProducerPro.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerLocation);
        kafkaProducerPro.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "kafka.serializer.DefaultEncoder");
        kafkaProducerPro.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProducerPro.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(kafkaProducerPro);

    }

    public void publishMessage(String topic, String message) {
        UUID uuid = UUID.randomUUID();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, UUID.randomUUID().toString(), message);
        producer.send(producerRecord);

    }

    public Producer<String, String> getProducer() {
        return producer;
    }
}

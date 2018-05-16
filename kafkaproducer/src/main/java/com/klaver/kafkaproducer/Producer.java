package com.klaver.kafkaproducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Producer {

    private static Logger logger = LoggerFactory.getLogger(Producer.class);
    private String queueName;
    private String kafkaServerLocation;
    KafkaProducerImpl kafkaProducer;

    public Producer(String queueName, String kafkaServerLocation) {
        this.queueName = queueName;
        this.kafkaServerLocation = kafkaServerLocation;
        kafkaProducer = new KafkaProducerImpl();
        kafkaProducer.producerInitialize(kafkaServerLocation);
    }

    public void send(Message message) {
        KafkaMessage kafkaMessage = ProducerUtils.convertToKafkaMessage(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writeValueAsString(kafkaMessage);
            logger.debug("Read JSON: {}", jsonString);
            kafkaProducer.publishMessage(queueName, jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        if(kafkaProducer != null && kafkaProducer.getProducer() != null) {
            kafkaProducer.getProducer().close();
        }
    }



}

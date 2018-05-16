package com.klaver.kafkaproducer;

import org.apache.commons.io.FilenameUtils;

public class ProducerUtils {

    public static KafkaMessage convertToKafkaMessage(Message message) {
        KafkaMessage kafkaMessage = new KafkaMessage();
        kafkaMessage.setSource(new Source());
        if(message.getFilePath() != null) {
            String filePath = message.getFilePath();
            kafkaMessage.getSource().setFolderPath(FilenameUtils.getFullPathNoEndSeparator(filePath));
            kafkaMessage.getSource().setFileName(FilenameUtils.getName(filePath));
            kafkaMessage.getSource().setFileExtension(FilenameUtils.getExtension(filePath));
        }
        kafkaMessage.getSource().setFileType(message.getFileType());
        kafkaMessage.getSource().setGateway(message.getGateway());
        kafkaMessage.setContent(message.getValues());
        return kafkaMessage;
    }
}

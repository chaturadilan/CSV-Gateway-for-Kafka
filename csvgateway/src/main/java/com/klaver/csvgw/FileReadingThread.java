package com.klaver.csvgw;

import com.klaver.kafkaproducer.Message;
import com.klaver.kafkaproducer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileReadingThread implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(FileReadingThread.class);
    private String topicName;
    private String kafkaServerLocation;
    private String filePathName;
    private String fileType;
    private String delimiter;

    public FileReadingThread(String filePathName, String fileType, String topicName, String delimiter, String kafkaServerLocation) {
        this.topicName = topicName;
        this.kafkaServerLocation = kafkaServerLocation;
        this.filePathName = filePathName;
        this.fileType = fileType;
        this.delimiter = delimiter;
    }

    @Override
    public void run() {
        String line = "";
        BufferedReader bufferedReader = null;
        String[] headers = null;

        Producer producer = new Producer(topicName, kafkaServerLocation);

        try {
            bufferedReader = new BufferedReader(new FileReader(filePathName));

            if ((line = bufferedReader.readLine()) != null) {
                headers = line.split(delimiter);
            }

            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(delimiter);
                int i = 0;
                if (headers.length != values.length) {
                    logger.warn("Error while parsing the CSV headers length is not equal to values length in line {}" +
                            ". Headers length: {} and Values Length: {} ", i + 1, headers.length, values.length);
                }
                if (headers != null) {
                    Message message = new Message();
                    message.setGateway(CSVReader.GATEWAY);
                    message.setFileType(fileType);
                    message.setFilePath(filePathName);
                    Map<String, String> messageValues = new HashMap<>();
                    for (String header : headers) {
                        try {
                            messageValues.put(header, values[i]);
                        } catch (Exception ex) {
                            logger.error("Invalid value at header: {}", header);
                            messageValues.put(header, "");
                        }
                        i++;
                    }
                    message.setValues(messageValues);
                    producer.send(message);
                }

            }

        } catch (FileNotFoundException e) {
            logger.error("File Not Found", e);
        } catch (IOException e) {
            logger.error("IOException occurred", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.error("IOException occurred", e);
                }
            }
            producer.close();
        }
    }
}

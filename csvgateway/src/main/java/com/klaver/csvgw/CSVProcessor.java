package com.klaver.csvgw;

import com.klaver.filewatcher.FileProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CSVProcessor implements FileProcessor {

    private static final int THREAD_COUNT = 10;
    private static Logger logger = LoggerFactory.getLogger(CSVProcessor.class);
    private String fileType;
    private String topicName;
    private String delimiter;
    private String kafkaServerLocation;

    public CSVProcessor(String fileType, String topicName, String delimiter, String kafkaServerLocation) {
        this.fileType = fileType;
        this.topicName = topicName;
        this.delimiter = delimiter;
        this.kafkaServerLocation = kafkaServerLocation;
    }

    @Override
    public void processCreatedFile(String filePathName) {
        logger.info("Reading added file {}.", filePathName);
        logger.debug("DEBUG Reading added file {}.", filePathName);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        Runnable fileReadingThread = new FileReadingThread(filePathName, fileType, topicName, delimiter, kafkaServerLocation);
        executor.execute(fileReadingThread);
    }

    @Override
    public void processModifiedFile(String pathname) {
        logger.info("Reading modified file {}.", pathname);
        processCreatedFile(pathname);
    }
}

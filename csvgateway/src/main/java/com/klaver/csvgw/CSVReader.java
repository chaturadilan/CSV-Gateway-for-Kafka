package com.klaver.csvgw;

import com.klaver.filewatcher.DirectoryWatchingUtility;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


public class CSVReader {

    public static final String GATEWAY = "csv";
    public static final String FILE_EXTENSION = "csv";
    private static Logger logger = LoggerFactory.getLogger(CSVReader.class);


    public static void main(String[] args) {

        Properties prop = new Properties();

        String confPath = new File(CSVReader.class.getProtectionDomain().getCodeSource().getLocation()
                .getPath()).getParentFile().getPath() + "/conf/";
        PropertyConfigurator.configure(confPath + "log4j.properties");



        try {
            InputStream input = null;
            input = new FileInputStream(confPath + "config.properties");
            prop.load(input);
        } catch (Exception e) {
            logger.error("Error while reading the properties file", e);
        }

        try {
            Path directoryPath = Paths.get(args[0]);
            String fileType = prop.getProperty("csvgw.filetype");
            String delimiter = prop.getProperty("csvgw.delimiter");
            String topicName = prop.getProperty("csvgw.topicName");
            String kafkaServerLocation = prop.getProperty("csvgw.kafkaServer");
            logger.info("CSV Gateway started for file type {}", fileType);
            DirectoryWatchingUtility directoryWatchingUtility = new DirectoryWatchingUtility(directoryPath, FILE_EXTENSION,
                    new CSVProcessor(fileType, topicName, delimiter, kafkaServerLocation));
            logger.info("CSV gateway started is for {} files", fileType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

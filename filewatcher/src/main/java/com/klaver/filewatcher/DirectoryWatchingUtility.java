package com.klaver.filewatcher;

import io.methvin.watcher.DirectoryWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class DirectoryWatchingUtility {

    private static Logger logger = LoggerFactory.getLogger(DirectoryWatchingUtility.class);
    private DirectoryWatcher watcher;
    private FileProcessor fileProcessor;
    private String fileExtension;

    public DirectoryWatchingUtility(Path directoryToWatch, String fileExtension, FileProcessor fileProcessor) throws IOException {

        this.watcher = DirectoryWatcher.create(directoryToWatch, event -> {
            String filePath = event.path().toString();
            logger.info("{} {}", event.eventType(), filePath);

            if (filePath.endsWith("." + fileExtension)) {
                switch (event.eventType()) {
                    case CREATE:
                        fileProcessor.processCreatedFile(filePath);
                        break;
                    case MODIFY:
                        fileProcessor.processModifiedFile(filePath);
                        break;
                }
            }

        });
        this.fileProcessor = fileProcessor;
        watcher.watch();
    }

    public void stopWatching() throws IOException {
        watcher.close();
    }

    public CompletableFuture<Void> watch() {
        // you can also use watcher.watch() to block the current thread
        return watcher.watchAsync();
    }

}
package com.klaver.filewatcher;

public interface FileProcessor {

    public void processCreatedFile(String pathname);
    public void processModifiedFile(String pathname);
}

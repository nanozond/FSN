package org.ts_labs.example.model;

import org.ts_labs.example.FileSystemNavigator.FileType;

/**
 *  File system navigator core class
 *
 *  @author         Sergey Tatarinov
 *  @version        1.05 17.01.15.
 */
public class FileRecord{

    private String name;
    private long size;

    public FileRecord(String name, Long size){
        this.name = name;
        this.size = size;
    }

    public FileType getType(){
        return FileType.FILE;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String toString() {
        return name;
    }

}

package org.ts_labs.example.model;

import org.ts_labs.example.Utils;

import java.io.File;

/**
 *  File system navigator core class
 *
 *  @author         Sergey Tatarinov
 *  @version        1.04 17.01.15.
 */
public class FileRecord{

    private String name;
    private long size;
    private String fullName;

    public FileRecord(File file){
        setFullName(file.getAbsolutePath());
        setName(file.getName());
        setSize(Utils.getFileSize(file));
    }


    public boolean isFile(){
        return new File(getFullName()).isFile();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

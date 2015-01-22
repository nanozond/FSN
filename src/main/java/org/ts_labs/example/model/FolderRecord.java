package org.ts_labs.example.model;

import java.io.File;

/**
 *  File system navigator core class
 *
 *  @version        1.03 17.01.15.
 *  @author         Nano Zond
 */
public class FolderRecord extends FileRecord {

    private String Type;


    public FolderRecord(File file){
        super(file);
        setType("<DIR>");
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}

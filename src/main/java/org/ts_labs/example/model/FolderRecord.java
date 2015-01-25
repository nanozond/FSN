package org.ts_labs.example.model;

import org.ts_labs.example.FileSystemNavigator.FileType;

/**
 *  File system navigator core class
 *
 *  @version        1.04 17.01.15.
 *  @author         Sergey Tatarinov
 */
public class FolderRecord extends FileRecord {

    public FolderRecord(String name, long size){
        super(name, size);
    }

    public FileType getType() {
        return FileType.DIR;
    }
}

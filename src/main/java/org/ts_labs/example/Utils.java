package org.ts_labs.example;

import java.io.File;

/**
 *
 *  @author         Sergey Tatarinov
 *  @version        1.04 17.01.15.
 */
public class Utils {

    private Utils(){}

    /*public static int countFolders(List<FileRecord> filesAndFolders){
        int count = 0;
        Iterator<FileRecord> iterator = filesAndFolders.iterator();
        while(iterator.hasNext()){
            if (iterator.next().getType() == FileType.FILE) {
                count++;
            }
        }
        return count;
    }

    public static int countFiles(List<FileRecord>  filesAndFolders){
        int count = 0;
        Iterator<FileRecord> iterator = filesAndFolders.iterator();
        while(iterator.hasNext()){
            if (iterator.next().getType() == FileType.DIR) {
                count++;
            }
        }
        return count;
    }*/

    public static long getFileSize(File fileOrFolder){
        long size = 0;
        if (fileOrFolder.isFile()) {
            size = fileOrFolder.length();
        } else {
            File[] subFiles = fileOrFolder.listFiles();
            if (subFiles != null){
                for (File file : subFiles) {
                    if (file.isFile()) {
                        size += file.length();
                    } else {
                        size += getFileSize(file);
                    }
                }
            }
        }
        return size;
    }
}
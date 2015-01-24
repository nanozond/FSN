package org.ts_labs.example;

import org.ts_labs.example.model.FileRecord;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

/**
 *  File system navigator core class
 *
 *  @author         Sergey Tatarinov
 *  @version        1.04 17.01.15.
 */
public class Utils {

    private Utils(){}

    public static boolean validatePath(String path){
        return Files.exists((new File(path).toPath()).toAbsolutePath());
    }

    public static int countFolders(List<?> filesAndFolders){
        int count = 0;
        Iterator<?> iterator = filesAndFolders.iterator();
        while(iterator.hasNext()){
            FileRecord folderRecord = (FileRecord)iterator.next();
            if (!folderRecord.isFile()) {
                count++;
            }
        }
        return count;
    }

    public static int countFiles(List<?>  filesAndFolders){
        int count = 0;
        Iterator<?> iterator = filesAndFolders.iterator();
        while(iterator.hasNext()){
            FileRecord folderRecord = (FileRecord)iterator.next();
            if (folderRecord.isFile()) {
                count++;
            }
        }
        return count;
    }

    public static String[] preparePath(String[] args) {
        int argsLen = args.length - 1;
        if (args[argsLen].charAt(args[argsLen].length() - 1) != '\\'){
            args[argsLen] = args[argsLen] + "\\";
        }
        String[] argsNew = new String[argsLen];
        for (int i = 1; i < args.length; i++) {
            argsNew[i - 1] = args[i];
        }
        return argsNew;
    }

    public static long getFileSize(File fileOrFolder){
        long size = 0;
        try{
            if (fileOrFolder.isFile()) {
                size = fileOrFolder.length();
            } else {
                File[] subFiles = fileOrFolder.listFiles();
                for (File file : subFiles) {
                    if (file.isFile()) {
                        size += file.length();
                    } else {
                        size += getFileSize(file);
                    }
                }
            }
        }catch (NullPointerException e){
            System.err.println(e);
        }
        return size;
    }
}
package org.ts_labs.example;

import org.ts_labs.example.model.*;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;

import static org.ts_labs.example.Localization.Messages;
import static org.ts_labs.example.Localization.Messages.*;

/**
 *  File system navigator core class
 *
 *  @author         Sergey Tatarinov
 *  @version        1.06 17.01.15.
 */
public class FileSystemNavigator{

    private static final String DEFAULT_PATH = ".";
    private static Map<String, ArrayList<FileRecord>> recentDirs = new HashMap<String,
            ArrayList<FileRecord>>();

    public enum FileType{
        FILE, DIR
    }

    public FileSystemNavigator(){

    }

    public void readAndPrintDirContent(String currentDirPath){
        File currentDir = new File(currentDirPath != null ? currentDirPath : DEFAULT_PATH);
        if (!Files.exists(currentDir.toPath()) || !currentDir.isDirectory()){
            ConsolePrinter.exception(new IllegalArgumentException());
        }
        List<FileRecord> listFiles = storeDirContent(currentDir, currentDir.listFiles());
        ConsolePrinter.printListContent(currentDir.getAbsolutePath(), listFiles);
    }

    public void waitForInput(){
        Messages message;
        Scanner in = new Scanner(System.in);

        try {
            if (in.hasNext()) {
                in.skip(Pattern.compile(" *"));
                String[] commandString = in.nextLine().split(" ");
                message = Messages.getValue(commandString[0]);
                switch (message) {
                    case QUIT:
                        System.exit(0);
                        break;
                    case CD:
                        Main.main(Utils.preparePath(commandString));
                        break;
                    case RECENT:
                        List<String> recentDirsList = new ArrayList<String>();
                        recentDirsList.addAll(recentDirs.keySet());
                        ConsolePrinter.printRecentDirs(recentDirsList);
                        waitForInputRecentDirNumber(recentDirsList);
                        break;
                    case HELP:
                        ConsolePrinter.print(HELP);
                    default:
                        break;
                }
            }
        } catch (IllegalArgumentException e) {
            ConsolePrinter.exception(e);
        } finally {
            waitForInput();
        }
    }

    public void waitForInputRecentDirNumber(List<String> recentDirsList){
        Scanner in = new Scanner(System.in);
        try {
            if (in.hasNextInt()) {
                int recentDirNumber = in.nextInt();
                if (recentDirNumber > recentDirsList.size() ||
                        recentDirNumber < 0){
                    throw new IllegalArgumentException();
                }
                String key = recentDirsList.get(recentDirNumber - 1);
                ConsolePrinter.printListContent(key, recentDirs.get(key));
            }
        } catch (IllegalArgumentException e){
            ConsolePrinter.exception(e);
        }
    }

    private List<FileRecord> storeDirContent(File currentDir,File[] listFiles){
        ArrayList<FileRecord> fileRecords = new ArrayList<FileRecord>();

        try {
            for (File file : listFiles) {
                fileRecords.add((file.isFile())
                        ? new FileRecord(file.getName(), Utils.getFileSize(file))
                        : new FolderRecord (file.getName(), Utils.getFileSize(file)));
            }
        } catch (SecurityException e) {
            ConsolePrinter.exception(e);
        }
        Collections.sort(fileRecords, new Comparator<FileRecord>() {

            @Override
            public int compare(FileRecord first, FileRecord second) {
                if (first.getType() == FileType.DIR && second.getType() == FileType.FILE){
                    return - 1;
                } else if (first.getType() == FileType.FILE && second.getType() == FileType.DIR) {
                    return 1;
                } else {
                    return (first.getName().compareToIgnoreCase(second.getName()));
                }
            }
        });
        recentDirs.put(currentDir.getAbsolutePath(), fileRecords);
        return fileRecords;
    }
}

package org.ts_labs.example;

import org.ts_labs.example.model.*;

import java.io.File;
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
public class FileSystemNavigator {

    private static Map<String, ArrayList<FileRecord>> recentDirs =
            new HashMap<String, ArrayList<FileRecord>>();

    public FileSystemNavigator() {

    }

    public void readAndPrintFolderContent(String currentDir) {

        if (!Utils.validatePath(currentDir)) {
            ConsolePrinter.print(PATH_ERROR);
        }
        storeDirContent(currentDir);
        List<?> list = recentDirs.get(
                new File(currentDir).getAbsolutePath());
        ConsolePrinter.printListContent(currentDir, list);

    }

    public void waitForInput(){
        Messages message;
        Scanner in = new Scanner(System.in);

        try{
            if (in.hasNext()) {
                in.skip(Pattern.compile(" *"));
                String[] commandString = in.nextLine().split(" ");
                message = Messages.getValue(commandString[0]);
                switch (message) {
                    case QUIT:
                        System.exit(0);
                        break;
                    case CH_DIR:
                        Main.main(Utils.preparePath(commandString));
                        break;
                    case RECENT:
                        List<String> recentDirsList = new ArrayList<String>();
                        recentDirsList.addAll(recentDirs.keySet());
                        ConsolePrinter.printListContent(null, recentDirsList);
                        waitForInputRecentDirNumber(recentDirsList);
                        break;
                    case HELP:
                        ConsolePrinter.print(HELP);
                    default:
                        break;
                }
            }
        } catch (IllegalArgumentException e){
            ConsolePrinter.exception(e);
        }finally {
            waitForInput();
        }
    }

    public static void waitForInputRecentDirNumber(List<String> recentDirsList){

        Scanner in = new Scanner(System.in);
        try{
            if (in.hasNextInt()) {
                int recentDirNumber = in.nextInt();
                if (recentDirNumber > recentDirsList.size() ||
                        recentDirNumber < 0){
                    throw new IllegalArgumentException();
                }
                String key = recentDirsList.get(recentDirNumber - 1);
                ConsolePrinter.printListContent(key, recentDirs.get(key));
            }
        }catch (IllegalArgumentException e){
            ConsolePrinter.exception(e);
        }
    }

    private void storeDirContent(String currentDir){
        ArrayList<FileRecord> fileRecords = new ArrayList<FileRecord>();

        try{
            for (File record : new File(currentDir).listFiles()) {
                fileRecords.add((record.isFile())
                        ? new FileRecord(record)
                        : new FolderRecord(record));
            }
        }catch (SecurityException e){
            ConsolePrinter.exception(e);
        }
        Collections.sort(fileRecords, new Comparator<FileRecord>() {

            @Override
            public int compare(FileRecord first, FileRecord second) {
                if (first.getClass().getSimpleName().equals("FolderRecord") &&
                        second.getClass().getSimpleName().equals("FileRecord")) {
                    return - 1;
                } else if (first.getClass().getSimpleName().equals("FileRecord") &&
                        second.getClass().getSimpleName().equals("FolderRecord")) {
                    return 1;
                } else {
                    return (first.getName().compareToIgnoreCase(second.getName()));
                }
            }
        });
        recentDirs.put(new File(currentDir).getAbsolutePath(), fileRecords);
    }



}

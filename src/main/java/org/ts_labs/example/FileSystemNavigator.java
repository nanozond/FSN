package org.ts_labs.example;

import org.ts_labs.example.model.FileRecord;
import org.ts_labs.example.model.FolderRecord;

import java.io.File;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;


/**
 *  File system navigator core class
 *
 *  @author         Sergey Tatarinov
 *  @version        1.06 17.01.15.
 */
public class FileSystemNavigator{

    private static final String APPLICATION_PATH = "";
    private static final String PARENT_DIR = "..";

    private  Map<String, List<FileRecord>> recentDirs = new TreeMap<String, List<FileRecord>>();
    private String currentDir;

    public enum FileType{
        FILE,
        DIR
    }

    public enum Commands{
        CD,
        HELP,
        LS,
        QUIT,
        REC;

        public static Commands getValue(String name) throws
                IllegalArgumentException{
            for (Commands Command : values()){
                if (name.toLowerCase().equals(Command.name().toLowerCase())){
                    return Command;
                }
            }
            return null;
        }
    }

    public FileSystemNavigator(){
        try {
            currentDir = new File(APPLICATION_PATH).getAbsolutePath();
        } catch (SecurityException e) {
            ConsolePrinter.exception(e);
        }

    }

    public void readAndPrintDirContent(){
        storeDirContent();
        ConsolePrinter.printDirContent(currentDir, recentDirs.get(currentDir));
    }

    public void changeDirectory(String newPath){
        Path path;

        if (newPath.equals(PARENT_DIR)){
            path = Paths.get(currentDir).getParent();
        } else {
            try {
                path = Paths.get(currentDir, newPath);
            } catch (InvalidPathException e) {
                path = Paths.get(newPath);
            }
        }
        if (path != null){
            if (Files.exists(path)){
                currentDir = path.toAbsolutePath().toString();
            } else {
                path = Paths.get(newPath);
                if (Files.exists(path)){
                    currentDir = path.toAbsolutePath().toString();
                } else {
                    ConsolePrinter.printError(Localization.Messages.PATH_ERROR);
                    return;
                }
            }
        }
    }

    public void waitForInput(){
        Commands command;
        Scanner in = new Scanner(System.in);

        ConsolePrinter.printCurDir(currentDir);
        if (in.hasNext()) {
            in.skip(Pattern.compile(" *"));
            String[] commandString = in.nextLine().split(" ");
            command = Commands.getValue(commandString[0]);
            if (command != null) {
                switch (command) {
                    case CD:
                        if (commandString.length < 2) {
                            break;
                        }
                        changeDirectory(commandString[1]);
                        break;
                    case HELP:
                        ConsolePrinter.printHelp();
                        break;
                    case LS:
                        readAndPrintDirContent();
                        break;
                    case REC:
                        if (recentDirs.size() == 0) {
                            ConsolePrinter.printError(Localization.Messages.NO_RECENT);
                            break;
                        }
                        List<String> recentDirsList = new ArrayList<String>();
                        recentDirsList.addAll(recentDirs.keySet());
                        ConsolePrinter.printRecentDirs(recentDirsList);
                        waitForInputRecentDirNumber(recentDirsList);
                        break;
                    case QUIT:
                        System.exit(0);
                        break;
                    default:
                        break;
                }
            } else {
                ConsolePrinter.printError(Localization.Messages.UNKNOWN_COM);
            }
            waitForInput();
        }

    }

    public void waitForInputRecentDirNumber(List<String> recentDirsList){
        Scanner in = new Scanner(System.in);
        if (in.hasNextInt()) {
            int recentDirNumber = in.nextInt();
            if (recentDirNumber > recentDirsList.size() ||
                    recentDirNumber < 0){
                throw new IllegalArgumentException();
            }
            String key = recentDirsList.get(recentDirNumber - 1);
            ConsolePrinter.printDirContent(key, recentDirs.get(key));
            //ConsolePrinter.printCurDir(currentDir);
        }
    }

    private void storeDirContent(){
        List<FileRecord> fileList = new ArrayList<FileRecord>();

        File currentFile = new File(currentDir);
        for (File file : currentFile.listFiles()) {
            try {
                fileList.add((file.isFile())
                        ? new FileRecord(file.getName(), file.length())
                        : new FolderRecord(file.getName(), Utils.getFileSize(file)));
            } catch (SecurityException e) {
                ConsolePrinter.exception(e);
            }
        }
        Collections.sort(fileList, new Comparator<FileRecord>() {

            @Override
            public int compare(FileRecord first, FileRecord second) {
                if (first.getType() == FileType.DIR && second.getType() == FileType.FILE) {
                    return -1;
                } else if (first.getType() == FileType.FILE && second.getType() == FileType.DIR) {
                    return 1;
                } else {
                    return (first.getName().compareToIgnoreCase(second.getName()));
                }
            }
        });
        recentDirs.put(currentDir, fileList);
//        ConsolePrinter.printCurDir(currentDir);
    }
}

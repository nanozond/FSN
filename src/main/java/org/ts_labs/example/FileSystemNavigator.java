package org.ts_labs.example;

import org.ts_labs.example.model.FileRecord;
import org.ts_labs.example.model.FolderRecord;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

/**
 *  File system navigator core class
 *
 *  @version        1.05 17.01.15.
 *  @author         Nano Zond
 */
public class FileSystemNavigator {


    private static Map<String, ArrayList<FileRecord>> recentDirs =
            new HashMap<String, ArrayList<FileRecord>>();

    public FileSystemNavigator() {}

    public enum Commands { /**/
        QUIT("quit"),
        CHANGE_DIRECTORY("cd"),
        VIEW_RECENT("recent");

        private String name;

        Commands(String name){
            this.name = name;
        }

        public static String getName(Commands commands) {
            return commands.name;
        }

        public static Commands getValue(String name) throws
                IllegalArgumentException{
            for (Commands commands : values()){
                if (name.toLowerCase().equals(getName(commands))){
                    return commands;
                }
            }
            throw new IllegalArgumentException("Incorrect command name! ");
        }
    }

    public void readAndPrintFolderContent(String[] currentDir) {
        if (currentDir.length > 1){
            ConsolePrinter.error(0);    //argumentError type
        }
        String path = (currentDir.length == 0) ? "." : currentDir[0];
        if (!Utils.validatePath(path)) {
            ConsolePrinter.error(1);    //validationError type
        }

        storeDirContent(path);
        List<?> list = recentDirs.get(
                new File(path).getAbsolutePath());
        ConsolePrinter.printListContent(path, list);

    }

    public void waitForInput(){
        Commands command;
        Scanner in = new Scanner(System.in);
        try{
            if (in.hasNext()) {
                in.skip(Pattern.compile(" *"));
                String[] commandString = in.nextLine().split(" ");
                command = Commands.getValue(commandString[0]);
                switch (command) {
                    case QUIT:
                        System.exit(0);
                        break;
                    case CHANGE_DIRECTORY:
                        Main.main(Utils.preparePath(commandString));
                        break;
                    case VIEW_RECENT:
                        List<String> recentDirsList = new ArrayList<String>();
                        recentDirsList.addAll(recentDirs.keySet());
                        ConsolePrinter.printListContent("recentDir", recentDirsList);
                        waitForInputRecentDirNumber(recentDirsList);
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

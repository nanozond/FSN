package org.ts_labs.example;

import org.apache.log4j.Logger;
import org.ts_labs.example.model.FileRecord;
import org.ts_labs.example.model.FolderRecord;

import java.io.File;
import java.util.*;

/**
 *  File system navigator core class
 *
 *  @version        1.04 17.01.15.
 *  @author         Nano Zond
 */
public class ConsolePrinter {

    private static final String LINE =
            "──────────────────────────────────────────────────\n";
    private static final String ERROR_TEXT_FORMAT = "\n\u001B[31m       %s\u001B[0m\n";
    private static final Logger LOGGER =
            Logger.getLogger(FileSystemNavigator.class);
    private static ResourceBundle localization =
            ResourceBundle.getBundle("localization",Locale.getDefault());

    private ConsolePrinter() {}

     public static void error(int type){
        LOGGER.error(String.format(ERROR_TEXT_FORMAT,
                localization.getString((type == 0) ? "errorArg" : "errorPath")));
        printMAN();
    }

    public static void exception(Exception e){
        LOGGER.error(String.format(ERROR_TEXT_FORMAT, e));
        printMAN();
    }

    @Deprecated
    public static void printDirContent(String dir,
                                       List<FileRecord> dirContents){
        StringBuilder sb = new StringBuilder();
        sb.append(LINE).append(new File(dir).getAbsolutePath());
        sb.append("\n").append(LINE);
        Iterator<FileRecord> iterator = dirContents.iterator();
        while(iterator.hasNext()){
                FileRecord folder = iterator.next();
            sb.append(String.format("%-35s%5s %15d bytes\n", folder.getName(),
                    (folder.isFile() ? "" : ((FolderRecord) folder).getType()),
                    folder.getSize()));
        }
        sb.append(LINE);
        sb.append(String.format("%s %d, %d %s & %d %s\n",
                localization.getString("totalCount"), dirContents.size(),
                Utils.countFolders(dirContents), localization.getString("dirCount"),
                Utils.countFiles(dirContents), localization.getString("fileCount")));
        sb.append(LINE).append(localization.getString("newCommand"));
        LOGGER.info(sb);
    }

    public static void printListContent(String dir,
                                        List<?> dirContents){
        StringBuilder sb = new StringBuilder();
        boolean receivedStringPathValidated = false;

        sb.append(LINE);
        if (Utils.validatePath(dir)) {
            sb.append(new File(dir).getAbsolutePath());
            receivedStringPathValidated = true;
        } else {
            sb.append(localization.getString(dir));
        }
        sb.append("\n").append(LINE);

        Iterator<?> iterator = dirContents.iterator();
        while(iterator.hasNext()){
            if (receivedStringPathValidated){
                FileRecord folder = (FileRecord)iterator.next();
                sb.append(String.format("%-35s%5s %15d bytes\n", folder.getName(),
                        (folder.isFile() ? "" : ((FolderRecord) folder).getType()),
                        folder.getSize()));

            } else {
                String dirName = (String) iterator.next();
                sb.append(String.format("%3d. %-35s\n",
                        dirContents.indexOf(dirName)+1,dirName.replace('.',' ')));
            }

        }
        sb.append(LINE);
        if (receivedStringPathValidated) {
            sb.append(String.format("%s %d, %d %s & %d %s\n",
                    localization.getString("totalCount"), dirContents.size(),
                    Utils.countFolders(dirContents), localization.getString("dirCount"),
                    Utils.countFiles(dirContents), localization.getString("fileCount")));
            sb.append(LINE).append(localization.getString("newCommand"));
        } else {
            sb.append(localization.getString("enterRecent"));
        }
        LOGGER.info(sb);
    }

    @Deprecated
    public static void printRecentDirsList(List<String> recentDirsList){
        StringBuilder sb = new StringBuilder();
        sb.append(LINE).append(localization.getString("recentDir"));
        sb.append("\n").append(LINE);
        Iterator<String> iterator = recentDirsList.iterator();
        while(iterator.hasNext()) {
            String dirName = iterator.next();
            sb.append(String.format("%3d. %-35s\n",
                    recentDirsList.indexOf(dirName)+1,dirName.replace('.',' ')));
        }
        sb.append(LINE).append(localization.getString("enterRecent"));
        LOGGER.info(sb);
    }


    public static void printMAN(){
        LOGGER.info(String.format("%s\n%s\n%s\n%s\n", localization.getString("man"),
                localization.getString("quit"), localization.getString("cd"),
                localization.getString("recent")));
    }

}

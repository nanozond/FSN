package org.ts_labs.example;

import org.apache.log4j.Logger;
import org.ts_labs.example.model.FileRecord;

import java.io.File;
import java.util.*;

import static org.ts_labs.example.Localization.Messages.*;

/**
 *  File system navigator core class
 *
 *  @author         Sergey Tatarinov
 *  @version        1.06 23.01.15.
 */
public class ConsolePrinter {

    private static final String LINE =
            "\n──────────────────────────────────────────────────\n";
    public static final String ERROR_TEXT_FORMAT_START = "\n\u001B[31m";
    public static final String ERROR_TEXT_FORMAT_STOP = "\u001B[0m\n";
    private static Logger log = Logger.getLogger("user");
    private static Logger logError = Logger.getLogger("errorLog");
    private static Localization loc = Localization.getInstance();
    private static StringBuilder sb = new StringBuilder();


    private ConsolePrinter() {

    }

    public static void print(Localization.Messages enumMessage){
        sb.delete(0, sb.length());
        sb.append(loc.toString(enumMessage));
        printHelp(sb);
        log.info(sb);
    }

    public static void exception(Exception e){
        sb.delete(0, sb.length());
        sb.append("\n");
        sb.append(ERROR_TEXT_FORMAT_START).append(e.getMessage());
        printHelp(sb);
        log.info(sb.append(ERROR_TEXT_FORMAT_STOP));
        logError.error("", e);
    }

    public static void printListContent(String dir, List<FileRecord> dirContents){
        sb.delete(0,sb.length());
        sb.append(LINE);
        sb.append(new File(dir).getAbsolutePath());
        sb.append(LINE);
        Iterator<FileRecord> iterator = dirContents.iterator();
        while(iterator.hasNext()){
            FileRecord fileRecord = iterator.next();
            sb.append(String.format("%-35s<%5s> %15d bytes\n", fileRecord.getName(),
                    fileRecord.getType().toString(), fileRecord.getSize()));
        }
        sb.append(LINE);
        sb.append(String.format("%s %d, %d %s & %d %s\n", loc.toString(WORD_TOTAL), dirContents.size(),
                    Utils.countFolders(dirContents), loc.toString(WORD_FOLDERS),
                    Utils.countFiles(dirContents), loc.toString(WORD_FILES)));
        sb.append(LINE).append(loc.toString(NEW_COM)).append("\n");
        log.info(sb);
    }

    public static void printRecentDirs(List<String> recentDirs) {
        ArrayList<String> rDirs = new ArrayList<String>();
        rDirs.addAll(recentDirs);
        sb.delete(0, sb.length());
        sb.append(LINE).append(loc.toString(R_DIRS)).append("\n").append(LINE);
        for (String recentDir : rDirs){
            sb.append(String.format("%3d. %-35s\n", rDirs.indexOf(recentDir)+1, recentDir.replace
                    ('.', ' ')));
        }
        sb.append(LINE).append(loc.toString(NUM_RECENT)).append("\n");
        log.info(sb);
    }

    public static StringBuilder printHelp(StringBuilder sb) {
        sb.append(String.format("\n%s\n%s\n%s\n", loc.toString(QUIT),
                loc.toString(CD), loc.toString(RECENT)));
        return sb;
    }



}

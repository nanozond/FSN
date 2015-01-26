package org.ts_labs.example;

import org.apache.log4j.Logger;
import org.ts_labs.example.model.FileRecord;

import java.util.ArrayList;
import java.util.List;

import static org.ts_labs.example.FileSystemNavigator.FileType.FILE;
import static org.ts_labs.example.Localization.Messages.*;

/**
 *
 *  @author         Sergey Tatarinov
 *  @version        1.06 23.01.15.
 */
public class ConsolePrinter {

    private static final String LINE =
            "\n──────────────────────────────────────────────────\n";
    public static final String ERROR_TEXT_FORMAT_START = "\n\u001B[31m";
    public static final String ERROR_TEXT_FORMAT_STOP = "\u001B[0m";
    public static final String POS_CURS = "\u001B[";
    private static final Logger LOG = Logger.getLogger("user");
    /*private static final Logger logError = Logger.getLogger("errorLog");*/

    private ConsolePrinter() {

    }

    public static void print(Localization.Messages enumMessage){
        StringBuilder sb = new StringBuilder();

        sb.append(Localization.getInstance().getString(enumMessage));
        LOG.info(sb);
    }

    public static void printError(Localization.Messages enumMessage){
        StringBuilder sb = new StringBuilder();

        sb.append(ERROR_TEXT_FORMAT_START);
        sb.append(Localization.getInstance().getString(enumMessage));
        LOG.error(sb.append(ERROR_TEXT_FORMAT_STOP));
    }

    public static void printCurDir(String currentDir){
        StringBuilder sb = new StringBuilder();
        sb.append(currentDir).append('>');
        sb.append(POS_CURS).append(currentDir.length()).append("C");
        LOG.info(sb);
    }

    public static void exception(Exception e){
        StringBuilder sb = new StringBuilder();
        sb.append(ERROR_TEXT_FORMAT_START).append(e.getMessage());
        printHelp();
        LOG.error(sb.append(ERROR_TEXT_FORMAT_STOP), e);
    }

    public static void printDirContent(String dirPath, List<FileRecord> fileList){
        StringBuilder sb = new StringBuilder();
        int filesCount = 0;
        int dirsCount = 0;

        sb.append(LINE);
        sb.append(dirPath);
        sb.append(LINE);
        for (FileRecord fileRecord : fileList) {
            if (fileRecord.getType() == FILE) {
                filesCount++;
            } else {
                dirsCount++;
            }
            sb.append(String.format("%-35s<%5s> %15d bytes\n", fileRecord.getName(),
                    fileRecord.getType(), fileRecord.getSize()));
        }
        sb.append(LINE);
        sb.append(String.format("%s %d, %d %s & %d %s", Localization.getInstance().getString(WORD_TOTAL),
                fileList.size(), dirsCount, Localization.getInstance().getString(WORD_FOLDERS),
                filesCount, Localization.getInstance().getString(WORD_FILES)));
        sb.append(LINE).append(Localization.getInstance().getString(NEW_COM)).append("\n");
        LOG.info(sb);
    }

    public static void printRecentDirs(List<String> recentDirs) {
        ArrayList<String> rDirs = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();

        rDirs.addAll(recentDirs);
        sb.append(LINE).append(Localization.getInstance().getString(R_DIRS)).append("\n").append
                (LINE);
        for (String recentDir : rDirs){
            sb.append(String.format("%3d. %-35s\n", rDirs.indexOf(recentDir)+1, recentDir.replace
                    ('.', ' ')));
        }
        sb.append(LINE).append(Localization.getInstance().getString(NUM_RECENT)).append("\n>");
        LOG.info(sb);
    }

    public static void printHelp() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n%s\n%s\n%s\n%s\n%s\n\n",
                Localization.getInstance().getString(HELP),
                Localization.getInstance().getString(CD),
                Localization.getInstance().getString(REC),
                Localization.getInstance().getString(LS),
                Localization.getInstance().getString(QUIT)));
        LOG.info(sb);
    }



}

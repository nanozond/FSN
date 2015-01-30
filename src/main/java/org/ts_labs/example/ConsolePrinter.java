package org.ts_labs.example;

import org.apache.log4j.Logger;
import org.ts_labs.example.FileSystemNavigator.FileType;
import org.ts_labs.example.model.FileRecord;

import java.util.ArrayList;
import java.util.List;

import static org.ts_labs.example.Localization.Messages.*;

/**
 *
 *  @author         Sergey Tatarinov
 *  @version        1.06 23.01.15.
 */
public class ConsolePrinter {

    public static final String ERROR_TEXT_FORMAT = "\u001B[31m    %s\u001B[0m";

    private static final char LINE_SYMBOL = '\u2500';
    private static final Logger LOG = Logger.getLogger("user");

    private ConsolePrinter() {

    }

    public static String printLine(){
        StringBuilder line = new StringBuilder();
        line.append('\n');
        for (int i = 0; i < 60; i++) {
            line.append(LINE_SYMBOL);
        }
        return line.append('\n').toString();
    }

    public static void print(Localization.Messages enumMessage){
        StringBuilder sb = new StringBuilder();

        sb.append(Localization.getInstance().getString(enumMessage));
        LOG.info(sb);
    }

    public static void printError(Localization.Messages enumMessage){
        StringBuilder sb = new StringBuilder();

        sb.append(String.format(ERROR_TEXT_FORMAT,
                Localization.getInstance().getString(enumMessage)));
        LOG.error(sb);
    }

    public static void printCurDir(String currentDir){
        StringBuilder sb = new StringBuilder();

        sb.append(currentDir).append("> ");
        LOG.info(sb);
    }

    public static void exception(Exception e){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(ERROR_TEXT_FORMAT, e.getMessage()));
        LOG.error(e);
        printHelp();

    }

    public static void printDirContent(String dirPath, List<FileRecord> fileList){
        StringBuilder sb = new StringBuilder();
        int filesCount = 0;
        int dirsCount = 0;

        sb.append(printLine());
        sb.append(dirPath);
        sb.append(printLine());
        for (FileRecord fileRecord : fileList) {
            if (fileRecord.getType() == FileType.FILE) {
                filesCount++;
            } else {
                dirsCount++;
            }
            sb.append(String.format("%-35s<%5s> %15d bytes\n", fileRecord.getName(),
                    fileRecord.getType(), fileRecord.getSize()));
        }
        sb.append(printLine());
        sb.append(String.format("%s %d, %d %s & %d %s", Localization.getInstance().getString(WORD_TOTAL),
                fileList.size(), dirsCount, Localization.getInstance().getString(WORD_FOLDERS),
                filesCount, Localization.getInstance().getString(WORD_FILES)));
        sb.append(printLine()).append(Localization.getInstance().getString(NEW_COM)).append("\n");
        LOG.info(sb);
    }

    public static void printRecentDirs(List<String> recentDirs) {
        ArrayList<String> rDirs = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();

        rDirs.addAll(recentDirs);
        sb.append(printLine()).append(Localization.getInstance().getString(R_DIRS)).append(printLine());
        for (String recentDir : rDirs){
            sb.append(String.format("%3d. %-35s\n", rDirs.indexOf(recentDir)+1, recentDir.replace
                    ('.', ' ')));
        }
        sb.append(printLine()).append(Localization.getInstance().getString(NUM_RECENT)).append("\n>");
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

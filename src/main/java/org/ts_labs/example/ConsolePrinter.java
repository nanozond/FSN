package org.ts_labs.example;

import org.apache.log4j.Logger;
import org.ts_labs.example.model.FileRecord;
import org.ts_labs.example.model.FolderRecord;

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
            "──────────────────────────────────────────────────\n";
    private static Logger log = Logger.getLogger(FileSystemNavigator.class);
    private static Localization loc = Localization.getLocalization();
    private static StringBuilder sb = new StringBuilder();


    private ConsolePrinter() {

    }

    public static void print(Localization.Messages enumMessage){
        sb.delete(0, sb.length());
        sb.append(loc.getText(enumMessage));
        printHelp(sb);
        log.info(sb);
    }

    public static void exception(Exception e){
        sb.delete(0, sb.length());
        sb.append("\n\n");
        printHelp(sb);
        log.info(e.getLocalizedMessage() + sb);
    }

    public static void printListContent(String dir,
                                        List<?> dirContents){

        boolean receivedStringPathValidated = false;

        sb.append(LINE);
        if(dir != null){
            sb.append(new File(dir).getAbsolutePath());
            receivedStringPathValidated = true;
        } else {
            sb.append(loc.getText(R_DIRS));
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
                        dirContents.indexOf(dirName) + 1,
                        dirName.replace('.',' ')));
            }
        }
        sb.append(LINE);
        if (receivedStringPathValidated) {
            sb.append(String.format("%s %d, %d %s & %d %s\n",
                    loc.getText(TOTAL), dirContents.size(),
                    Utils.countFolders(dirContents), loc.getText(DIRS),
                    Utils.countFiles(dirContents), loc.getText(FILES)));
            sb.append(LINE).append(loc.getText(NEW_COM)).append("\n");
        } else {
            sb.append(loc.getText(NUM_RECENT));
        }
        log.info(sb);
    }

     public static StringBuilder printHelp(StringBuilder sb) {
         sb.append(String.format("\n%s\n%s\n%s\n", loc.getText(QUIT),
                 loc.getText(CH_DIR), loc.getText(RECENT)));
        return sb;
     }

}

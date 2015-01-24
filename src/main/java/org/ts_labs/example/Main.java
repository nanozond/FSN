package org.ts_labs.example;

/**
 *  Starting class
 *
 *  @author         Sergey Tatarinov
 *  @version        1.04 17.01.15.
 */
public class Main {

    public static void main(String[] args) {

        FileSystemNavigator fileSystemNavigator = new FileSystemNavigator();

        switch (args.length){
            case 3:
                Localization.setLocalization(new String[] {args[1], args[2]});
                fileSystemNavigator.readAndPrintFolderContent(args[0]);
                break;
            default:
                Localization.setLocalization(new String[]{});
                fileSystemNavigator.readAndPrintFolderContent(".");
        }

        fileSystemNavigator.waitForInput();
    }
}

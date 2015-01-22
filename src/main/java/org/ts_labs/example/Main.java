package org.ts_labs.example;

/**
 *  Starting class
 *
 *  @version        1.03 17.01.15.
 *  @author         Nano Zond
 */
public class Main {

    public static void main(String[] args) {

        FileSystemNavigator fileSystemNavigator = new FileSystemNavigator();

        fileSystemNavigator.readAndPrintFolderContent(args);

        fileSystemNavigator.waitForInput();
    }
}

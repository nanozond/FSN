package org.ts_labs.example;

/**
 *  Starting class
 *
 *  @author         Sergey Tatarinov
 *  @version        1.04 17.01.15.
 */
public class Main {
    public static void main(String[] args) {
        FileSystemNavigator fsn = new FileSystemNavigator();
        fsn.readAndPrintDirContent((args.length > 0) ? args[0] : null);
        fsn.waitForInput();
    }
}

package org.ts_labs.example;

/**
 *  Starting class
 *
 *  @author         Sergey Tatarinov
 *  @version        1.04 17.01.15.
 */
public class Main {

    public static void main(String[] args) {
        ConsolePrinter.print(Localization.Messages.HELLO);
        FileSystemNavigator fsn = new FileSystemNavigator();
        if(args.length > 0){
            fsn.changeDirectory(args[0]);
        }
        fsn.waitForInput();
    }
}

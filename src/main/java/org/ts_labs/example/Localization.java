package org.ts_labs.example;

import java.util.*;

/**
 * @author Sergey Tatarinov
 * @version 1.02 23.01.15
 */
public class Localization {

    private static ResourceBundle localization;
    private static Localization loc;

    public enum Messages{
        WORD_FOLDERS, WORD_FILES, WORD_TOTAL, RECENT, R_DIRS, NEW_COM,
        HELP, NUM_RECENT, QUIT, CD, PATH_ERROR, EXCEPTION;

        public static Messages getValue(String name) throws
                IllegalArgumentException{
            for (Messages message : values()){
                if (name.toLowerCase().equals(message.name().toLowerCase())){
                    return message;
                }
            }
            throw new IllegalArgumentException("Incorrect command name! ");
        }
    }

    private Localization() {
        Locale currentLocale = Locale.getDefault();
        localization = ResourceBundle.getBundle("localization", currentLocale);
    }

    public static String toString(Messages message){
        return localization.getString(message.name());
    }

    public static Localization getInstance() {
        if (loc == null){
            loc = new Localization();
        }
        return loc;
    }
}

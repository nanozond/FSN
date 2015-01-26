package org.ts_labs.example;

import java.util.*;

/**
 *
 * @author Sergey Tatarinov
 * @version 1.04 23.01.15
 */
public class Localization {

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("localization", Locale.getDefault());
    private static Localization loc;

    public enum Messages {
        CD,
        HELLO,
        HELP,
        LS,
        NEW_COM,
        NUM_RECENT,
        PATH_ERROR,
        QUIT,
        R_DIRS,
        REC,
        WORD_FOLDERS,
        WORD_FILES,
        WORD_TOTAL,
        UNKNOWN_COM,
        NO_RECENT
    }

    private Localization() {

    }

    public String getString(Messages message){
        return resourceBundle.getString(message.name());
    }

    public static Localization getInstance() {
        if (loc == null){
            loc = new Localization();
        }
        return loc;
    }
}

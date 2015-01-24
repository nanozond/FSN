package org.ts_labs.example;

import java.util.*;

/**
 * @author Sergey Tatarinov
 * @version 1.01 23.01.15
 */
public class Localization {

    private static final String ERROR_TEXT_FORMAT = "\n\u001B[31m       %s\u001B[0m\n";
    private static ResourceBundle localization;

    enum MessageTypes{ INFO, ERROR, ERROR_E, WARN }

    public enum Messages{
        DIRS("dirs", MessageTypes.INFO),
        FILES("files", MessageTypes.INFO),
        TOTAL("total", MessageTypes.INFO),
        RECENT("rDirs", MessageTypes.INFO),
        ARG_ERROR("errorArg", MessageTypes.ERROR),
        PATH_ERROR("errorPath", MessageTypes.ERROR),
        EXCEPTION("", MessageTypes.ERROR_E);

        private String name;
        private MessageTypes type;

        Messages(String name,MessageTypes type){
            this.name = name;
            this.type = type;
        }

        public MessageTypes getType() {
            return type;
        }

        @Override
        public String toString() {
            return localization.getString(name);
        }
    }

    private Localization() {

    }

    public static void setLocalizationProperties(String[] args){
        String language;
        String country;
        Locale currentLocale;

        if (args.length != 2) {
            language = new String("en");
            country = new String("US");
        } else {
            language = new String(args[0]);
            country = new String(args[1]);
        }
        currentLocale = new Locale(language, country);
        localization = ResourceBundle.getBundle("localization", currentLocale);
    }

    public static String toString(Messages locMessage, Exception e){
        switch (locMessage.getType()){
            case INFO:
                return localization.getString(locMessage.toString());
            case ERROR:
                return String.format(ERROR_TEXT_FORMAT,
                        localization.getString(locMessage.toString()));
            case ERROR_E:
                return String.format(ERROR_TEXT_FORMAT, e);
            default:
                return localization.getString(locMessage.toString());
        }
    }
}

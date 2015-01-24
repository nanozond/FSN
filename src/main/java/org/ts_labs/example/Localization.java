package org.ts_labs.example;

import java.util.*;

/**
 * @author Sergey Tatarinov
 * @version 1.02 23.01.15
 */
public class Localization {

    public static final String ERROR_TEXT_FORMAT = "\n\u001B[31m       %s\u001B[0m\n";
    private static ResourceBundle localization;
    private static Localization loc = new Localization();

    enum MessageTypes{ INFO, ERROR, ERROR_E }

    public enum Messages{
        DIRS("dirs", MessageTypes.INFO),
        FILES("files", MessageTypes.INFO),
        TOTAL("total", MessageTypes.INFO),
        RECENT("recent", MessageTypes.INFO),
        R_DIRS("rDirs", MessageTypes.INFO),
        NEW_COM("newCommand", MessageTypes.INFO),
        HELP("help", MessageTypes.INFO),
        NUM_RECENT("newRecent", MessageTypes.INFO),
        QUIT("quit", MessageTypes.INFO),
        CH_DIR("cd", MessageTypes.INFO),
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

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return localization.getString(name);
        }

        public static Messages getValue(String name) throws
                IllegalArgumentException{
            for (Messages message : values()){
                if (name.toLowerCase().equals(message.getName())){
                    return message;
                }
            }
            throw new IllegalArgumentException("Incorrect command name! ");
        }
    }

    private Localization() {
        setLocalization(new String[] {"en","US"});
    }

    public static String getText(Object ... args){
        Messages locMessage;
        Exception e = new Exception();
        switch(args.length){
            case 0:
                locMessage = Messages.EXCEPTION;
                e = new IllegalArgumentException();
                break;
            case 1:
                locMessage = (Messages) args[0];
                break;
            default:
                e = (Exception) args[1];
                locMessage = Messages.EXCEPTION;
        }
        switch (locMessage.getType()){
            case INFO:
                return locMessage.toString();
            case ERROR:
                return String.format(ERROR_TEXT_FORMAT, locMessage.toString());
            case ERROR_E:
                return String.format(ERROR_TEXT_FORMAT, e);
            default:
                return locMessage.toString();
        }
    }

    public static Localization getLocalization() {
        return loc;
    }

    public static void setLocalization(String [] args ) {
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
}

package com.safira.common;

/**
 * This class contains all the rgexes used to validate recieved user input.
 */
public class Regex {
    public static final String MONEY_FORMAT = "^\\d+\\.\\d+$";
    public static final String UUID_FORMAT = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
    public static final String PHONE_FORMAT = "^\\d{1,16}$";
    public static final String USERNAME_FORMAT = "^([0-9]*[a-z]*[A-Z]*)(?!.*\")(?=\\S+$).{6,50}$";
    public static final String PASSWORD_FORMAT = "^([0-9]*[a-z]*[A-Z]*[@#$%^&+=]*)(?!.*\")(?=\\S+$).{6,50}$";
    public static final String NUMBER_FORMAT = "^\\d{1,5}$";
    public static final String EMAIL_FORMAT = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
            "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+" +
            "(?:[A-Z]{2}|com|org|net|edu|gov|mil|biz|info|mobi|name|aero|asia|jobs|museum)\\b";
}

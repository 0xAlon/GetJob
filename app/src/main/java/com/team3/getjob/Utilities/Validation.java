package com.team3.getjob.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    //Password must contain minimum 8 characters at least 1 Alphabet, 1 Number, 1 special character
    public static boolean isValidPassword(String password) {
        final String PASSWORD_PATTERN =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isAlpha(String name) {
        String expression = "^[a-zA-Z]*$";
        CharSequence inputStr = name;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String phone) {
        return !(!phone.matches("(00972|0|\\+972)[5][0-9]{8}") && !phone.matches("(00970|0|\\+970)[5][0-9]{8}") && !phone.matches("(05[0-9]|0[12346789])([0-9]{7})") && !phone.matches("(00972|0|\\+972|0|)[2][0-9]{7}"));
    }

    public static boolean isValidId(String id) {
        CharSequence inputStr = id;
        Pattern pattern = Pattern.compile("[0-9]{9}");
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    public static boolean isValidAge(String age) {
        CharSequence inputStr = age;
        Pattern pattern = Pattern.compile("[1-9]{1}+[0-9]{1}");
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    public static boolean isValidCompany(String company) {
        return !company.isEmpty();
    }


}

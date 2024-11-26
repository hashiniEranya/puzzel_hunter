package com.example.nb_minegame.util;

import com.google.gson.Gson;

//this class help to process strings and easy to do some task
public class StringUtility {

    //will when we provide a String will that string is empty or not
    public static boolean isNotNull(String value) {
        if (value == null) {
            return false;
        } else if (value.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static String toString(int value) {
        return value + "";
    }

    public static String toString(double value) {
        return value + "";
    }

    public static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true;
        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;
            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }
        return false;
    }

    //Method That caps the first letter of a string
    public static String capitalizeFirst(String text) {
        String cap = text.substring(0, 1).toUpperCase() + text.substring(1);
        return cap;
    }

    public static String getFirstNStrings(String str, int n) {
        try {
            String[] sArr = str.split(" ");
            String firstStrs = "";
            for (int i = 0; i < n; i++){
                if(ExtenstionMethods.isNotEmptyString(sArr[i])){
                    firstStrs += sArr[i];
                }else{
                    n++;
                }
//            firstStrs += sArr[i] + " ";
            }
            return firstStrs.trim();
        }catch (Exception e){
            return "";
        }
    }

    //this method will remove unnecessary spaces in text
    public static String removeSpace(String str) {
        return str.replaceAll("[\\s|\\u00A0]+", " ");
    }

    public static boolean isSameString(String s1, String s2){
        if(s1!=null && s2!=null){
            return s1.equalsIgnoreCase(s2);
        }
        return false;
    }

    public static String objectToString(Object o){
        if(o!=null){
            return new Gson().toJson(o);
        }else {
            throw new NullPointerException();
        }
    }

    public static Object stringToObject(String st,Class clz){
        if(isNotNull(st)){
            return new Gson().fromJson(st,clz);
        }else {
            throw new NullPointerException();
        }
    }

}

package com.zybooks.databaselogin;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {
    ////////////////////////////// MICRO ID CHECK ///////////////////////////
    public static Boolean MicroCharacters(String microChip){
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(microChip);
        return hasSpecial.find();
    }
    ////////////////////////////// NAME ///////////////////////////
    public static Boolean isAllUpper(String name){
        String[] strArray = name.split(" ");

        for (int i = 0; i < strArray.length; i++){
            if(Character.isLowerCase(strArray[i].charAt(0))){
                return false;
            }
        }
        return true;
    }
    ////////////////////////////// EMAIL ///////////////////////////
    public static Boolean isPrefixCorrect(String email){
        String[] strArray = email.split("@");
        if (strArray[0].length() < 3){
            return false;
        }
        else{
            return true;
        }
    }
    public static Boolean isProperDomain(String email){

        String[] strArray = email.split("[.]");
        String[] DomainArray = {"edu", "co", "com", "gal" };
        for (int i = 0; i < DomainArray.length; i++){
            if(strArray[1].equals(DomainArray[i])){
                return true;
            }
        }
        return false;
    }
}

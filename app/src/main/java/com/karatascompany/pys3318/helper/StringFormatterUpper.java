package com.karatascompany.pys3318.helper;

import java.util.Locale;

public class StringFormatterUpper {
    public static String capitalizeWord(String str){
        try{
            String[] words =str.split("\\s");
            String capitalizeWord="";
            for(String w:words){
                String first=w.substring(0,1);
                String afterfirst=w.substring(1);
                capitalizeWord+=first.toUpperCase()+afterfirst+" ";
            }
            return capitalizeWord.trim();
        }catch (Exception ex){
            return str;
            //return ex.getLocalizedMessage();
        }


    }
}

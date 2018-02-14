package com.adsizzler.mangolaa.impressions.util
/**
 * Created by Ankush on 17/07/17.
 */
class Strings {

     static final String EMPTY = ""

    /**
     * Validate if a String has text in it. Whitespaces are not considered valid text
     * @param text The String that has to be validated
     * @return false if the String does not have text
     */
     static boolean hasText(String text){
        if(!text){
            return false
        }
        for(char ch in text.toCharArray()){
            if(!Character.isWhitespace(ch)){
                return true
            }
        }
        return false
    }

}

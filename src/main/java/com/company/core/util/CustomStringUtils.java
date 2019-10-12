package com.company.core.util;

public class CustomStringUtils {

	public static final boolean isNumber(String s) 
    { 
        for (int i = 0; i < s.length(); i++) 
        if (Character.isDigit(s.charAt(i))  == false) 
            return false; 
  
        return true; 
    } 
}

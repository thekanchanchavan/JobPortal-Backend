package com.zidio.project.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isValidPassword(String password){
        String regex = "(?=.*[0-9])"
                      +"(?=.*[a-z])(?=.*[A-Z])"
                      +"(?=.*[@#$%&*!^])"
                      +"(?=\\S+$).{4,20}$";
    Pattern p = Pattern.compile(regex);
    if(password == null || password.isEmpty() || password.length() < 4 || password.length() > 50){
        return false;
    }
    Matcher m = p.matcher(password);
    return m.matches();
    }
    public static boolean isStringLowerCase(String str){
        char[] charArray = str.toCharArray();
        for(int i = 0 ; i<charArray.length;i++){
            if(!Character.isLowerCase(charArray[i]))
            return false;
        }
        return true;
    }
    public static boolean isValidUsername(String username){
        System.out.println("username:" + username);
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern p = Pattern.compile(regex);
        if(username == null || username.isEmpty() || username.length() < 4 || username.length() > 50){
            System.out.println("invalid");
            return false;
        }
        Matcher m = p.matcher(username);
        System.out.println("result: "+ m.matches());
        return m.matches();
    }
    public static boolean isValidUsertype(String usertype){
        if(usertype == null || usertype.isEmpty()){
            return false;
        }
        if(usertype.equals("admin") || usertype.equals("student") ||
        usertype.equals("recruiter")){
            return true;
        }else{
            return false;
        }
    }
    
}

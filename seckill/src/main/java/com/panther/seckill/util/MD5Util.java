package com.panther.seckill.util;

import org.springframework.util.DigestUtils;

/**
 * @version 1.0
 * @ClassName MD5Util
 * @Description TODO
 * @date 2019-05-30 14:27
 */
public class MD5Util {
    
    private static final String salt = "1a2b3c4d";
    
    public static String md5(String src){
        return DigestUtils.md5DigestAsHex(src.getBytes());
    }
    
    public static String inputPassToFromPass(String pass){
        String s = ""+salt.charAt(0) + salt.charAt(2) + pass + salt.charAt(5) + salt.charAt(4);
        return md5(s);
    }

    public static String formPassToDBPass(String pass, String salt){
        String s = ""+salt.charAt(0) + salt.charAt(2) + pass + salt.charAt(5) + salt.charAt(4);
        return md5(s);
    }
    
    public static String inputPassToDBPass(String input, String DBSalt){
        String formPass = inputPassToFromPass(input);
        String dbPass = formPassToDBPass(formPass, DBSalt);
        return dbPass;
    }
    
    
    public static void main(String[] args){
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
        System.out.println(inputPassToFromPass("123456"));
    }
}

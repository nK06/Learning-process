package com.panther.seckill.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version 1.0
 * @ClassName ValidatorUtil
 * @Description TODO
 * @date 2019-05-31 10:55
 */
public class ValidatorUtil {
    private static final Pattern mobile_patter = Pattern.compile("1\\d{10}");
    
    public static boolean isMobile(String s){
        if(StringUtils.isEmpty(s)){
            return false;
        }
        Matcher m = mobile_patter.matcher(s);
        return m.matches();
    }
}

package com.panther.seckill.util;

import java.util.UUID;

/**
 * @version 1.0
 * @ClassName UUIDUtil
 * @Description TODO
 * @date 2019-07-24 10:00
 */
public class UUIDUtil { 
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
    
}

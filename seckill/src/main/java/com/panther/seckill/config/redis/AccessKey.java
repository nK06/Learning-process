package com.panther.seckill.config.redis;

/**
 * @version 1.0
 * @ClassName OrderKey
 * @Description TODO
 * @date 2019-05-29 15:25
 */
public class AccessKey extends BasePrefix{
    
    public AccessKey(String prefix) {
        super(prefix);
    }

    private AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    
    public static AccessKey accessLimitBy5Second = new AccessKey(5,"accessLimitBy5Second");
    
    public static AccessKey accessLimitBySecond(int expireSeconds) { 
        return new AccessKey(expireSeconds,"accessLimitBy"+expireSeconds+"Second");
    }
}

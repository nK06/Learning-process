package com.panther.seckill.config.redis;

/**
 * @version 1.0
 * @ClassName UserKey
 * @Description TODO
 * @date 2019-05-29 15:25
 */
public class SeckillUserKey extends BasePrefix{
    
    private static final int TOKEN_EXPIRE = 3600*24*2;

    private SeckillUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    
    private SeckillUserKey(String prefix) {
        super(prefix);
    }
    
    public static SeckillUserKey token = new SeckillUserKey(TOKEN_EXPIRE,"token");
    
    public static SeckillUserKey getById = new SeckillUserKey(0,"getById");
    
}

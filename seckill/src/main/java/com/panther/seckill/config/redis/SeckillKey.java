package com.panther.seckill.config.redis;

/**
 * @version 1.0
 * @ClassName OrderKey
 * @Description TODO
 * @date 2019-05-29 15:25
 */
public class SeckillKey extends BasePrefix{

    private static final int TOKEN_EXPIRE = 60;
    
    public SeckillKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    
    public static SeckillKey seckillEnd = new SeckillKey(0,"seckillFailed");
    
    public static SeckillKey getSeckillPath = new SeckillKey(TOKEN_EXPIRE,"seckillPath");
    
    public static SeckillKey getSeckillVerifyCode = new SeckillKey(300,"seckillVerifyCode");

    
}

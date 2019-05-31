package com.panther.seckill.config.redis;

/**
 * @version 1.0
 * @ClassName OrderKey
 * @Description TODO
 * @date 2019-05-29 15:25
 */
public class OrderKey extends BasePrefix{
    
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}

package com.panther.seckill.config.redis;

/**
 * @version 1.0
 * @ClassName OrderKey
 * @Description TODO
 * @date 2019-05-29 15:25
 */
public class OrderKey extends BasePrefix{
    
    public OrderKey(String prefix) {
        super(prefix);
    }

    private OrderKey(int expireSeconds,String prefix) {
        super(expireSeconds, prefix);
    }
    
    public static OrderKey getSeckillOrderByUidGid = new OrderKey(180,"seckillOrderUnique");
}

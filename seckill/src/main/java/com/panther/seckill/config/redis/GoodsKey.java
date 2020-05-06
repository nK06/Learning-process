package com.panther.seckill.config.redis;

/**
 * @version 1.0
 * @ClassName UserKey
 * @Description TODO
 * @date 2019-05-29 15:25
 */
public class GoodsKey extends BasePrefix{
    
    private static final int GOODSLIST_EXPIRE = 60;
    
    private GoodsKey(int expireSeconds,String prefix) {
        super(expireSeconds, prefix);
    }
    
    public static GoodsKey getGoodsList = new GoodsKey(GOODSLIST_EXPIRE,"goodsList");
    
    public static GoodsKey getGoodsDetail = new GoodsKey(GOODSLIST_EXPIRE,"goodsDetail");
    
    public static GoodsKey seckillGoodsStock = new GoodsKey(0,"goodsStock");
    
}

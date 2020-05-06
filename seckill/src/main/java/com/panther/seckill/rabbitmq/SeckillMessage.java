package com.panther.seckill.rabbitmq;

import com.panther.seckill.model.User;

/**
 * @version 1.0
 * @ClassName SeckillMessage
 * @Description TODO
 * @date 2019-08-27 17:12
 */
public class SeckillMessage {
    
    private User user;
    private long goodsId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}

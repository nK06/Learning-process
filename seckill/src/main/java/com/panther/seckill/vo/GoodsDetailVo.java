package com.panther.seckill.vo;

import com.panther.seckill.model.User;

/**
 * @version 1.0
 * @ClassName GoodsDetailVo
 * @Description TODO
 * @date 2019-08-26 13:36
 */
public class GoodsDetailVo {
    
    private int seckillStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goodsVo;
    private User user;

    public int getSeckillStatus() {
        return seckillStatus;
    }

    public void setSeckillStatus(int seckillStatus) {
        this.seckillStatus = seckillStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

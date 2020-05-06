package com.panther.seckill.vo;

import com.panther.seckill.model.OrderInfo;

/**
 * @version 1.0
 * @ClassName OrderDetailVo
 * @Description TODO
 * @date 2019-08-27 9:22
 */
public class OrderDetailVo {
    private GoodsVo goodsVo;
    private OrderInfo orderInfo;

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
}

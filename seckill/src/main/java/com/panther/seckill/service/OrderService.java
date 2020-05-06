package com.panther.seckill.service;

import com.panther.seckill.config.redis.OrderKey;
import com.panther.seckill.config.redis.RedisService;
import com.panther.seckill.dao.GoodsDao;
import com.panther.seckill.dao.OrderDao;
import com.panther.seckill.model.OrderInfo;
import com.panther.seckill.model.SeckillOrder;
import com.panther.seckill.model.User;
import com.panther.seckill.vo.GoodsVo;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @version 1.0
 * @ClassName OrderService
 * @Description TODO
 * @date 2019-07-25 11:08
 */
@Service
public class OrderService {
    
    @Autowired
    OrderDao orderDao;
    
    @Autowired
    RedisService redisService;

    public SeckillOrder getSeckillOrderByUserIdGoodsId(long userId, long goodsId) {
        //return orderDao.getSeckillOrderByUserIdGoodsId(userId, goodsId);
        return redisService.get(OrderKey.getSeckillOrderByUidGid,userId+"_"+goodsId,SeckillOrder.class);
    }
    
    
    @Transactional
    public OrderInfo creatOrder(User user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsPrice(goods.getSeckillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderDao.insert(orderInfo);
        
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setOrderId(orderInfo.getId());
        seckillOrder.setUserId(user.getId());
        seckillOrder.setGoodsId(goods.getId());
        orderDao.insertSeckillOrder(seckillOrder);

        redisService.set(OrderKey.getSeckillOrderByUidGid,user.getId()+"_"+goods.getId(),seckillOrder);
        return orderInfo;
    }

    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }
}

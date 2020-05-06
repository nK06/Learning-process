package com.panther.seckill.controller;

import com.panther.seckill.common.CodeMsg;
import com.panther.seckill.common.Result;
import com.panther.seckill.config.redis.RedisService;
import com.panther.seckill.model.OrderInfo;
import com.panther.seckill.model.User;
import com.panther.seckill.service.GoodsService;
import com.panther.seckill.service.OrderService;
import com.panther.seckill.vo.GoodsVo;
import com.panther.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @version 1.0
 * @ClassName GoodsController
 * @Description TODO
 * @date 2019-07-24 10:20
 */
@RequestMapping("order")
@Controller
public class OrderController {

    @Autowired
    GoodsService goodsService;
    
    @Autowired
    RedisService redisService;
    
    @Autowired
    OrderService orderService;
    
    @RequestMapping(value = "detail")
    @ResponseBody
    public Result<OrderDetailVo> orderDetail(@RequestParam long orderId, User user){
        if(user == null){
            Result.error(CodeMsg.USER_NOT_EXIST);
        }
        OrderInfo orderInfo = orderService.getOrderById(orderId);
        if(orderInfo == null){
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = orderInfo.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setGoodsVo(goods);
        orderDetailVo.setOrderInfo(orderInfo);
        return Result.success(orderDetailVo);
    }
}

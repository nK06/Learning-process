package com.panther.seckill.rabbitmq;

import com.panther.seckill.common.CodeMsg;
import com.panther.seckill.common.Result;
import com.panther.seckill.config.redis.RedisService;
import com.panther.seckill.model.OrderInfo;
import com.panther.seckill.model.SeckillOrder;
import com.panther.seckill.model.User;
import com.panther.seckill.service.GoodsService;
import com.panther.seckill.service.OrderService;
import com.panther.seckill.service.SeckillService;
import com.panther.seckill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownServiceException;

/**
 * @version 1.0
 * @ClassName MQSender
 * @Description TODO
 * @date 2019-08-27 14:01
 */
@Service
public class MQReceiver {
    
    @Autowired
    AmqpTemplate amqpTemplate;
    
    @Autowired
    SeckillService seckillService;
    
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;
    
    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @RabbitListener(queues = MQConfig.SECKILL_QUEUE_NAME)
    public void seckill(String message){
        log.info("receive messgae: "+message);
        SeckillMessage seckillMessage = RedisService.stringToBean(message, SeckillMessage.class);
        User user = seckillMessage.getUser();
        long goodsId = seckillMessage.getGoodsId();
        //判断商品库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getGoodsStock();
        if(stock <= 0){
            return;
        }
        //判断是否已经秒杀成功
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order != null){
            return;
        }
        //下单
        OrderInfo orderInfo = seckillService.seckill(user, goods);
    }
    
    //@RabbitListener(queues = MQConfig.SECKILL_QUEUE_NAME)
    //public void receiver(String message){
    //    log.info("receive messgae: "+message);
    //}
    //
    //@RabbitListener(queues = MQConfig.TOPIC_QUEUE_NAME_1)
    //public void receiverTopic1(String message){
    //    log.info("topic queue 1 receive messgae: "+message);
    //}
    //
    //@RabbitListener(queues = MQConfig.TOPIC_QUEUE_NAME_2)
    //public void receiverTopic2(String message){
    //    log.info("topic queue 2 receive messgae: "+message);
    //}
    //
    //@RabbitListener(queues = MQConfig.HEADERS_QUEUE)
    //public void receiverTopic2(byte[] message){
    //    log.info("headers queue receive messgae: "+new String(message));
    //}

}

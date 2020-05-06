package com.panther.seckill.rabbitmq;

import com.panther.seckill.config.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @ClassName MQSender
 * @Description TODO
 * @date 2019-08-27 14:01
 */
@Service
public class MQSender {

    private static Logger log = LoggerFactory.getLogger(MQSender.class);
    
    @Autowired
    AmqpTemplate amqpTemplate;

    //public void send(Object message){
    //    String msg = RedisService.beanToString(message);
    //    amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE_NAME,msg);
    //    log.info("send messgae: " + msg);
    //}
    //
    //public void sendTopic(Object message){
    //    String msg = RedisService.beanToString(message);
    //    amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE_NAME,"topic.key",msg+"1");
    //    amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE_NAME,"topic.kkk",msg+"2");
    //    
    //}
    //
    //public void sendFanout(Object message){
    //    String msg = RedisService.beanToString(message);
    //    amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE_NAME,"",msg+"fanout");
    //}
    //
    //public void sendHeaders(Object message){
    //    String msg = RedisService.beanToString(message);
    //    MessageProperties messageProperties = new MessageProperties();
    //    messageProperties.setHeader("header1","value1");
    //    messageProperties.setHeader("header2","value2");
    //    Message obj = new Message(msg.getBytes(),messageProperties);
    //    amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE_NAME,"",obj);
    //}

    public void sendSeckillMessage(SeckillMessage seckillMessage) {
        String msg = RedisService.beanToString(seckillMessage);
        log.info("send Message: "+msg);
        amqpTemplate.convertAndSend(MQConfig.SECKILL_QUEUE_NAME,msg);
    }
}

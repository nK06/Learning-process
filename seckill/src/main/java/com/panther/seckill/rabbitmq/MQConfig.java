package com.panther.seckill.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @version 1.0
 * @ClassName MQConfig
 * @Description TODO
 * @date 2019-08-27 14:01
 */
@Configuration
public class MQConfig {
    
    public static final String SECKILL_QUEUE_NAME = "seckillQueue";
    public static final String TOPIC_QUEUE_NAME_1 = "testTopicQueue1";
    public static final String TOPIC_QUEUE_NAME_2 = "testTopicQueue2";
    public static final String HEADERS_QUEUE = "headersQueue";
    public static final String TOPIC_EXCHANGE_NAME = "topicExchange";
    public static final String FANOUT_EXCHANGE_NAME = "fanoutExchange";
    public static final String HEADERS_EXCHANGE_NAME = "headersExchange";
    
    public static final String ROUTING_KEY_1 = "topic.key1";
    public static final String ROUTING_KEY_2 = "topic.#";//*代表一个单词，#代表0个或多个单词

    /**
     * Direct 模式 
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(SECKILL_QUEUE_NAME,true);
    }

    /**
     * Topic模式
     */
    @Bean
    public Queue testTopicQueue1(){
        return new Queue(TOPIC_QUEUE_NAME_1,true);
    }
    
    @Bean
    public Queue testTopicQueue2(){
        return new Queue(TOPIC_QUEUE_NAME_2,true);
    }
    
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }
    
    @Bean
    public Binding topicBingding1(){
        return BindingBuilder.bind(testTopicQueue1()).to(topicExchange()).with("topic.key");
    }

    @Bean
    public Binding topicBingding2(){
        return BindingBuilder.bind(testTopicQueue2()).to(topicExchange()).with("topic.#");
    }

    /**
     * Fanout模式 （广播）
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
    }
    
    @Bean
    public Binding fanoutBinding1(){
        return BindingBuilder.bind(testTopicQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2(){
        return BindingBuilder.bind(testTopicQueue2()).to(fanoutExchange());
    }

    /**
     * Header模式
     * 
     */
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(HEADERS_EXCHANGE_NAME);
    }

    @Bean
    public Queue headersQueue(){
        return new Queue(HEADERS_QUEUE,true);
    }

    @Bean
    public Binding headersBinding(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("header1","value1");
        map.put("header2","value2");
        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(map).match();
    }
}
























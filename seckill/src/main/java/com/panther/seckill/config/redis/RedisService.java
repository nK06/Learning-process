package com.panther.seckill.config.redis;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @version 1.0
 * @ClassName RedisService
 * @Description TODO
 * @date 2019-05-29 13:53
 */
@Service
public class RedisService {
    
    @Autowired
    @Lazy
    private JedisPool jedisPool;
    
    @Autowired
    private RedisConfig redisConfig;

    /**
     * 获取单个对象
     * @param keyPrefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix keyPrefix ,String key, Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 生成存入Redis的KEY
            String realKey = keyPrefix.getPrefix() + key;
            String s = jedis.get(realKey);
            T t = stringToBean(s, clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }
    
    public <T> boolean set(KeyPrefix keyPrefix ,String key, T value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 生成存入Redis的KEY
            String realKey = keyPrefix.getPrefix() + key;
            String s = beanToString(value);
            int seconds = keyPrefix.expireSeconds();
            if(seconds >0 ){
                jedis.setex(realKey,seconds,s);
            }else {
                jedis.set(realKey,s);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }
    
    public <T> boolean exists(KeyPrefix keyPrefix ,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 生成存入Redis的KEY
            String realKey = keyPrefix.getPrefix() + key;
            return jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 删除一个key
     * @param keyPrefix
     * @param key
     * @param <T>
     * @return
     */

    public <T> boolean delete(KeyPrefix keyPrefix ,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 生成存入Redis的KEY
            String realKey = keyPrefix.getPrefix() + key;
            long ret = jedis.del(realKey);
            return ret > 0;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     * @param keyPrefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long incr(KeyPrefix keyPrefix ,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 生成存入Redis的KEY
            String realKey = keyPrefix.getPrefix() + key;
            return jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     * @param keyPrefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long decr(KeyPrefix keyPrefix ,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            // 生成存入Redis的KEY
            String realKey = keyPrefix.getPrefix() + key;
            return jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    public static <T> String beanToString(T value) {
        if(value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class){
            return ""+value;
        }else if(clazz == String.class){
            return (String) value;
        }else if(clazz == long.class || clazz == Long.class){
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T stringToBean(String s, Class<T> clazz) {
        if(StringUtils.isEmpty(s)|| clazz == null){
            return null;
        }
        if(clazz == int.class || clazz == Integer.class){
            return (T)Integer.valueOf(s);
        }else if(clazz == String.class){
            return (T)s;
        }else if(clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(s);
        }else {
            return JSON.toJavaObject(JSON.parseObject(s),clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if(jedis!=null){
            jedis.close();
        }
    }

    @Bean
    public JedisPool JedisPoolFactory(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait()*1000);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(),
                redisConfig.getPort(),redisConfig.getTimeout()*1000,redisConfig.getPassword());
        return jedisPool;
    }
}

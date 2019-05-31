package com.panther.seckill.config.redis;

public interface KeyPrefix {
    
    public int expireSeconds();
    
    public String getPrefix();
}

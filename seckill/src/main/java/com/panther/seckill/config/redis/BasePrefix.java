package com.panther.seckill.config.redis;

/**
 * @version 1.0
 * @ClassName BasePrefix
 * @Description TODO
 * @date 2019-05-29 15:21
 */
public abstract class BasePrefix implements KeyPrefix{
    
    private int expireSeconds;
    
    private String prefix;

    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    /**
     * 默认0代表永不过期
     */
    @Override
    public int expireSeconds() { 
        return expireSeconds;
    }
    
    /**
     * 
     */
    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}

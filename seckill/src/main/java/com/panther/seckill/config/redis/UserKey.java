package com.panther.seckill.config.redis;

import com.panther.seckill.model.User;

/**
 * @version 1.0
 * @ClassName UserKey
 * @Description TODO
 * @date 2019-05-29 15:25
 */
public class UserKey extends BasePrefix{

    private UserKey(String prefix) {
        super(prefix);
    }
    
    public static UserKey getById = new UserKey("id");
    
}

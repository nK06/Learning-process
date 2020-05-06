package com.panther.seckill.config.mvc;

import com.panther.seckill.model.User;

/**
 * @version 1.0
 * @ClassName UserContext
 * @Description TODO
 * @date 2019-08-29 13:38
 */
public class UserContext {
    
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<User>();
    
    public static void setUser(User user){
        userThreadLocal.set(user);
    }

    public static User getUser(){
        return userThreadLocal.get();
    }
}

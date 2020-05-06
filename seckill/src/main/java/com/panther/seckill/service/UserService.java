package com.panther.seckill.service;

import com.panther.seckill.common.CodeMsg;
import com.panther.seckill.config.redis.RedisService;
import com.panther.seckill.config.redis.SeckillUserKey;
import com.panther.seckill.config.redis.UserKey;
import com.panther.seckill.dao.UserDao;
import com.panther.seckill.exception.GlobalException;
import com.panther.seckill.model.User;
import com.panther.seckill.util.MD5Util;
import com.panther.seckill.util.UUIDUtil;
import com.panther.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0
 * @ClassName UserService
 * @Description TODO
 * @date 2019-05-29 10:00
 */
@Service
public class UserService {

    public static final String COOKIE_NAME_TOKEN = "token";
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private RedisService redisService;
    
    public User getById(long id){
        //取缓存
        User user = redisService.get(SeckillUserKey.getById,id+"",User.class);
        if(user != null){
            return user;
        }
        //取数据库
        user = userDao.getById(id);
        if(user != null){
            redisService.set(SeckillUserKey.getById,id+"",user);
        }
        return user;
    }

    public boolean updatePassword(long id, String passsword,String token){
        //获取User
        User user = getById(id);
        if(user != null){
            User updateUser = new User();
            updateUser.setId(id);
            updateUser.setPassword(MD5Util.formPassToDBPass(passsword,user.getSalt()));
            userDao.update(updateUser);
            //刷新缓存
            redisService.delete(SeckillUserKey.getById,id+"");
            user.setPassword(updateUser.getPassword());
            redisService.set(SeckillUserKey.token,token,user);
            return true;
        }else {
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
    }
    
    public void login(HttpServletResponse response,LoginVo loginVo){
        if(loginVo == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        User user = getById(Long.parseLong(mobile));
        if(user == null){
            throw new GlobalException(CodeMsg.USER_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String salt = user.getSalt();

        String calcPass = MD5Util.formPassToDBPass(password, salt);
        if(!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response,user,token);
    }

    public User getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        User user = redisService.get(SeckillUserKey.token, token, User.class);
        if(user != null){
            //延长有效期
            addCookie(response,user,token);
        }
        return user;
    }
    
    private void addCookie(HttpServletResponse response, User user,String token){
        
        redisService.set(SeckillUserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(SeckillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}

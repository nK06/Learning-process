package com.panther.seckill.service;

import com.panther.seckill.common.CodeMsg;
import com.panther.seckill.dao.UserDao;
import com.panther.seckill.exception.GlobalException;
import com.panther.seckill.model.User;
import com.panther.seckill.util.MD5Util;
import com.panther.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @ClassName UserService
 * @Description TODO
 * @date 2019-05-29 10:00
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    
    public User getById(long id){
        return userDao.getById(id);
    }
    
    public void login(LoginVo loginVo){
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
    }
}

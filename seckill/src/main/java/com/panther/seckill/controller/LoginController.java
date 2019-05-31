package com.panther.seckill.controller;

import com.panther.seckill.common.CodeMsg;
import com.panther.seckill.common.Result;
import com.panther.seckill.service.UserService;
import com.panther.seckill.util.ValidatorUtil;
import com.panther.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @version 1.0
 * @ClassName LoginController
 * @Description TODO
 * @date 2019-05-30 16:27
 */
@Controller
public class LoginController {
    
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    
    @Autowired
    private UserService userService;
    
    @RequestMapping("/login")
    public String login(){
        return "/login.html";
    }
    
    @RequestMapping("/login/do_login")
    @ResponseBody
    public Result<Boolean> toLogin(@Valid LoginVo loginVo){
        //if(StringUtils.isEmpty(loginVo.getPassword())){
        //    return Result.error(CodeMsg.PASSWORD_EMPTY);
        //}
        //if(!ValidatorUtil.isMobile(loginVo.getMobile())){
        //    return Result.error(CodeMsg.MOBILE_ERROR);
        //}
        userService.login(loginVo);
        return Result.success(true);
    }
}

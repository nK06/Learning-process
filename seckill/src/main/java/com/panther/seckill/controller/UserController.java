package com.panther.seckill.controller;

import com.panther.seckill.common.Result;
import com.panther.seckill.model.User;
import com.panther.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;

/**
 * @version 1.0
 * @ClassName Controller
 * @Description TODO
 * @date 2019-05-28 14:43
 */
@Controller
@RequestMapping("/demo")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/user/{id}")
    @ResponseBody
    public Result<User> getUser(@PathVariable int id){
        User user = userService.getById(id);
        return Result.success(user);
    }
    
}

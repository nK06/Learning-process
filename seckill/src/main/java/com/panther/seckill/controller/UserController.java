package com.panther.seckill.controller;

import com.panther.seckill.common.Result;
import com.panther.seckill.model.User;
import com.panther.seckill.rabbitmq.MQSender;
import com.panther.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    
    @Autowired
    MQSender mqSender;
    
    @GetMapping("/user/{id}")
    @ResponseBody
    public Result<User> getUser(@PathVariable int id){
        User user = userService.getById(id);
        return Result.success(user);
    }
    
    @RequestMapping("/info")
    @ResponseBody
    public Result<User> getUserInfo(Model model, User user){
        return Result.success(user);
    }

    //@RequestMapping("/mq")
    //@ResponseBody
    //public void testMQ(User user){
    //    mqSender.send(user);
    //}
    //
    //@RequestMapping("/testMqTopic")
    //@ResponseBody
    //public void testMQTopic(){
    //    mqSender.sendTopic("============");
    //}
    //
    //@RequestMapping("/testMqFanout")
    //@ResponseBody
    //public void testMqFanout(){
    //    mqSender.sendFanout("QQQQQQQQQ");
    //}
    //
    //@RequestMapping("/testMqHeaders")
    //@ResponseBody
    //public void testMqHeaders(){
    //    mqSender.sendHeaders("TTTTTTTTTT");
    //}
    
}

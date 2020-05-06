package com.panther.seckill.controller;

import com.panther.seckill.common.CodeMsg;
import com.panther.seckill.common.Result;
import com.panther.seckill.config.mvc.AccessLimit;
import com.panther.seckill.config.redis.AccessKey;
import com.panther.seckill.config.redis.GoodsKey;
import com.panther.seckill.config.redis.RedisService;
import com.panther.seckill.model.SeckillOrder;
import com.panther.seckill.model.User;
import com.panther.seckill.rabbitmq.MQSender;
import com.panther.seckill.rabbitmq.SeckillMessage;
import com.panther.seckill.service.GoodsService;
import com.panther.seckill.service.OrderService;
import com.panther.seckill.service.SeckillService;
import com.panther.seckill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @ClassName SeckillController
 * @Description TODO
 * @date 2019-07-25 10:58
 */
@RequestMapping("seckill")
@Controller
public class SeckillController implements InitializingBean {
    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;
    
    @Autowired
    OrderService orderService;
    
    @Autowired
    SeckillService seckillService;
    
    @Autowired
    MQSender mqSender;
    
    private Map<Long,Boolean> localOverMap = new HashMap<Long,Boolean>();

    /**
     * 系统初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsVo = goodsService.listGoodsVo();
        if(goodsVo == null){
            return;
        }
        for(GoodsVo goods : goodsVo){
            redisService.set(GoodsKey.seckillGoodsStock,""+goods.getId(),goods.getStockCount());
            localOverMap.put(goods.getId(),false);
        }
    }

    @RequestMapping(value = "/{path}/do_seckill", method = RequestMethod.POST)
    @ResponseBody
    public Result seckill(User user, @RequestParam long goodsId, @PathVariable("path") String path){
        if(user == null){
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }
        //验证path
        boolean checkpath = seckillService.checkPath(path,user,goodsId);
        if(!checkpath){
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        
        //内存标记
        boolean end = localOverMap.get(goodsId);
        if(end){
            return Result.error(CodeMsg.SECKILL_FAIL);
        }
        //预减库存
        long stock = redisService.decr(GoodsKey.seckillGoodsStock,""+goodsId);
        if(stock < 0){
            localOverMap.put(goodsId,true);
            return Result.error(CodeMsg.SECKILL_FAIL);
        }
        //判断是否已经秒杀成功
        SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order != null){
            return Result.error(CodeMsg.SECKILL_REPEATE);
        }
        //入队
        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setUser(user);
        seckillMessage.setGoodsId(goodsId);
        mqSender.sendSeckillMessage(seckillMessage);
        return Result.success(0);//排队中
    }


    /**
     * 0 排队中
     * -1 秒杀失败
     * orderId 秒杀成功
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> result(User user, @RequestParam long goodsId){
        if(user == null){
            Result.error(CodeMsg.USER_NOT_EXIST);
        }
        //判断是否已经秒杀成功
        long result = seckillService.getSeckillResult(user.getId(),goodsId);
        return Result.success(result);
    }
    
    @AccessLimit(seconds=5,maxCount=5,needLogin=true)
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getPath(HttpServletRequest request,User user, @RequestParam("goodsId") long goodsId, @RequestParam(value = "verifyCode", defaultValue = "0") int verifyCode){
        if(user == null){
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }
        boolean check = seckillService.checkVerifyCode(user,goodsId,verifyCode);
        if(!check){
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        String str = seckillService.creatPath(user,goodsId);
        return Result.success(str);
    }

    @RequestMapping(value = "verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public void getVerifyCode(HttpServletResponse response, User user, @RequestParam long goodsId){
        if(user == null){
            return;
        }
        BufferedImage image = seckillService.creatVerifyImg(user,goodsId);
        try{
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"PNG",outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    //@RequestMapping(value = "do_seckill", method = RequestMethod.POST)
    //@ResponseBody
    //public Result<OrderInfo> toGoodsList(User user, @RequestParam long goodsId){
    //    if(user == null){
    //        Result.error(CodeMsg.USER_NOT_EXIST);
    //    }
    //    //判断商品库存
    //    GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    //    int stock = goods.getGoodsStock();
    //    if(stock <= 0){
    //        return Result.error(CodeMsg.SECKILL_FAIL);
    //    }
    //    //判断是否已经秒杀成功
    //    SeckillOrder order = orderService.getSeckillOrderByUserIdGoodsId(user.getId(),goodsId);
    //    if(order != null){
    //        return Result.error(CodeMsg.SECKILL_FAIL);
    //    }
    //    //减库存下单
    //    OrderInfo orderInfo = seckillService.seckill(user, goods);
    //    return Result.success(orderInfo);
    //}

    
}

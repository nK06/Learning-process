package com.panther.seckill.controller;

import com.panther.seckill.common.Result;
import com.panther.seckill.config.redis.GoodsKey;
import com.panther.seckill.config.redis.RedisService;
import com.panther.seckill.model.Goods;
import com.panther.seckill.model.User;
import com.panther.seckill.service.GoodsService;
import com.panther.seckill.service.UserService;
import com.panther.seckill.vo.GoodsDetailVo;
import com.panther.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.adapter.DefaultServerWebExchange;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxExpressionContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxThymeleafRequestContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @version 1.0
 * @ClassName GoodsController
 * @Description TODO
 * @date 2019-07-24 10:20
 */
@RequestMapping("goods")
@Controller
public class GoodsController {

    @Autowired
    GoodsService goodsService;
    
    @Autowired
    RedisService redisService;
    
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;   
    
    @RequestMapping(value = "goods_list", produces = "text/html")
    @ResponseBody
    public String toGoodsList(HttpServletRequest request,HttpServletResponse response,Model model,User user){
        model.addAttribute("user",user);
        //获取缓存数据
        String html = redisService.get(GoodsKey.getGoodsList,"",String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }else {
            //手动渲染页面
            //查询商品
            List<GoodsVo> goodsVos = goodsService.listGoodsVo();
            model.addAttribute("goodsList",goodsVos);
            
            IWebContext iWebContext = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
            html = thymeleafViewResolver.getTemplateEngine().process("goods_list",iWebContext);
            if(!StringUtils.isEmpty(html)){
                redisService.set(GoodsKey.getGoodsList,"",html);
            }
            return html;
        }
    }
    
    @RequestMapping(value = "to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String toGoodsDetail(HttpServletRequest request,HttpServletResponse response,Model model, User user, @PathVariable(name = "goodsId")long goodsId){
        model.addAttribute("user",user);

        //获取缓存数据
        String html = redisService.get(GoodsKey.getGoodsDetail,goodsId+"",String.class);
        if(!StringUtils.isEmpty(html)) {
            return html;
        }
        //查询商品
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);
        
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int seckillStatus = 0;
        int remainSeconds = 0;
        if(now < startAt){ //秒杀未开始
            seckillStatus = 0;
            remainSeconds = (int) (startAt - now)/1000;
        }else if(now > endAt){ // 秒杀结束
            seckillStatus = 2;
            remainSeconds = -1;
        }else {
            seckillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("seckillStatus",seckillStatus);
        model.addAttribute("remainSeconds",remainSeconds);
        //手动渲染页面
        IWebContext iWebContext = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail",iWebContext);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail,goodsId+"",html);
        }
        return html;
    }

    @RequestMapping(value = "to_detail_new/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> toGoodsDetailNew(HttpServletRequest request, HttpServletResponse response, Model model, User user, @PathVariable(name = "goodsId")long goodsId){
        //查询商品
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int seckillStatus = 0;
        int remainSeconds = 0;
        if(now < startAt){ //秒杀未开始
            seckillStatus = 0;
            remainSeconds = (int) (startAt - now)/1000;
        }else if(now > endAt){ // 秒杀结束
            seckillStatus = 2;
            remainSeconds = -1;
        }else {
            seckillStatus = 1;
            remainSeconds = 0;
        }

        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoodsVo(goods);
        goodsDetailVo.setUser(user);
        goodsDetailVo.setRemainSeconds(remainSeconds);
        goodsDetailVo.setSeckillStatus(seckillStatus);
        
        return Result.success(goodsDetailVo);
    }
}

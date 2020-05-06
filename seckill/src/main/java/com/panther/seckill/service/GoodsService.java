package com.panther.seckill.service;

import com.panther.seckill.dao.GoodsDao;
import com.panther.seckill.model.Goods;
import com.panther.seckill.model.SeckillGoods;
import com.panther.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @ClassName GoodsService
 * @Description TODO
 * @date 2019-07-24 15:28
 */
@Service
public class GoodsService {
    
    @Autowired
    GoodsDao goodsDao;
    
    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goods) {
        SeckillGoods g = new SeckillGoods();
        g.setGoodsId(goods.getId());
        return goodsDao.reduceStock(g) > 0;
    }
}

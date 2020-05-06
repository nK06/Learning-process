package com.panther.seckill.dao;

import com.panther.seckill.model.Goods;
import com.panther.seckill.model.SeckillGoods;
import com.panther.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {
    
    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price as seckillPrice from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price as seckillPrice from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{id}")
    public GoodsVo getGoodsVoByGoodsId(@Param("id") long goodsId);

    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(SeckillGoods seckillGoods);
}

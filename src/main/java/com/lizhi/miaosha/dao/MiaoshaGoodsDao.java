package com.lizhi.miaosha.dao;

import com.lizhi.miaosha.domain.MiaoshaGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 秒杀商品
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Mapper
public interface MiaoshaGoodsDao {

    /**
     * 查询秒杀商品列表
     * @return
     */
    @Select("select * from miaosha_goods where deleted = 0")
    List<MiaoshaGoods> queryList();

    /**
     * 根据商品id查询秒杀商品
     * @param goodsId
     * @return
     */
    @Select("select * from miaosha_goods where goods_id = #{goodsId} and deleted = 0")
    MiaoshaGoods queryById(@Param("goodsId") Long goodsId);


}

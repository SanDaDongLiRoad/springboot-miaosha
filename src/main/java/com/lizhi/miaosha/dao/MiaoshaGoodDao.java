package com.lizhi.miaosha.dao;

import com.lizhi.miaosha.domain.MiaoshaGood;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 秒杀商品
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Mapper
public interface MiaoshaGoodDao {

    /**
     * 查询秒杀商品列表
     * @return
     */
    @Select("select * from miaosha_good where deleted = 0")
    List<MiaoshaGood> queryList();

    /**
     * 根据商品id查询秒杀商品
     * @param goodsId
     * @return
     */
    @Select("select * from miaosha_good where goods_id = #{goodsId} and deleted = 0")
    MiaoshaGood queryById(@Param("goodsId") Long goodsId);

    /**
     * 减商品库存
     * @param goodsId
     * @return
     */
    @Update("update miaosha_good set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(@Param("goodsId") Long goodsId);

}

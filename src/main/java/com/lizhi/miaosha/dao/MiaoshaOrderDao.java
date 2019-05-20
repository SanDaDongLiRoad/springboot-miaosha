package com.lizhi.miaosha.dao;

import com.lizhi.miaosha.domain.MiaoshaOrder;
import org.apache.ibatis.annotations.*;

/**
 * 秒杀订单
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/20
 */
@Mapper
public interface MiaoshaOrderDao {

    /**
     * 根据用户id和商品id查询秒杀订单
     * @param userId
     * @param goodsId
     * @return
     */
    @Select("select * from miaosha_order where user_id = #{userId} and goods_id = #{goodsId} and deleted = 0")
    MiaoshaOrder queryByUserIdAndGoodsId(@Param("userId") Long userId,@Param("goodsId") Long goodsId);


    /**
     * 保存秒杀订单
     * @param miaoshaOrder
     * @return
     */
    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    Long insert(MiaoshaOrder miaoshaOrder);
}

package com.lizhi.miaosha.dao;

import com.lizhi.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * 订单
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/20
 */
@Mapper
public interface OrderInfoDao {

    /**
     * 保存订单
     * @param orderInfo
     * @return
     */
    @Insert("insert into order_info(user_id, goods_id, delivery_addr_id,goods_name, goods_count, goods_price, order_channel, order_status)values("
            + "#{userId}, #{goodsId},  #{deliveryAddrId},#{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{orderStatus})")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    Long insert(OrderInfo orderInfo);

    /**
     * 根据订单id查询订单
     * @param orderId
     * @return
     */
    @Select("select * from order_info where id = #{orderId} and deleted = 0")
    OrderInfo queryById(@Param("orderId") Long orderId);
}

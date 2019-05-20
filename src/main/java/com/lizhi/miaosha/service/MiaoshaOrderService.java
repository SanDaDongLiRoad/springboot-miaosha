package com.lizhi.miaosha.service;

import com.lizhi.miaosha.domain.MiaoshaOrder;

/**
 * 秒杀订单业务类
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/20
 */
public interface MiaoshaOrderService {

    /**
     * 根据用户id和商品id查询秒杀订单
     * @param userId
     * @param goodsId
     * @return
     */
    MiaoshaOrder queryByUserIdAndGoodsId(Long userId, Long goodsId);

    /**
     * 保存秒杀订单
     * @param miaoshaOrder
     * @return
     */
    Long saveMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}

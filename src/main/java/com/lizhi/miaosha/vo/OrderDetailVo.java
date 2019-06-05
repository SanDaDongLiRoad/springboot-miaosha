package com.lizhi.miaosha.vo;

import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.domain.OrderInfo;
import lombok.Data;

/**
 * 订单详情
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/6/4
 */
@Data
public class OrderDetailVo {

    /**
     * 订单信息
     */
   private OrderInfo orderInfo;

    /**
     * 秒杀商品详情
     */
    private MiaoshaGoodsVO miaoshaGoodsVO;

    /**
     * 秒杀用户信息
     */
    private MiaoshaUser miaoshaUser;
}

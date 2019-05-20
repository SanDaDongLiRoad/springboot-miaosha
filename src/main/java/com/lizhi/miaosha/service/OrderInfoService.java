package com.lizhi.miaosha.service;

import com.lizhi.miaosha.domain.OrderInfo;

/**
 * 订单
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/20
 */
public interface OrderInfoService {

    /**
     * 保存订单
     * @return
     */
    Long saveOrderInfo(OrderInfo orderInfo);

    /**
     * 根据订单id查询订单
     * @param orderId
     * @return
     */
    OrderInfo queryOrderInfoById(Long orderId);

}

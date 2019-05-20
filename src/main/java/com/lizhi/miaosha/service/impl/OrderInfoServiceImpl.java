package com.lizhi.miaosha.service.impl;

import com.lizhi.miaosha.dao.OrderInfoDao;
import com.lizhi.miaosha.domain.OrderInfo;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 订单业务类
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/20
 */
@Slf4j
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoDao orderInfoDao;

    @Transactional
    @Override
    public Long saveOrderInfo(OrderInfo orderInfo) {
        log.info("saveOrderInfo requestParam orderInfo is: {}",orderInfo);
        Long orderId = orderInfoDao.insert(orderInfo);
        log.info("saveOrderInfo result orderId is: {}",orderId);
        return orderId;
    }

    @Override
    public OrderInfo queryOrderInfoById(Long orderId) {
        log.info("queryOrderInfoById requestParam orderId is: {}",orderId);
        if(Objects.equals(null,orderId)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("订单orderId为空"));
        }
        OrderInfo orderInfo = orderInfoDao.queryById(orderId);
        log.info("queryOrderInfoById result orderInfo is: {}",orderInfo);
        return orderInfo;
    }
}

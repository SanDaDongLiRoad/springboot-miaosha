package com.lizhi.miaosha.service.impl;

import com.lizhi.miaosha.dao.MiaoshaOrderDao;
import com.lizhi.miaosha.domain.MiaoshaOrder;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.service.MiaoshaOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 秒杀订单业务类
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/20
 */
@Slf4j
@Service
public class MiaoshaOrderServiceImpl implements MiaoshaOrderService {

    @Autowired
    private MiaoshaOrderDao miaoshaOrderDao;

    @Override
    public MiaoshaOrder queryByUserIdAndGoodsId(Long userId, Long goodsId) {
        log.info("queryByUserIdAndGoodsId requestParam userId is: {},goodsId is {}",userId,goodsId);
        if(Objects.equals(null,userId)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("用户id为空"));
        }
        if(Objects.equals(null,goodsId)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("商品goodsId为空"));
        }
        MiaoshaOrder miaoshaOrder = miaoshaOrderDao.queryByUserIdAndGoodsId(userId,goodsId);
        log.info("queryByUserIdAndGoodsId result miaoshaOrder is: {}",miaoshaOrder);
        return miaoshaOrder;
    }

    @Transactional
    @Override
    public Long saveMiaoshaOrder(MiaoshaOrder miaoshaOrder) {
        log.info("saveMiaoshaOrder requestParam miaoshaOrder is: {}",miaoshaOrder);
        Long miaoshaOrderId = miaoshaOrderDao.insert(miaoshaOrder);
        log.info("saveMiaoshaOrder result miaoshaOrderId is: {}",miaoshaOrderId);
        return miaoshaOrderId;
    }
}

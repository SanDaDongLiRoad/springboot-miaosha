package com.lizhi.miaosha.service.impl;

import com.lizhi.miaosha.domain.MiaoshaOrder;
import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.domain.OrderInfo;
import com.lizhi.miaosha.redis.JedisService;
import com.lizhi.miaosha.redis.MiaoshaKey;
import com.lizhi.miaosha.redis.OrderKey;
import com.lizhi.miaosha.service.MiaoshaGoodsService;
import com.lizhi.miaosha.service.MiaoshaOrderService;
import com.lizhi.miaosha.service.MiaoshaService;
import com.lizhi.miaosha.service.OrderInfoService;
import com.lizhi.miaosha.util.MD5Util;
import com.lizhi.miaosha.util.UUIDUtil;
import com.lizhi.miaosha.vo.MiaoshaGoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * 秒杀业务类
 *
 * @author xulizhi-lenovo
 * @date 2019/5/20
 */
@Slf4j
@Service
public class MiaoshaServiceImpl implements MiaoshaService {

    @Autowired
    private JedisService jedisService;

    @Autowired
    private MiaoshaGoodsService miaoshaGoodsService;

    @Autowired
    private MiaoshaOrderService miaoshaOrderService;

    @Autowired
    private OrderInfoService orderService;

    @Transactional
    @Override
    public Long miaosha(MiaoshaUser miaoshaUser, MiaoshaGoodsVO miaoshaGoodsVO) {
        log.info("miaosha requestParam miaoshaUser is: {},miaoshaGoodsVO is {}",miaoshaUser,miaoshaGoodsVO);

        //开始秒杀
        //1.减库存
        boolean reduceStockFlag = miaoshaGoodsService.reduceStock(miaoshaGoodsVO.getGoodsId());

        //2.生成订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(miaoshaUser.getId());
        orderInfo.setGoodsId(miaoshaGoodsVO.getGoodsId());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsName(miaoshaGoodsVO.getGoodsName());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(miaoshaGoodsVO.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setOrderStatus(0);
        orderInfo.setCreateDate(new Date());
        orderService.saveOrderInfo(orderInfo);

        //3.生成秒杀订单
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(miaoshaGoodsVO.getGoodsId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(miaoshaUser.getId());
        miaoshaOrderService.saveMiaoshaOrder(miaoshaOrder);

        jedisService.set(OrderKey.getMiaoshaOrderByUidGid, ""+miaoshaUser.getId()+"_"+miaoshaGoodsVO.getGoodsId(), miaoshaOrder);
        return orderInfo.getId();
    }

    @Override
    public String createMiaoshaPath(MiaoshaUser miaoshaUser, long goodsId) {
        if(miaoshaUser == null || goodsId <=0) {
            return null;
        }
        String miaoshaPath = MD5Util.md5(UUIDUtil.uuid()+"123456");
        jedisService.set(MiaoshaKey.getMiaoshaPath, ""+miaoshaUser.getId() + "_"+ goodsId,miaoshaPath);
        return miaoshaPath;
    }
}

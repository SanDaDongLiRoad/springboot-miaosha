package com.lizhi.miaosha.service.impl;

import com.lizhi.miaosha.domain.MiaoshaOrder;
import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.domain.OrderInfo;
import com.lizhi.miaosha.dto.VerifyCodeDTO;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.redis.JedisService;
import com.lizhi.miaosha.redis.MiaoshaKey;
import com.lizhi.miaosha.redis.OrderKey;
import com.lizhi.miaosha.service.MiaoshaGoodsService;
import com.lizhi.miaosha.service.MiaoshaOrderService;
import com.lizhi.miaosha.service.MiaoshaService;
import com.lizhi.miaosha.service.OrderInfoService;
import com.lizhi.miaosha.util.MD5Util;
import com.lizhi.miaosha.util.ResultUtil;
import com.lizhi.miaosha.util.UUIDUtil;
import com.lizhi.miaosha.util.VerifyCodeUtil;
import com.lizhi.miaosha.vo.MiaoshaGoodsVO;
import com.lizhi.miaosha.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Objects;
import java.util.Random;


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

        if(!reduceStockFlag){
            jedisService.set(MiaoshaKey.isGoodsOver, ""+miaoshaGoodsVO.getGoodsId(), true);
            return null;
        }

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
    public String createMiaoshaPath(Long miaoshaUserId, Long goodsId) {

        if(Objects.equals(null,miaoshaUserId) || Objects.equals(null,goodsId)) {
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("商品id或用户id为空"));
        }
        String miaoshaPath = MD5Util.md5(UUIDUtil.uuid()+"123456");
        jedisService.set(MiaoshaKey.getMiaoshaPath, ""+miaoshaUserId + "_"+ goodsId,miaoshaPath);
        return miaoshaPath;
    }

    @Override
    public BufferedImage createVerifyCode(MiaoshaUser miaoshaUser, long goodsId) {

        VerifyCodeDTO verifyCodeDTO = VerifyCodeUtil.createVerifyCode();

        //保存验证码结果到Redis
        jedisService.set(MiaoshaKey.getMiaoshaVerifyCode, miaoshaUser.getId()+","+goodsId, verifyCodeDTO.getVerifyCodeResult());
        //输出图片
        return verifyCodeDTO.getBufferedImage();
    }

    @Override
    public Boolean checkMiaoShaPath(Long miaoshaUserId, Long goodsId, String miaoShaPath) {

        if(Objects.equals(null,miaoshaUserId)) {
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("商品id为空"));
        }else if(Objects.equals(null,goodsId)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("用户id为空"));
        }else if(Objects.equals(null,miaoShaPath)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("秒杀路径为空"));
        }
        String cacheMiaoShaPath = jedisService.get(MiaoshaKey.getMiaoshaPath, ""+miaoshaUserId + "_"+ goodsId, String.class);
        return Objects.equals(miaoShaPath,cacheMiaoShaPath);
    }

    @Override
    public Boolean checkVerifyCode(Long miaoshaUserId, Long goodsId,Integer verifyCode) {
        if(Objects.equals(null,miaoshaUserId)) {
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("商品id为空"));
        }else if(Objects.equals(null,goodsId)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("用户id为空"));
        }else if(Objects.equals(null,verifyCode)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("验证码为空"));
        }
        Integer cacheVerifyCode = jedisService.get(MiaoshaKey.getMiaoshaVerifyCode, miaoshaUserId+","+goodsId, Integer.class);
        if(!Objects.equals(cacheVerifyCode ,verifyCode)) {
            return false;
        }
        jedisService.delete(MiaoshaKey.getMiaoshaVerifyCode, miaoshaUserId+","+goodsId);
        return true;
    }

    @Override
    public ResultVO<Long> getMiaoshaResult(long goodsId, Long miaoshaUserId) {
        MiaoshaOrder miaoshaOrder = miaoshaOrderService.queryByUserIdAndGoodsId(miaoshaUserId,goodsId);

        //秒杀成功
        if(!Objects.equals(null,miaoshaOrder)){
            return ResultUtil.error(ResultEnum.MIAOSHA_SUCCESS,miaoshaOrder.getOrderId());
        }

        //商品秒杀结束标志
        boolean miaoshaOverFlag = jedisService.exists(MiaoshaKey.isGoodsOver, ""+goodsId);

        //秒杀已结束
        if(miaoshaOverFlag){
            return ResultUtil.error(ResultEnum.MIAOSHA_OVER);
        }
        //排队中
        return ResultUtil.error(ResultEnum.WAIT_IN_LINE);
    }
}

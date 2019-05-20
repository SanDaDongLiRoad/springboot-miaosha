package com.lizhi.miaosha.service;

import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.domain.OrderInfo;
import com.lizhi.miaosha.vo.MiaoshaGoodsVO;

/**
 * 秒杀业务类
 *
 * @author xulizhi-lenovo
 * @date 2019/5/20
 */
public interface MiaoshaService {

    /**
     * 执行秒杀
     * @param miaoshaUser
     * @param miaoshaGoodsVO
     * @return
     */
    OrderInfo miaosha(MiaoshaUser miaoshaUser, MiaoshaGoodsVO miaoshaGoodsVO);
}

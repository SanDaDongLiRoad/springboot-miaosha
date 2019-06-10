package com.lizhi.miaosha.service;

import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.vo.MiaoshaGoodsVO;

import java.awt.image.BufferedImage;

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
    Long miaosha(MiaoshaUser miaoshaUser, MiaoshaGoodsVO miaoshaGoodsVO);

    /**
     * 创建秒杀地址
     * @param user
     * @param goodsId
     * @return
     */
    String createMiaoshaPath(MiaoshaUser user, long goodsId);

    /**
     * 创建秒杀验证码
     * @param user
     * @param goodsId
     * @return
     */
    BufferedImage createVerifyCode(MiaoshaUser user, long goodsId);

    /**
     * 校验秒杀路径
     * @param user
     * @param goodsId
     * @param miaoShaPath
     * @return
     */
    Boolean checkMiaoShaPath(MiaoshaUser user, long goodsId,String miaoShaPath);

    /**
     * 校验验证码
     * @param user
     * @param goodsId
     * @param verifyCode
     * @return
     */
    Boolean checkVerifyCode(MiaoshaUser user, long goodsId,int verifyCode);
}

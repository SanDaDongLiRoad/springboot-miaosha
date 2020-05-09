package com.lizhi.miaosha.service;

import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.vo.MiaoshaGoodVO;
import com.lizhi.miaosha.vo.ResultVO;

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
     * @param miaoshaGoodVO
     * @return
     */
    Long miaosha(MiaoshaUser miaoshaUser, MiaoshaGoodVO miaoshaGoodVO);

    /**
     * 创建秒杀地址
     * @param miaoshaUserId
     * @param goodsId
     * @return
     */
    String createMiaoshaPath(Long miaoshaUserId, Long goodsId);

    /**
     * 创建秒杀验证码
     * @param user
     * @param goodsId
     * @return
     */
    BufferedImage createVerifyCode(MiaoshaUser user, long goodsId);

    /**
     * 校验秒杀路径
     * @param miaoshaUserId
     * @param goodsId
     * @param miaoShaPath
     * @return
     */
    Boolean checkMiaoShaPath(Long miaoshaUserId, Long goodsId,String miaoShaPath);

    /**
     * 校验验证码
     * @param miaoshaUserId
     * @param goodsId
     * @param verifyCode
     * @return
     */
    Boolean checkVerifyCode(Long miaoshaUserId, Long goodsId,Integer verifyCode);

    /**
     * 获取秒杀结果
     * @param goodsId
     * @param miaoshaUserId
     * @return
     */
    ResultVO<Long> getMiaoshaResult(long goodsId, Long miaoshaUserId);
}

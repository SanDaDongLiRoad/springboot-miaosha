package com.lizhi.miaosha.vo;

import com.lizhi.miaosha.domain.MiaoshaUser;
import lombok.Data;

/**
 * 秒杀商品详情页信息
 *
 * @author xulizhi-lenovo
 * @date 2019/6/4
 */
@Data
public class GoodsDetailVO {

    /**
     * 秒杀商品详情
     */
    private MiaoshaGoodsVO miaoshaGoodsVO;

    /**
     * 秒杀用户信息
     */
    private MiaoshaUser miaoshaUser;

    /**
     * 秒杀状态（0：未开始，1：秒杀进行中，2：秒杀已经结束）
     */
    private int miaoshaStatus;
    /**
     * 秒杀剩余时间（秒）
     */
    private int remainSeconds;
}

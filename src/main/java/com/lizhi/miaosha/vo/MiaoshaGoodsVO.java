package com.lizhi.miaosha.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 秒杀商品信息
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Data
public class MiaoshaGoodsVO {

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品的图片
     */
    private String goodsImg;

    /**
     * 商品原价
     */
    private BigDecimal goodsPrice;

    /**
     * 秒杀价格
     */
    private BigDecimal miaoshaPrice;

    /**
     * 秒杀量
     */
    private Integer stockCount;

    /**
     * 秒杀开始时间
     */
    private Date startDate;
    /**
     * 秒杀结束时间
     */
    private Date endDate;
}

package com.lizhi.miaosha.domain;

import com.lizhi.miaosha.base.BaseDomain;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Data
public class Goods extends BaseDomain {
    private static final long serialVersionUID = -8737422420455583287L;

    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品标题
     */
    private String goodsTitle;
    /**
     * 商品的图片
     */
    private String goodsImg;
    /**
     * 商品的详情介绍
     */
    private String goodsDetail;
    /**
     * 商品单价
     */
    private BigDecimal goodsPrice;
    /**
     * 商品库存
     */
    private Integer goodsStock;
}

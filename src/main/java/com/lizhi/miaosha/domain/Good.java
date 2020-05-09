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
public class Good extends BaseDomain {
    private static final long serialVersionUID = -8737422420455583287L;

    /**
     * 商品名称
     */
    private String goodName;
    /**
     * 商品标题
     */
    private String goodTitle;
    /**
     * 商品的图片
     */
    private String goodImg;
    /**
     * 商品的详情介绍
     */
    private String goodDetail;
    /**
     * 商品单价
     */
    private BigDecimal goodPrice;
    /**
     * 商品库存
     */
    private Integer goodStock;
}

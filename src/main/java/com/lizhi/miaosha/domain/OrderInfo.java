package com.lizhi.miaosha.domain;

import com.lizhi.miaosha.base.BaseDomain;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Data
public class OrderInfo extends BaseDomain {
    private static final long serialVersionUID = 4412578023078629575L;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 商品ID
     */
    private Long goodsId;
    /**
     * 收货地址ID
     */
    private Long  deliveryAddrId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品数量
     */
    private Integer goodsCount;
    /**
     * 下单价格
     */
    private BigDecimal goodsPrice;
    /**
     * 渠道（1pc，2android，3ios）
     */
    private Integer orderChannel;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 支付时间
     */
    private Date payDate;
}

package com.lizhi.miaosha.domain;

import com.lizhi.miaosha.base.BaseDomain;
import lombok.Data;

/**
 * 秒杀订单
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Data
public class MiaoshaOrder extends BaseDomain {
    private static final long serialVersionUID = 4135173198875327098L;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 订单ID
     */
    private Long  orderId;
    /**
     * 商品ID
     */
    private Long goodId;
}

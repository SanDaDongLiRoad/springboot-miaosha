package com.lizhi.miaosha.domain;

import com.lizhi.miaosha.base.BaseDomain;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 秒杀商品
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Data
public class MiaoshaGood extends BaseDomain {
    private static final long serialVersionUID = 908482003288559126L;

    /**
     * 商品Id
     */
    private Long goodId;
    /**
     * 秒杀价格
     */
    private BigDecimal miaoshaPrice;
    /**
     * 库存数量
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

package com.lizhi.miaosha.redis;

/**
 * 订单相关
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/6/5
 */
public class OrderKey extends BasePrefix {

    public OrderKey(String prefix) {
        super(prefix);
    }
    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");
}

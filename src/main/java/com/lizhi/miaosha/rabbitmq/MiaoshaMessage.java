package com.lizhi.miaosha.rabbitmq;

import com.lizhi.miaosha.domain.MiaoshaUser;
import lombok.Data;

import java.io.Serializable;

/**
 * 秒杀消息
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/6/17
 */
@Data
public class MiaoshaMessage implements Serializable {
    private static final long serialVersionUID = 1849761828214057318L;

    /**
     * 秒杀用户
     */
    private MiaoshaUser miaoshaUser;

    /**
     * 商品ID
     */
    private long goodsId;
}

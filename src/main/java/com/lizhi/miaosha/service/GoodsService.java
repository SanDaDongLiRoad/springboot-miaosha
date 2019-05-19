package com.lizhi.miaosha.service;

import com.lizhi.miaosha.domain.Goods;

/**
 * 商品业务类
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
public interface GoodsService {

    /**
     * 通过商品id查询商品
     * @param id
     * @return
     */
    Goods queryGoodsById(Long id);
}

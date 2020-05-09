package com.lizhi.miaosha.service;

import com.lizhi.miaosha.domain.Good;

/**
 * 商品业务类
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
public interface GoodService {

    /**
     * 通过商品id查询商品
     * @param id
     * @return
     */
    Good queryGoodsById(Long id);
}

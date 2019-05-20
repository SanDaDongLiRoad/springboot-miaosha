package com.lizhi.miaosha.service;

import com.lizhi.miaosha.vo.MiaoshaGoodsVO;

import java.util.List;

/**
 * 秒杀商品业务类
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
public interface MiaoshaGoodsService {

    /**
     * 查询秒杀商品列表页的秒杀商品列表
     * @return
     */
    List<MiaoshaGoodsVO> queryMiaoshaGoodsVOList();

    /**
     * 根据商品id查询秒杀商品
     * @param goodsId
     * @return
     */
    MiaoshaGoodsVO queryMiaoshaGoodsVOById(Long goodsId);

    /**
     * 减库存
     * @param goodsId
     * @return
     */
    boolean reduceStock(Long goodsId);
}

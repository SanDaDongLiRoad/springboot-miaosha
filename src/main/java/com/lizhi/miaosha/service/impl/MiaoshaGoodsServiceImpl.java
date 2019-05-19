package com.lizhi.miaosha.service.impl;

import com.lizhi.miaosha.dao.MiaoshaGoodsDao;
import com.lizhi.miaosha.domain.Goods;
import com.lizhi.miaosha.domain.MiaoshaGoods;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.service.GoodsService;
import com.lizhi.miaosha.service.MiaoshaGoodsService;
import com.lizhi.miaosha.vo.MiaoshaGoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 秒杀商品业务类
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Slf4j
@Service
public class MiaoshaGoodsServiceImpl implements MiaoshaGoodsService {

    @Autowired
    private MiaoshaGoodsDao miaoshaGoodsDao;

    @Autowired
    private GoodsService goodsService;

    @Override
    public List<MiaoshaGoodsVO> queryMiaoshaGoodsVOList() {

        List<MiaoshaGoodsVO> miaoshaGoodsVOList = new ArrayList<MiaoshaGoodsVO>();
        List<MiaoshaGoods> miaoshaGoodsList = miaoshaGoodsDao.queryList();
        if(!Objects.equals(null,miaoshaGoodsList) && (miaoshaGoodsList.size() > 0)){
            for(MiaoshaGoods miaoshaGoods : miaoshaGoodsList){
                Goods goods = goodsService.queryGoodsById(miaoshaGoods.getGoodsId());
                MiaoshaGoodsVO miaoshaGoodsVO = new MiaoshaGoodsVO();
                miaoshaGoodsVO.setGoodsId(miaoshaGoods.getGoodsId());
                miaoshaGoodsVO.setGoodsName(goods.getGoodsName());
                miaoshaGoodsVO.setGoodsImg(goods.getGoodsImg());
                miaoshaGoodsVO.setGoodsPrice(goods.getGoodsPrice());
                miaoshaGoodsVO.setMiaoshaPrice(miaoshaGoods.getMiaoshaPrice());
                miaoshaGoodsVO.setStockCount(miaoshaGoods.getStockCount());
                miaoshaGoodsVO.setStartDate(miaoshaGoods.getStartDate());
                miaoshaGoodsVO.setEndDate(miaoshaGoods.getEndDate());
                miaoshaGoodsVOList.add(miaoshaGoodsVO);
            }
        }
        return miaoshaGoodsVOList;
    }

    @Override
    public MiaoshaGoodsVO queryMiaoshaGoodsVOById(Long goodsId) {
        log.info("queryMiaoshaGoodsVOById requestParam goodsId is: {}",goodsId);
        if(Objects.equals(null,goodsId)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("goodsId为空"));
        }
        MiaoshaGoodsVO miaoshaGoodsVO = new MiaoshaGoodsVO();
        MiaoshaGoods miaoshaGoods = miaoshaGoodsDao.queryById(goodsId);
        Goods goods = goodsService.queryGoodsById(goodsId);
        miaoshaGoodsVO.setGoodsId(miaoshaGoods.getGoodsId());
        miaoshaGoodsVO.setGoodsName(goods.getGoodsName());
        miaoshaGoodsVO.setGoodsImg(goods.getGoodsImg());
        miaoshaGoodsVO.setGoodsPrice(goods.getGoodsPrice());
        miaoshaGoodsVO.setMiaoshaPrice(miaoshaGoods.getMiaoshaPrice());
        miaoshaGoodsVO.setStockCount(miaoshaGoods.getStockCount());
        miaoshaGoodsVO.setStartDate(miaoshaGoods.getStartDate());
        miaoshaGoodsVO.setEndDate(miaoshaGoods.getEndDate());
        return miaoshaGoodsVO;
    }
}

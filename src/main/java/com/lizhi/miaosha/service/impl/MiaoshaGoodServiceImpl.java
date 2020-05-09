package com.lizhi.miaosha.service.impl;

import com.lizhi.miaosha.dao.MiaoshaGoodDao;
import com.lizhi.miaosha.domain.Good;
import com.lizhi.miaosha.domain.MiaoshaGood;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.service.GoodService;
import com.lizhi.miaosha.service.MiaoshaGoodsService;
import com.lizhi.miaosha.vo.MiaoshaGoodVO;
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
public class MiaoshaGoodServiceImpl implements MiaoshaGoodsService {

    @Autowired
    private MiaoshaGoodDao miaoshaGoodDao;

    @Autowired
    private GoodService goodService;

    @Override
    public List<MiaoshaGoodVO> queryMiaoshaGoodsVOList() {

        List<MiaoshaGoodVO> miaoshaGoodVOList = new ArrayList<MiaoshaGoodVO>();
        List<MiaoshaGood> miaoshaGoodList = miaoshaGoodDao.queryList();
        if(!Objects.equals(null, miaoshaGoodList) && (miaoshaGoodList.size() > 0)){
            for(MiaoshaGood miaoshaGood : miaoshaGoodList){
                Good good = goodService.queryGoodsById(miaoshaGood.getGoodId());
                MiaoshaGoodVO miaoshaGoodVO = new MiaoshaGoodVO();
                miaoshaGoodVO.setGoodId(miaoshaGood.getGoodId());
                miaoshaGoodVO.setGoodName(good.getGoodName());
                miaoshaGoodVO.setGoodImg(good.getGoodImg());
                miaoshaGoodVO.setGoodPrice(good.getGoodPrice());
                miaoshaGoodVO.setMiaoshaPrice(miaoshaGood.getMiaoshaPrice());
                miaoshaGoodVO.setStockCount(miaoshaGood.getStockCount());
                miaoshaGoodVO.setStartDate(miaoshaGood.getStartDate());
                miaoshaGoodVO.setEndDate(miaoshaGood.getEndDate());
                miaoshaGoodVOList.add(miaoshaGoodVO);
            }
        }
        return miaoshaGoodVOList;
    }

    @Override
    public MiaoshaGoodVO queryMiaoshaGoodsVOById(Long goodsId) {
        log.info("queryMiaoshaGoodsVOById requestParam goodsId is: {}",goodsId);
        if(Objects.equals(null,goodsId)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("goodsId为空"));
        }
        MiaoshaGoodVO miaoshaGoodVO = new MiaoshaGoodVO();
        MiaoshaGood miaoshaGood = miaoshaGoodDao.queryById(goodsId);
        Good good = goodService.queryGoodsById(goodsId);
        miaoshaGoodVO.setGoodId(miaoshaGood.getGoodId());
        miaoshaGoodVO.setGoodName(good.getGoodName());
        miaoshaGoodVO.setGoodImg(good.getGoodImg());
        miaoshaGoodVO.setGoodPrice(good.getGoodPrice());
        miaoshaGoodVO.setMiaoshaPrice(miaoshaGood.getMiaoshaPrice());
        miaoshaGoodVO.setStockCount(miaoshaGood.getStockCount());
        miaoshaGoodVO.setStartDate(miaoshaGood.getStartDate());
        miaoshaGoodVO.setEndDate(miaoshaGood.getEndDate());
        return miaoshaGoodVO;
    }

    @Override
    public boolean reduceStock(Long goodsId) {
        log.info("reduceStock requestParam goodsId is: {}",goodsId);
        if(Objects.equals(null,goodsId)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("goodsId为空"));
        }
        int flag = miaoshaGoodDao.reduceStock(goodsId);
        log.info("reduceStock result flag is: {}",flag);
        return flag > 0;
    }
}

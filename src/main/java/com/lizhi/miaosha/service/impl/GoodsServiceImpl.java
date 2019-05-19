package com.lizhi.miaosha.service.impl;

import com.lizhi.miaosha.dao.GoodsDao;
import com.lizhi.miaosha.domain.Goods;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 商品业务类
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public Goods queryGoodsById(Long id) {
        log.info("queryGoodsById requestParam goodsId is: {}",id);
        if(Objects.equals(null,id)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("商品id为空"));
        }
        Goods goods = goodsDao.queryById(id);
        log.info("queryGoodsById result goods is: {}",goods);
        return goods;
    }
}

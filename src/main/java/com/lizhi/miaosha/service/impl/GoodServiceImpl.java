package com.lizhi.miaosha.service.impl;

import com.lizhi.miaosha.dao.GoodDao;
import com.lizhi.miaosha.domain.Good;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.service.GoodService;
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
public class GoodServiceImpl implements GoodService {

    @Autowired
    private GoodDao goodDao;

    @Override
    public Good queryGoodsById(Long id) {
        log.info("queryGoodsById requestParam goodsId is: {}",id);
        if(Objects.equals(null,id)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("商品id为空"));
        }
        Good good = goodDao.queryById(id);
        log.info("queryGoodsById result good is: {}", good);
        return good;
    }
}

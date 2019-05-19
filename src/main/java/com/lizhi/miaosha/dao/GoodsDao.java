package com.lizhi.miaosha.dao;

import com.lizhi.miaosha.domain.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 商品
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Mapper
public interface GoodsDao {

    /**
     * 通过商品id查询商品
     * @param id
     * @return
     */
    @Select("select * from goods where id= #{id} and deleted = 0")
    Goods queryById(@Param("id") Long id);
}

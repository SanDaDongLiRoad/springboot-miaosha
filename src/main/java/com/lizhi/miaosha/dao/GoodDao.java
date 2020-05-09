package com.lizhi.miaosha.dao;

import com.lizhi.miaosha.domain.Good;
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
public interface GoodDao {

    /**
     * 通过商品id查询商品
     * @param id
     * @return
     */
    @Select("select * from good where id= #{id} and deleted = 0")
    Good queryById(@Param("id") Long id);
}

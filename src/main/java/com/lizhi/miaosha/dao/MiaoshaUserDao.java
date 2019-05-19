package com.lizhi.miaosha.dao;

import com.lizhi.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 秒杀用户
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/18
 */
@Mapper
public interface MiaoshaUserDao {

    /**
     * 根据用户id查询秒杀用户
     * @param id
     * @return
     */
    @Select("select * from miaosha_user where id = #{id} and deleted = 0")
    MiaoshaUser queryById(@Param("id") Long id);

}

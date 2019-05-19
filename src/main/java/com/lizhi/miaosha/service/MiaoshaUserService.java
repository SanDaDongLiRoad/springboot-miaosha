package com.lizhi.miaosha.service;

import com.lizhi.miaosha.domain.MiaoshaUser;

import javax.servlet.http.HttpServletResponse;

/**
 * 秒杀用户业务类
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/18
 */
public interface MiaoshaUserService {

    /**
     * 根据用户id查询秒杀用户
     * @param id
     * @return
     */
    MiaoshaUser queryMiaoshaUserById(Long id);

    /**
     * 根据token查询秒杀用户
     * @param token
     * @return
     */
    MiaoshaUser queryMiaoshaUserByToken(HttpServletResponse response, String token);
}

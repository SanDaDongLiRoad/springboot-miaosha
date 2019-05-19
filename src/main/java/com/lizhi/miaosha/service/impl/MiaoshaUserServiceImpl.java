package com.lizhi.miaosha.service.impl;

import com.lizhi.miaosha.dao.MiaoshaUserDao;
import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.redis.JedisService;
import com.lizhi.miaosha.redis.MiaoshaUserKey;
import com.lizhi.miaosha.service.MiaoshaUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.lizhi.miaosha.constant.CommonConstant.COOKI_NAME_TOKEN;

/**
 * Demo class
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/18
 */
@Slf4j
@Service
public class MiaoshaUserServiceImpl implements MiaoshaUserService {

    @Autowired
    private JedisService jedisService;

    @Autowired
    private MiaoshaUserDao miaoshaUserDao;

    @Override
    public MiaoshaUser queryMiaoshaUserById(Long id) {
        log.info("queryMiaoshaUserById requestParam userId is: {}",id);
        if(Objects.equals(null,id)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("用户id为空"));
        }
        MiaoshaUser miaoshaUser = miaoshaUserDao.queryById(id);
        log.info("queryMiaoshaUserById result miaoshaUser is: {}",miaoshaUser);
        return miaoshaUser;
    }

    @Override
    public MiaoshaUser queryMiaoshaUserByToken(HttpServletResponse response, String token) {
        log.info("queryMiaoshaUserByToken requestParam token is: {}",token);
        if(Objects.equals(null,token)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("token为空"));
        }
        MiaoshaUser miaoshaUser = jedisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        log.info("queryMiaoshaUserByToken result miaoshaUser is: {}",miaoshaUser);
        //延长有效期
        if(miaoshaUser != null) {
            addCookie(response, token, miaoshaUser);
        }
        return miaoshaUser;
    }

    /**
     * 生成Cookie写回客户端
     * @param response
     * @param token
     * @param miaoshaUser
     */
    private void addCookie(HttpServletResponse response, String token, MiaoshaUser miaoshaUser) {
        jedisService.set(MiaoshaUserKey.token, token, miaoshaUser);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}

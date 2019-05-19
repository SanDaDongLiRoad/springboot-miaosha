package com.lizhi.miaosha.service.impl;

import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.redis.JedisService;
import com.lizhi.miaosha.redis.MiaoshaUserKey;
import com.lizhi.miaosha.service.LoginService;
import com.lizhi.miaosha.service.MiaoshaUserService;
import com.lizhi.miaosha.util.MD5Util;
import com.lizhi.miaosha.util.UUIDUtil;
import com.lizhi.miaosha.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.lizhi.miaosha.constant.CommonConstant.COOKI_NAME_TOKEN;

/**
 * 登录业务类
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private JedisService jedisService;

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Override
    public String doLogin(HttpServletResponse response,LoginVO loginVO) {
        log.info("doLogin requestParam loginVO is: {}",loginVO);

        //doLogin方法请求参数非空校验
        checkDoLoginRequestParam(loginVO);
        //校验手机号是否存在
        MiaoshaUser miaoshaUser = miaoshaUserService.queryMiaoshaUserById(Long.parseLong(loginVO.getMobile()));
        if(Objects.equals(null,miaoshaUser)){
            throw new GlobalException(ResultEnum.MOBILE_NOT_EXIST);
        }
        //校验密码
        String dbPass = miaoshaUser.getPassword();
        String dbSalt = miaoshaUser.getSalt();
        String calcPass = MD5Util.formPassToDBPass(loginVO.getPassword(), dbSalt);
        if(!Objects.equals(calcPass,dbPass)) {
            throw new GlobalException(ResultEnum.PASSWORD_ERROR);
        }

        //校验通过生成token
        String token = UUIDUtil.uuid();
        addCookie(response,token,miaoshaUser);
        return token;
    }

    /**
     * doLogin方法请求参数非空校验
     * @param loginVO
     */
    private void checkDoLoginRequestParam(LoginVO loginVO){
        if(Objects.equals(null,loginVO)){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("loginVO is null!"));
        }else if(Objects.equals(null,loginVO.getMobile())){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("mobile is null!"));
        }else if(StringUtils.isEmpty(loginVO.getPassword())){
            throw new GlobalException(ResultEnum.SERVICE_PARAM_ERROR.fillArgs("password 为空!"));
        }
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

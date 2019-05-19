package com.lizhi.miaosha.service;

import com.lizhi.miaosha.vo.LoginVO;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录业务类
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
public interface LoginService {

    /**
     * 登录业务类
     * @param loginVO
     * @return
     */
    String doLogin(HttpServletResponse response, LoginVO loginVO);
}

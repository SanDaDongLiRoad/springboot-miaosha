package com.lizhi.miaosha.controller;

import com.lizhi.miaosha.service.LoginService;
import com.lizhi.miaosha.util.ResultUtil;
import com.lizhi.miaosha.vo.LoginVO;
import com.lizhi.miaosha.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 登录
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 跳转登录首页
     * @return
     */
    @GetMapping("to_login")
    public String toLogin(){
        return "login";
    }

    /**
     * 登录
     * @param loginVO
     * @return
     */
    @ResponseBody
    @PostMapping("do_login")
    public ResultVO<String> doLogin(HttpServletResponse response, @Valid LoginVO loginVO){
        String token = loginService.doLogin(response,loginVO);
        return ResultUtil.success(token);
    }
}

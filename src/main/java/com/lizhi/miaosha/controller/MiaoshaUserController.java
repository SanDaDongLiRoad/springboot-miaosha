package com.lizhi.miaosha.controller;

import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.service.MiaoshaUserService;
import com.lizhi.miaosha.util.ResultUtil;
import com.lizhi.miaosha.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * 秒杀用户
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/18
 */
@Slf4j
@Controller
@RequestMapping("miaoshaUser")
public class MiaoshaUserController {

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    /**
     * 根据用户id查询秒杀用户
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("queryMiaoshaUserById")
    public ResultVO<MiaoshaUser> queryMiaoshaUserById(@RequestParam Long id){
        log.info("queryMiaoshaUserById requestParam userId is: {}",id);
        if(Objects.equals(null,id)){
            throw new GlobalException(ResultEnum.CONTROLLER_PARAM_ERROR.fillArgs("用户id为空"));
        }
        MiaoshaUser miaoshaUser = miaoshaUserService.queryMiaoshaUserById(id);
        log.info("queryMiaoshaUserById result miaoshaUser is: {}",miaoshaUser);
        return ResultUtil.success(miaoshaUser);
    }

}

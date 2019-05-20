package com.lizhi.miaosha.controller;

import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.service.MiaoshaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 执行秒杀
 *
 * @author xulizhi-lenovo
 * @date 2019/5/20
 */
@Controller
@RequestMapping("miaosha")
public class MiaoshaController {

    @Autowired
    private MiaoshaService miaoshaService;

    @GetMapping("do_miaosha")
    public String miaosha(Model model,MiaoshaUser miaoshaUser,@RequestParam("goodsId")long goodsId){
        model.addAttribute("miaoshaUser", miaoshaUser);
        return "order_detail";
    }
}

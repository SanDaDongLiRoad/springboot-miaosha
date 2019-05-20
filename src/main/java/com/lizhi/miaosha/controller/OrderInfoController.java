package com.lizhi.miaosha.controller;

import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.domain.OrderInfo;
import com.lizhi.miaosha.service.MiaoshaGoodsService;
import com.lizhi.miaosha.service.OrderInfoService;
import com.lizhi.miaosha.vo.MiaoshaGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 订单
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/20
 */
@Controller
@RequestMapping("orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private MiaoshaGoodsService miaoshaGoodsService;


    /**
     * 跳转到订单详情页
     * @param model
     * @param miaoshaUser
     * @param orderIds
     * @return
     */
    @GetMapping("queryOrderInfoDetail")
    public String queryOrderInfoDetail(Model model, MiaoshaUser miaoshaUser, @RequestParam Long orderIds){
        model.addAttribute("miaoshaUser", miaoshaUser);
        OrderInfo orderInfo = orderInfoService.queryOrderInfoById(orderIds);
        model.addAttribute("orderInfo", orderInfo);
        MiaoshaGoodsVO miaoshaGoodsVO = miaoshaGoodsService.queryMiaoshaGoodsVOById(orderInfo.getGoodsId());
        model.addAttribute("miaoshaGoodsVO", miaoshaGoodsVO);
        return "order_detail";
    }
}

package com.lizhi.miaosha.controller;

import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.domain.OrderInfo;
import com.lizhi.miaosha.service.MiaoshaGoodsService;
import com.lizhi.miaosha.service.OrderInfoService;
import com.lizhi.miaosha.util.ResultUtil;
import com.lizhi.miaosha.vo.MiaoshaGoodVO;
import com.lizhi.miaosha.vo.OrderDetailVo;
import com.lizhi.miaosha.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        MiaoshaGoodVO miaoshaGoodVO = miaoshaGoodsService.queryMiaoshaGoodsVOById(orderInfo.getGoodId());
        model.addAttribute("miaoshaGoodsVO", miaoshaGoodVO);
        return "order_detail";
    }

    /**
     * 订单详情页面数据接口
     * @param miaoshaUser
     * @param orderIds
     * @return
     */
    @ResponseBody
    @GetMapping("queryOrderInfoDetail2")
    public ResultVO<OrderDetailVo> queryOrderInfoDetail2(MiaoshaUser miaoshaUser, @RequestParam Long orderIds){
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setMiaoshaUser(miaoshaUser);
        OrderInfo orderInfo = orderInfoService.queryOrderInfoById(orderIds);
        orderDetailVo.setOrderInfo(orderInfo);
        MiaoshaGoodVO miaoshaGoodVO = miaoshaGoodsService.queryMiaoshaGoodsVOById(orderInfo.getGoodId());
        orderDetailVo.setMiaoshaGoodVO(miaoshaGoodVO);
        return ResultUtil.success(orderDetailVo);
    }
}

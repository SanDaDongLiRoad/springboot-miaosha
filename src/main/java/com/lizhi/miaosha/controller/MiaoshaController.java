package com.lizhi.miaosha.controller;

import com.lizhi.miaosha.domain.MiaoshaOrder;
import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.domain.OrderInfo;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.service.MiaoshaGoodsService;
import com.lizhi.miaosha.service.MiaoshaOrderService;
import com.lizhi.miaosha.service.MiaoshaService;
import com.lizhi.miaosha.vo.MiaoshaGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

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

    @Autowired
    private MiaoshaGoodsService miaoshaGoodsService;

    @Autowired
    private MiaoshaOrderService miaoshaOrderService;

    /**
     * 执行秒杀
     * @param model
     * @param miaoshaUser
     * @param goodsId
     * @return
     */
    @PostMapping("do_miaosha")
    public String miaosha(Model model,MiaoshaUser miaoshaUser,@RequestParam("goodsId")Long goodsId){
        model.addAttribute("miaoshaUser", miaoshaUser);

        if(Objects.equals(null,miaoshaUser)){
            return "login";
        }
        //判断库存是否充足
        MiaoshaGoodsVO miaoshaGoodsVO = miaoshaGoodsService.queryMiaoshaGoodsVOById(goodsId);
        if(Objects.equals(null,miaoshaGoodsVO) || Objects.equals(0,miaoshaGoodsVO.getStockCount())){
            throw new GlobalException(ResultEnum.MIAO_SHA_OVER);
        }

        //判断用户是否已经秒杀过
        MiaoshaOrder miaoshaOrder = miaoshaOrderService.queryByUserIdAndGoodsId(miaoshaUser.getId(),goodsId);
        if(!Objects.equals(null,miaoshaOrder)){
            throw new GlobalException(ResultEnum.REPEATE_MIAOSHA);
        }
        //开始秒杀
        OrderInfo orderInfo = miaoshaService.miaosha(miaoshaUser,miaoshaGoodsVO);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("miaoshaGoodsVO", miaoshaGoodsVO);
        return "order_detail";
    }
}

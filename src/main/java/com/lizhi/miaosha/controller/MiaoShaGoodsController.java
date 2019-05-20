package com.lizhi.miaosha.controller;

import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.service.MiaoshaGoodsService;
import com.lizhi.miaosha.vo.MiaoshaGoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 秒杀商品
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Slf4j
@Controller
@RequestMapping("miaoShaGoods")
public class MiaoShaGoodsController {

    @Autowired
    private MiaoshaGoodsService miaoshaGoodsService;

    /**
     * 跳转秒杀商品列表页
     * @param model
     * @param miaoshaUser
     * @return
     */
    @GetMapping("to_list")
    public String listMiaoShaGoods(Model model, MiaoshaUser miaoshaUser){
        model.addAttribute("miaoshaUser", miaoshaUser);
        List<MiaoshaGoodsVO> miaoshaGoodsVOList = miaoshaGoodsService.queryMiaoshaGoodsVOList();
        model.addAttribute("miaoshaGoodsVOList", miaoshaGoodsVOList);
        return "goods_list";
    }

    /**
     * 秒杀商品
     * @param model
     * @param miaoshaUser
     * @param goodsId
     * @return
     */
    @GetMapping("to_detail/{goodsId}")
    public String queryMiaoShaGoodsDetail(Model model, MiaoshaUser miaoshaUser, @PathVariable("goodsId") Long goodsId){
        model.addAttribute("miaoshaUser", miaoshaUser);
        MiaoshaGoodsVO miaoshaGoodsVO = miaoshaGoodsService.queryMiaoshaGoodsVOById(goodsId);
        model.addAttribute("miaoshaGoodsVO", miaoshaGoodsVO);
        long startDate = miaoshaGoodsVO.getStartDate().getTime();
        long endDate = miaoshaGoodsVO.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startDate ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startDate - now )/1000);
        }else  if(now > endDate){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
}

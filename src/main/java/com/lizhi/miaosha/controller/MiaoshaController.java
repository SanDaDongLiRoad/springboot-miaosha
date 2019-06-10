package com.lizhi.miaosha.controller;

import com.lizhi.miaosha.domain.MiaoshaOrder;
import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.service.MiaoshaGoodsService;
import com.lizhi.miaosha.service.MiaoshaOrderService;
import com.lizhi.miaosha.service.MiaoshaService;
import com.lizhi.miaosha.util.ResultUtil;
import com.lizhi.miaosha.vo.MiaoshaGoodsVO;
import com.lizhi.miaosha.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
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
    @ResponseBody
    @PostMapping("do_miaosha")
    public ResultVO<Long> miaosha(Model model, MiaoshaUser miaoshaUser, @RequestParam("goodsId")Long goodsId){
        model.addAttribute("miaoshaUser", miaoshaUser);

        MiaoshaGoodsVO miaoshaGoodsVO = miaoshaGoodsService.queryMiaoshaGoodsVOById(goodsId);
        //判断是否在秒杀期间
        long startDate = miaoshaGoodsVO.getStartDate().getTime();
        long endDate = miaoshaGoodsVO.getEndDate().getTime();
        long now = System.currentTimeMillis();
        if(now < startDate){
            throw new GlobalException(ResultEnum.NO_START);
        }
        else if(now > endDate){
            throw new GlobalException(ResultEnum.END);
        }

        //判断库存是否充足
        if(Objects.equals(null,miaoshaGoodsVO) || Objects.equals(0,miaoshaGoodsVO.getStockCount())){
            throw new GlobalException(ResultEnum.MIAO_SHA_OVER);
        }

        //判断用户是否已经秒杀过
        MiaoshaOrder miaoshaOrder = miaoshaOrderService.queryByUserIdAndGoodsId(miaoshaUser.getId(),goodsId);
        if(!Objects.equals(null,miaoshaOrder)){
            throw new GlobalException(ResultEnum.REPEATE_MIAOSHA);
        }
        //开始秒杀
        Long orderId = miaoshaService.miaosha(miaoshaUser,miaoshaGoodsVO);
        return ResultUtil.success(orderId);
    }

    /**
     * 获取秒杀地址
     * @param goodsId
     * @param verifyCode
     * @param user
     * @return
     */
    @ResponseBody
    @GetMapping("getMiaoShaPath")
    public ResultVO<String> getMiaoShaPath(@RequestParam("goodsId")long goodsId,
                                           @RequestParam(value="verifyCode", defaultValue="0")int verifyCode,MiaoshaUser user){
        String miaoShaPath  =miaoshaService.createMiaoshaPath(user, goodsId);
        return ResultUtil.success(miaoShaPath);
    }

    /**
     * 获取秒杀验证码
     * @param goodsId
     * @param response
     * @param user
     * @return
     */
    @ResponseBody
    @GetMapping("getMiaoshaVerifyCode")
    public void getMiaoshaVerifyCode(@RequestParam("goodsId")long goodsId,HttpServletResponse response,MiaoshaUser user){

        BufferedImage image  = miaoshaService.createVerifyCode(user, goodsId);
        try (OutputStream out = response.getOutputStream()) {
            ImageIO.write(image, "JPEG", out);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}

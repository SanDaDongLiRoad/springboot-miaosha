package com.lizhi.miaosha.controller;

import com.lizhi.miaosha.domain.MiaoshaOrder;
import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.rabbitmq.MQSender;
import com.lizhi.miaosha.rabbitmq.MiaoshaMessage;
import com.lizhi.miaosha.redis.GoodKey;
import com.lizhi.miaosha.redis.JedisService;
import com.lizhi.miaosha.redis.MiaoshaKey;
import com.lizhi.miaosha.service.MiaoshaGoodsService;
import com.lizhi.miaosha.service.MiaoshaOrderService;
import com.lizhi.miaosha.service.MiaoshaService;
import com.lizhi.miaosha.util.ResultUtil;
import com.lizhi.miaosha.vo.MiaoshaGoodVO;
import com.lizhi.miaosha.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 执行秒杀
 *
 * @author xulizhi-lenovo
 * @date 2019/5/20
 */
@Slf4j
@Controller
@RequestMapping("miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    private JedisService jedisService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private MiaoshaGoodsService miaoshaGoodsService;

    @Autowired
    private MiaoshaOrderService miaoshaOrderService;

    @Autowired
    private MQSender sender;

    private HashMap<Long, Boolean> localOverMap =  new HashMap<Long, Boolean>();

    /**
     * 初始化秒杀商品库存到Redis
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("初始化秒杀商品库存到Redis");
        List<MiaoshaGoodVO> miaoshaGoodVOList = miaoshaGoodsService.queryMiaoshaGoodsVOList();
        if(!Objects.equals(null, miaoshaGoodVOList) && (miaoshaGoodVOList.size() > 0)){
            for(int i = 0; i< miaoshaGoodVOList.size(); i++){
                MiaoshaGoodVO miaoshaGoodVO = miaoshaGoodVOList.get(i);
                jedisService.set(GoodKey.getMiaoshaGoodStock,String.valueOf(miaoshaGoodVO.getGoodId()), miaoshaGoodVO.getStockCount());
                localOverMap.put(miaoshaGoodVO.getGoodId(), false);
            }
        }
    }

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

        MiaoshaGoodVO miaoshaGoodVO = miaoshaGoodsService.queryMiaoshaGoodsVOById(goodsId);
        //判断是否在秒杀期间
        long startDate = miaoshaGoodVO.getStartDate().getTime();
        long endDate = miaoshaGoodVO.getEndDate().getTime();
        long now = System.currentTimeMillis();
        if(now < startDate){
            throw new GlobalException(ResultEnum.NO_START);
        }
        else if(now > endDate){
            throw new GlobalException(ResultEnum.END);
        }

        //判断库存是否充足
        if(Objects.equals(null, miaoshaGoodVO) || Objects.equals(0, miaoshaGoodVO.getStockCount())){
            throw new GlobalException(ResultEnum.MIAOSHA_OVER);
        }

        //判断用户是否已经秒杀过
        MiaoshaOrder miaoshaOrder = miaoshaOrderService.queryByUserIdAndGoodsId(miaoshaUser.getId(),goodsId);
        if(!Objects.equals(null,miaoshaOrder)){
            throw new GlobalException(ResultEnum.REPEATE_MIAOSHA);
        }
        //开始秒杀
        Long orderId = miaoshaService.miaosha(miaoshaUser, miaoshaGoodVO);
        return ResultUtil.success(orderId);
    }

    /**
     * 执行秒杀 (使用消息队列RabbitMQ)
     * @param model
     * @param miaoshaUser
     * @param goodsId
     * @return
     */
    @ResponseBody
    @PostMapping("/{path}/do_miaosha2")
    public ResultVO miaosha2(Model model, MiaoshaUser miaoshaUser, @RequestParam("goodsId")Long goodsId,
                             @PathVariable("path") String path){
        model.addAttribute("miaoshaUser", miaoshaUser);

        //验证秒杀path
        boolean miaoshaPathCheckFlag = miaoshaService.checkMiaoShaPath(miaoshaUser.getId(), goodsId, path);
        if(!miaoshaPathCheckFlag){
            return ResultUtil.error(ResultEnum.REQUEST_ILLEGAL);
        }

        //内存标记，减少redis访问
        boolean overFlag = localOverMap.get(goodsId);
        if(overFlag) {
            return ResultUtil.error(ResultEnum.MIAOSHA_OVER);
        }

        //预减库存
        long stock = jedisService.decr(GoodKey.getMiaoshaGoodStock, ""+goodsId);
        if(stock < 0) {
            localOverMap.put(goodsId, true);
            jedisService.set(MiaoshaKey.isGoodsOver, ""+goodsId, true);
            return ResultUtil.error(ResultEnum.MIAOSHA_OVER);
        }
        //判断是否已经秒杀到了
        MiaoshaOrder miaoshaOrder = miaoshaOrderService.queryByUserIdAndGoodsId(miaoshaUser.getId(),goodsId);
        if(!Objects.equals(null,miaoshaOrder)) {
            return ResultUtil.error(ResultEnum.REPEATE_MIAOSHA);
        }
        //入队
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
        miaoshaMessage.setMiaoshaUser(miaoshaUser);
        miaoshaMessage.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(miaoshaMessage);
        //排队中
        return ResultUtil.error(ResultEnum.WAIT_IN_LINE);
    }

    /**
     * 获取秒杀地址
     * @param goodsId
     * @param verifyCode
     * @param miaoshaUser
     * @return
     */
    @ResponseBody
    @GetMapping("getMiaoShaPath")
    public ResultVO<String> getMiaoShaPath(@RequestParam("goodsId")Long goodsId,
                                           @RequestParam(value="verifyCode", defaultValue="0")Integer verifyCode,MiaoshaUser miaoshaUser){

        //校验验证码
        boolean verifyCodeCheckFlag = miaoshaService.checkVerifyCode(miaoshaUser.getId(), goodsId, verifyCode);
        if(!verifyCodeCheckFlag) {
            return ResultUtil.error(ResultEnum.ERROR_VERIFYCODE);
        }
        String miaoShaPath  =miaoshaService.createMiaoshaPath(miaoshaUser.getId(), goodsId);
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

    /**
     * 查询秒杀结果
     * @param goodsId
     * @param user
     * @return
     */
    @ResponseBody
    @GetMapping("getMiaoshaResult")
    public ResultVO<Long> getMiaoshaResult(@RequestParam("goodsId")long goodsId,Model model,MiaoshaUser user){
        model.addAttribute("user", user);
        return miaoshaService.getMiaoshaResult(goodsId,user.getId());
    }
}

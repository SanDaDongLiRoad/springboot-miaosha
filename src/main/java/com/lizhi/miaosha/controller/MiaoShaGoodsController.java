package com.lizhi.miaosha.controller;

import com.lizhi.miaosha.base.BaseController;
import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.redis.GoodsKey;
import com.lizhi.miaosha.redis.JedisService;
import com.lizhi.miaosha.service.MiaoshaGoodsService;
import com.lizhi.miaosha.util.ResultUtil;
import com.lizhi.miaosha.vo.GoodsDetailVO;
import com.lizhi.miaosha.vo.MiaoshaGoodsVO;
import com.lizhi.miaosha.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 秒杀商品
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Controller
@RequestMapping("miaoShaGoods")
public class MiaoShaGoodsController extends BaseController {

    @Autowired
    private JedisService jedisService;

    @Autowired
    private MiaoshaGoodsService miaoshaGoodsService;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 跳转秒杀商品列表页
     * @param model
     * @param miaoshaUser
     * @return
     */
    @GetMapping("to_list")
    public String listMiaoShaGoods(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser miaoshaUser){
        model.addAttribute("miaoshaUser", miaoshaUser);
        List<MiaoshaGoodsVO> miaoshaGoodsVOList = miaoshaGoodsService.queryMiaoshaGoodsVOList();
        model.addAttribute("miaoshaGoodsVOList", miaoshaGoodsVOList);
        return render(request, response, model, "goods_list", GoodsKey.getGoodsList, "");
    }

    /**
     * 秒杀商品详情页
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
        //秒杀还没开始，倒计时
        if(now < startDate ) {
            miaoshaStatus = 0;
            remainSeconds = (int)((startDate - now )/1000);
            //秒杀已经结束
        }else  if(now > endDate){
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

    /**
     * 秒杀商品详情页（URL 缓存）
     * @param request
     * @param response
     * @param model
     * @param miaoshaUser
     * @param goodsId
     * @return
     */
    @ResponseBody
    @GetMapping(value="to_detail2/{goodsId}",produces="text/html")
    public String queryMiaoShaGoodsDetail2(HttpServletRequest request, HttpServletResponse response,
                                           Model model, MiaoshaUser miaoshaUser, @PathVariable("goodsId") Long goodsId){
        model.addAttribute("miaoshaUser", miaoshaUser);

        //取缓存
        String html = jedisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
        if(StringUtils.isNotEmpty(html)) {
            return html;
        }
        //手动渲染
        MiaoshaGoodsVO miaoshaGoodsVO = miaoshaGoodsService.queryMiaoshaGoodsVOById(goodsId);
        model.addAttribute("miaoshaGoodsVO", miaoshaGoodsVO);

        long startDate = miaoshaGoodsVO.getStartDate().getTime();
        long endDate = miaoshaGoodsVO.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        //秒杀还没开始，倒计时
        if(now < startDate ) {
            miaoshaStatus = 0;
            remainSeconds = (int)((startDate - now )/1000);
        }else  if(now > endDate){
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {
            remainSeconds = 0;
            miaoshaStatus = 1;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        SpringWebContext ctx = new SpringWebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap(),applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if(StringUtils.isNotEmpty(html)) {
            jedisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
        }
        return html;
    }

    /**
     * 秒杀商品详情页（页面静态化，后端只负责返回接口数据）
     * @param miaoshaUser
     * @param goodsId
     * @return
     */
    @ResponseBody
    @GetMapping("to_detail3/{goodsId}")
    public ResultVO<GoodsDetailVO> queryMiaoShaGoodsDetail3(MiaoshaUser miaoshaUser, @PathVariable("goodsId") Long goodsId){
        MiaoshaGoodsVO miaoshaGoodsVO = miaoshaGoodsService.queryMiaoshaGoodsVOById(goodsId);
        long startDate = miaoshaGoodsVO.getStartDate().getTime();
        long endDate = miaoshaGoodsVO.getEndDate().getTime();
        long now = System.currentTimeMillis();

        //默认秒杀进行中
        int miaoshaStatus = 1;
        int remainSeconds = 0;

        //秒杀还没开始，倒计时
        if(now < startDate ) {
            miaoshaStatus = 0;
            remainSeconds = (int)((startDate - now )/1000);
            //秒杀已经结束
        }else if(now > endDate){
            miaoshaStatus = 2;
            remainSeconds = -1;
        }
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
        goodsDetailVO.setMiaoshaGoodsVO(miaoshaGoodsVO);
        goodsDetailVO.setMiaoshaUser(miaoshaUser);
        goodsDetailVO.setMiaoshaStatus(miaoshaStatus);
        goodsDetailVO.setRemainSeconds(remainSeconds);
        return ResultUtil.success(goodsDetailVO);
    }
}

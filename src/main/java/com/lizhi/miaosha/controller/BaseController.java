package com.lizhi.miaosha.controller;

import com.lizhi.miaosha.redis.JedisService;
import com.lizhi.miaosha.redis.KeyPrefix;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * BaseController
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/6/1
 */
@Controller
public class BaseController {


    @Value("#{'${pageCache.enbale}'}")
    private boolean pageCacheEnable;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    private JedisService jedisService;


    public String render(HttpServletRequest request, HttpServletResponse response, Model model, String viewName, KeyPrefix prefix, String key) {
        if(!pageCacheEnable) {
            return viewName;
        }
        //取缓存
        String html = jedisService.get(prefix, key, String.class);
        if(StringUtils.isNotEmpty(html)) {
            out(response, html);
            return null;
        }
        //手动渲染
        WebContext ctx = new WebContext(request,response, request.getServletContext(),request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process(viewName, ctx);
        if(!StringUtils.isEmpty(html)) {
            jedisService.set(prefix, key, html);
        }
        out(response, html);
        return null;
    }

    public static void out(HttpServletResponse response, String html){
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try{
            OutputStream out = response.getOutputStream();
            out.write(html.getBytes("UTF-8"));
            out.flush();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

package com.lizhi.miaosha.interceptor;

import com.alibaba.fastjson.JSON;
import com.lizhi.miaosha.access.AccessLimit;
import com.lizhi.miaosha.constant.CommonConstant;
import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.redis.AccessKey;
import com.lizhi.miaosha.redis.JedisService;
import com.lizhi.miaosha.service.MiaoshaUserService;
import com.lizhi.miaosha.util.CookieUtil;
import com.lizhi.miaosha.util.ResultUtil;
import com.lizhi.miaosha.util.UserContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Objects;

/**
 * 访问拦截器
 *
 * @author xulizhi-lenovo
 * @date 2019/5/17
 */
@Component
public class AccessInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Autowired
    private JedisService jedisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            MiaoshaUser miaoshaUser = getMiaoshaUser(request, response);
            UserContext.setMiaoshaUser(miaoshaUser);
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if(Objects.equals(null,accessLimit)) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            String key = request.getRequestURI();
            if(accessLimit.needLogin()) {
                if(Objects.equals(null,miaoshaUser)) {
                    render(response, ResultEnum.SESSION_ERROR);
                    return false;
                }
                key += "_" + miaoshaUser.getId();
            }
            AccessKey accessKey = AccessKey.withExpire(seconds);
            Integer count = jedisService.get(accessKey, key, Integer.class);
            if(Objects.equals(null,count)) {
                jedisService.set(accessKey, key, 1);
            }else if(count < maxCount) {
                jedisService.incr(accessKey, key);
            }else {
                render(response, ResultEnum.ACCESS_LIMIT_REACHED);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 获取秒杀用户
     * @param request
     * @param response
     * @return
     */
    private MiaoshaUser getMiaoshaUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(CommonConstant.COOKI_NAME_TOKEN);
        String cookieToken = CookieUtil.getCookieValue(request, CommonConstant.COOKI_NAME_TOKEN);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return miaoshaUserService.queryMiaoshaUserByToken(response,token);
    }

    private void render(HttpServletResponse response, ResultEnum resultEnum)throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        String str  = JSON.toJSONString(ResultUtil.error(resultEnum));
        try (OutputStream out = response.getOutputStream()) {
            out.write(str.getBytes("UTF-8"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}

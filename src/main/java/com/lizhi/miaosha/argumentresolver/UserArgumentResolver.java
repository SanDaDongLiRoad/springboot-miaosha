package com.lizhi.miaosha.argumentresolver;

import com.lizhi.miaosha.constant.CommonConstant;
import com.lizhi.miaosha.domain.MiaoshaUser;
import com.lizhi.miaosha.service.MiaoshaUserService;
import com.lizhi.miaosha.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 秒杀用户参数分解器
 *
 * @author xulizhi-lenovo
 * @date 2019/5/17
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        String paramToken = request.getParameter(CommonConstant.COOKI_NAME_TOKEN);
        String cookieToken = CookieUtil.getCookieValue(request, CommonConstant.COOKI_NAME_TOKEN);
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return miaoshaUserService.queryMiaoshaUserByToken(response,token);
    }
}

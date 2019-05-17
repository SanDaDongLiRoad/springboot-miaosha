package com.lizhi.miaosha.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Cookie工具类
 *
 * @author xulizhi-lenovo
 * @date 2019/5/17
 */
public class CookieUtil {

    /**
     * 获取Cookie值
     * @param request
     * @param cookieName
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {

        if(!Objects.equals(null,request) && !Objects.equals(null,cookieName)){
            Cookie[]  cookies = request.getCookies();
            if(cookies == null || cookies.length <= 0){
                return null;
            }
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

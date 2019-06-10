package com.lizhi.miaosha.util;

import com.lizhi.miaosha.domain.MiaoshaUser;

/**
 * Demo class
 *
 * @author xulizhi-lenovo
 * @date 2019/6/10
 */
public class UserContext {

    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<MiaoshaUser>();

    public static void setMiaoshaUser(MiaoshaUser miaoshaUser) {
        userHolder.set(miaoshaUser);
    }

    public static MiaoshaUser getMiaoshaUser() {
        return userHolder.get();
    }
}

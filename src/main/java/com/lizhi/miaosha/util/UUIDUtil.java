package com.lizhi.miaosha.util;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author xulizhi-lenovo
 * @date 2019/5/17
 */
public class UUIDUtil {

    /**
     * 生成UUID
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

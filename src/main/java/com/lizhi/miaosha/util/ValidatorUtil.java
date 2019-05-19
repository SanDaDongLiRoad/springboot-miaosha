package com.lizhi.miaosha.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 变量验证工具类
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src) {
        if(StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }
}

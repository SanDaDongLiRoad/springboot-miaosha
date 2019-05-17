package com.lizhi.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5工具类
 *
 * @author xulizhi-lenovo
 * @date 2019/5/17
 */
public class MD5Util {

    /**
     * 客户端明文密码加密盐值
     */
    private static final String SALT = "1a2b3c4d";

    /**
     * md5加密
     * @param src
     * @return
     */
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * 加密客户端明文密码（客户端明文密码到服务端接收密码）
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass) {
        String str = ""+SALT.charAt(0)+SALT.charAt(2) + inputPass +SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    /**
     * 加密服务端接收密码（服务端接收密码到数据库存储密码）
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 加密客户端明文密码（客户端明文密码到数据库存储密码）
     * @param inputPass
     * @param dbSalt
     * @return
     */
    public static String inputPassToDbPass(String inputPass, String dbSalt) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, dbSalt);
        return dbPass;
    }
}

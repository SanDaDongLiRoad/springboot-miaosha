package com.lizhi.miaosha.redis;

/**
 * 请求访问相关
 *
 * @author xulizhi-lenovo
 * @date 2019/6/10
 */
public class AccessKey extends BasePrefix{

    private AccessKey( int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }

}

package com.lizhi.miaosha.enums;

import lombok.Getter;

/**
 * 接口返回结果状态码与详细信息枚举
 *
 * @author xulizhi-lenovo
 * @date 2019/5/17
 */
@Getter
public enum ResultEnum {

    /**
     * 系统相关状态码
     */
    ERROR(-1,"服务端异常"),
    SUCCESS(0, "成功"),
    CONTROLLER_PARAM_ERROR(1, "控制层参数校验异常：%s"),
    SERVICE_PARAM_ERROR(2, "业务层参数校验异常：%s"),
    NO_EXIST(3,"该条数据不存在"),

    /**
     * 登录相关状态码
     */
    MOBILE_NOT_EXIST(1001,"手机号不存在"),
    PASSWORD_ERROR(1002,"密码不正确"),

    /**
     * 秒杀相关状态码
     */
    MIAO_SHA_OVER(2001,"商品已经秒杀完毕"),
    REPEATE_MIAOSHA(2002,"不能重复秒杀"),
    ;

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

     ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 拼接完整的参数异常信息
     * @param args
     * @return
     */
    public ResultEnum fillArgs(Object... args) {
        this.msg = String.format(this.msg, args);
        return this;
    }
}

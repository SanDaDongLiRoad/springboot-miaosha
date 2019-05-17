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
    PARAM_ERROR(1, "参数校验异常：%s"),
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

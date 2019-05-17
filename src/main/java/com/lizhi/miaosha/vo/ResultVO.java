package com.lizhi.miaosha.vo;

import lombok.Data;

/**
 * 接口返回结果集
 *
 * @author xulizhi-lenovo
 * @date 2019/5/17
 */
@Data
public class ResultVO<T> {

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}

package com.lizhi.miaosha.exception;

import com.lizhi.miaosha.enums.ResultEnum;
import lombok.Getter;

/**
 * 全局异常类
 *
 * @author xulizhi-lenovo
 * @date 2019/5/17
 */
@Getter
public class GlobalException extends RuntimeException{

    private ResultEnum resultEnum;

    public GlobalException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.resultEnum = resultEnum;
    }
}

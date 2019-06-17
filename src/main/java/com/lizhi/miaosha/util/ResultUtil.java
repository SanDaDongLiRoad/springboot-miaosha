package com.lizhi.miaosha.util;

import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.vo.ResultVO;

/**
 * 接口返回结果工具类
 *
 * @author xulizhi-lenovo
 * @date 2019/5/17
 */
public class ResultUtil {

    /**
     * 成功时候的调用（存在返回数据）
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultVO success(T data){

        ResultVO<T> result = new ResultVO<T>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(),data);
        return result;
    }

    /**
     * 成功时候的调用（无返回数据）
     * @return
     */
    public static ResultVO success(){
        return success(null);
    }

    /**
     * 失败时候的调用
     * @param resultEnum
     * @return
     */
    public static <T> ResultVO<T> error(ResultEnum resultEnum){
        ResultVO<T> result = new ResultVO<T>(resultEnum.getCode(),resultEnum.getMsg(),null);
        return result;
    }

    /**
     * 失败时候的调用
     * @param resultEnum
     * @return
     */
    public static <T> ResultVO<T> error(ResultEnum resultEnum,T object){
        ResultVO<T> result = new ResultVO<T>(resultEnum.getCode(),resultEnum.getMsg(),object);
        return result;
    }
}

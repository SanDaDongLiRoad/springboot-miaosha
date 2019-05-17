package com.lizhi.miaosha.handle;

import com.lizhi.miaosha.enums.ResultEnum;
import com.lizhi.miaosha.exception.GlobalException;
import com.lizhi.miaosha.util.ResultUtil;
import com.lizhi.miaosha.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Demo class
 *
 * @author xulizhi-lenovo
 * @date 2019/5/17
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultVO<String> exceptionHandler(Exception e){

        if(e instanceof GlobalException) {
            GlobalException globalException = (GlobalException)e;
            return ResultUtil.error(globalException.getResultEnum());
        }else if(e instanceof BindException){
            BindException bindException = (BindException)e;
            List<ObjectError> errors = bindException.getAllErrors();
            ObjectError error = errors.get(0);
            String paramErrorMsg = error.getDefaultMessage();
            return ResultUtil.error(ResultEnum.PARAM_ERROR.fillArgs(paramErrorMsg));
        }else{
            log.error("【系统异常】{}", e);
            return ResultUtil.error(ResultEnum.ERROR);
        }
    }

}

package com.lizhi.miaosha.dto;

import lombok.Data;

import java.awt.image.BufferedImage;

/**
 * 验证码DTO（该系统目前验证码为数学公式验证码）
 *
 * @author xulizhi-lenovo
 * @date 2019/6/10
 */
@Data
public class VerifyCodeDTO {

    /**
     * 验证码的计算结果（如：验证码 1 + 1 * 2 则结果为3）
     */
    private Integer verifyCodeResult;

    /**
     * 图片验证码
     */
    private BufferedImage bufferedImage;

}

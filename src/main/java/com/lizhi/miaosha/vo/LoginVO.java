package com.lizhi.miaosha.vo;

import com.lizhi.miaosha.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 登录参数
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/19
 */
@Data
public class LoginVO {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min=32)
    private String password;

}

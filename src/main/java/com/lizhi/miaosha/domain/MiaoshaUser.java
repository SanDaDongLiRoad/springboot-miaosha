package com.lizhi.miaosha.domain;

import com.lizhi.miaosha.base.BaseDomain;
import lombok.Data;

import java.util.Date;

/**
 * 秒杀用户
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/18
 */
@Data
public class MiaoshaUser extends BaseDomain {

    private static final long serialVersionUID = 3356981033487915059L;

    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * MD5(MD5(pass明文+固定salt) + salt)
     */
    private String password;
    /**
     * 随机生成盐值
     */
    private String salt;
    /**
     * 头像，云存储的ID
     */
    private String head;
    /**
     * 注册时间
     */
    private Date registerDate;
    /**
     * 上次登录时间
     */
    private Date lastLoginDate;
    /**
     * 登录次数
     */
    private Integer loginCount;
}

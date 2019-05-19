package com.lizhi.miaosha.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共实体类
 *
 * @author XuLiZhi-MagicBook
 * @date 2019/5/18
 */
@Data
public class BaseDomain implements Serializable {

    private static final long serialVersionUID = 1337414756104551777L;

    /**
     * ID
     */
    private Long id;

    /**
     * 是否删除（0:未删除;1:已删除;）
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date modifyDate;
}

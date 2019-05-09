package com.farmershao.stock.persistence.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Manager
 *
 * @author Shao Yu
 * @since 2019/5/5 19:14
 **/
@Getter
@Setter
@ToString
public class Manager {
    private Integer id;

    /**
    * 姓名
    */
    private String name;

    /**
    * 手机号
    */
    private String mobile;

    /**
    * 状态：1 启用 2 禁用
    */
    private Byte status;

    /**
    * 客户数 
    */
    private Integer count;
}
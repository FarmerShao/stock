package com.farmershao.stock.persistence.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User
 *
 * @author Shao Yu
 * @since 2019/5/5 19:14
 **/
@Getter
@Setter
@ToString
public class User {
    private Integer id;

    /**
    * 手机号码
    */
    private String mobile;

    /**
    * 1 启用 2 黑名单
    */
    private Short disableFlag;

    /**
    * 昵称
    */
    private String nickname;

    /**
    * 密码
    */
    private String password;

    /**
    * 总出金
    */
    private BigDecimal totalOutMoney;

    /**
    * 总入金
    */
    private BigDecimal totalInMoney;

    /**
    * 账户余额
    */
    private BigDecimal amount;

    /**
    * 身份证
    */
    private String idCard;

    /**
    * 姓名
    */
    private String truthName;

    /**
    * 经纪人Id
    */
    private Integer broker;

    /**
    * 经纪人姓名
    */
    private String brokerName;

    /**
    * 注册时间
    */
    private Date createdAt;

    /**
    * 最后登录时间
    */
    private Date lastLoginTime;
}
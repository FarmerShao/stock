package com.farmershao.stock.persistence.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by ShaoYu on 2019/5/16.
 */

@ApiModel(value="com.farmershao.stock.persistence.model.User")
@Getter
@Setter
@ToString
public class User {
    @ApiModelProperty(value="null")
    private Integer id;

    /**
    * 手机号码
    */
    @ApiModelProperty(value="手机号码")
    private String mobile;

    /**
    * 1 启用 2 黑名单
    */
    @ApiModelProperty(value="1 启用 2 黑名单")
    private Short disableFlag;

    /**
    * 昵称
    */
    @ApiModelProperty(value="昵称")
    private String nickname;

    /**
    * 密码
    */
    @ApiModelProperty(value="密码")
    private String password;

    /**
    * 总出金
    */
    @ApiModelProperty(value="总出金")
    private BigDecimal totalOutMoney;

    /**
    * 总入金
    */
    @ApiModelProperty(value="总入金")
    private BigDecimal totalInMoney;

    /**
    * 账户余额
    */
    @ApiModelProperty(value="账户余额")
    private BigDecimal amount;

    /**
    * 身份证
    */
    @ApiModelProperty(value="身份证")
    private String idCard;

    /**
    * 姓名
    */
    @ApiModelProperty(value="姓名")
    private String truthName;

    /**
    * 经纪人Id
    */
    @ApiModelProperty(value="经纪人Id")
    private Integer broker;

    /**
    * 经纪人姓名
    */
    @ApiModelProperty(value="经纪人姓名")
    private String brokerName;

    /**
    * 注册时间
    */
    @ApiModelProperty(value="注册时间")
    private LocalDateTime createdAt;

    /**
    * 最后登录时间
    */
    @ApiModelProperty(value="最后登录时间")
    private LocalDateTime lastLoginTime;
}
package com.farmershao.stock.persistence.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by ShaoYu on 2019/5/16.
 */

@ApiModel(value="com.farmershao.stock.persistence.model.Account")
@Getter
@Setter
@ToString
public class Account {
    @ApiModelProperty(value="null")
    private Long id;

    /**
    * 交易服务器IP
    */
    @ApiModelProperty(value="交易服务器IP")
    private String tradeServerIp;

    /**
    * 端口号
    */
    @ApiModelProperty(value="端口号")
    private Integer tradePort;

    /**
    * 券商ID
    */
    @ApiModelProperty(value="券商ID")
    private Integer stockJobberId;

    /**
    * 营业部ID
    */
    @ApiModelProperty(value="营业部ID")
    private Integer stockExchangeId;

    /**
    * 账号类型
    */
    @ApiModelProperty(value="账号类型")
    private Integer accountType;

    /**
    * 登录账号
    */
    @ApiModelProperty(value="登录账号")
    private String accountNo;

    /**
    * 资金账号
    */
    @ApiModelProperty(value="资金账号")
    private String bankrollAccount;

    /**
    * 登录密码
    */
    @ApiModelProperty(value="登录密码")
    private String accountPassword;

    /**
    * 通讯密码
    */
    @ApiModelProperty(value="通讯密码")
    private String communicationPassword;

    /**
    * 是否为融券账号 1 是 2 不是
    */
    @ApiModelProperty(value="是否为融券账号 1 是 2 不是")
    private Integer financing;

    /**
    * 0 未删除 1 删除
    */
    @ApiModelProperty(value="0 未删除 1 删除")
    private Integer deleteFlag;

    /**
    * 0 可用 1不可用
    */
    @ApiModelProperty(value="0 可用 1不可用")
    private Integer isAble;

    /**
    * 券商编号
    */
    @ApiModelProperty(value="券商编号")
    private Integer qsFlag;

    /**
    * 单支股票限额
    */
    @ApiModelProperty(value="单支股票限额")
    private BigDecimal amountLimit;

    @ApiModelProperty(value="null")
    private Date createdAt;

    @ApiModelProperty(value="null")
    private Date updatedAt;

    /**
    * 是否允许担保品建仓 1 允许 2 不允许
    */
    @ApiModelProperty(value="是否允许担保品建仓 1 允许 2 不允许")
    private Integer collateral;
}
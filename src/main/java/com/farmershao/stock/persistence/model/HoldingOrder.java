package com.farmershao.stock.persistence.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by ShaoYu on 2019/5/16.
 */

@ApiModel(value="com.farmershao.stock.persistence.model.HoldingOrder")
@Getter
@Setter
@ToString
public class HoldingOrder {
    @ApiModelProperty(value="null")
    private Integer id;

    /**
    * 委托单ID
    */
    @ApiModelProperty(value="委托单ID")
    private Integer entrustOrderId;

    /**
    * 股票代码
    */
    @ApiModelProperty(value="股票代码")
    private String stockCode;

    /**
    * 股票
    */
    @ApiModelProperty(value="股票")
    private String stockName;

    /**
    * 股数
    */
    @ApiModelProperty(value="股数")
    private Integer lots;

    /**
    * 成交价
    */
    @ApiModelProperty(value="成交价")
    private BigDecimal price;

    /**
    * 止盈价
    */
    @ApiModelProperty(value="止盈价")
    private BigDecimal profitPrice;

    /**
    * 止损价
    */
    @ApiModelProperty(value="止损价")
    private BigDecimal lossPrice;

    /**
    * 订单状态：1.持仓 2.平仓 
    */
    @ApiModelProperty(value="订单状态：1.持仓 2.平仓 ")
    private Byte status;

    /**
    * 用户ID
    */
    @ApiModelProperty(value="用户ID")
    private Integer userId;

    /**
    * 盈利
    */
    @ApiModelProperty(value="盈利")
    private BigDecimal profit;

    /**
    * 保证金倍数
    */
    @ApiModelProperty(value="保证金倍数")
    private Byte marginRate;

    /**
    * 保证金
    */
    @ApiModelProperty(value="保证金")
    private BigDecimal margin;

    /**
    * 过期递延费
    */
    @ApiModelProperty(value="过期递延费")
    private BigDecimal delayFee;

    /**
    * 持仓有效天数，为0时会被系统平仓
    */
    @ApiModelProperty(value="持仓有效天数，为0时会被系统平仓")
    private Integer validDay;

    /**
    * 版本号
    */
    @ApiModelProperty(value="版本号")
    private Integer version;

    /**
    * 经纪人Id
    */
    @ApiModelProperty(value="经纪人Id")
    private Integer broker;

    /**
    * 订单创建日期
    */
    @ApiModelProperty(value="订单创建日期")
    private LocalDate createdDate;

    /**
    * 订单创建时间
    */
    @ApiModelProperty(value="订单创建时间")
    private LocalTime createdTime;

    /**
    * 订单最后修改时间
    */
    @ApiModelProperty(value="订单最后修改时间")
    private LocalDateTime updatedAt;
}
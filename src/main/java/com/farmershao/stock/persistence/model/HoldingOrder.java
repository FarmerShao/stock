package com.farmershao.stock.persistence.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * HoldingOrder
 *
 * @author Shao Yu
 * @since 2019/5/15 22:19
 **/
@ApiModel(value="com.farmershao.stock.persistence.model.HoldingOrder")
@Getter
@Setter
@ToString
public class HoldingOrder {
    @ApiModelProperty(value="null")
    private Integer id;

    /**
    * 股票ID
    */
    @ApiModelProperty(value="股票ID")
    private String stockId;

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
    @ApiModelProperty(value="订单状态：1.持仓 2.平仓")
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
    * 订单创建时间
    */
    @ApiModelProperty(value="订单创建时间")
    private Date createdAt;

    /**
    * 订单最后修改时间
    */
    @ApiModelProperty(value="订单最后修改时间")
    private Date updatedAt;
}
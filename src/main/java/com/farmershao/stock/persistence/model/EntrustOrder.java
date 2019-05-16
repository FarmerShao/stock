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

@ApiModel(value="com.farmershao.stock.persistence.model.EntrustOrder")
@Getter
@Setter
@ToString
public class EntrustOrder {
    @ApiModelProperty(value="null")
    private Integer id;

    /**
    * 用户ID
    */
    @ApiModelProperty(value="用户ID")
    private Integer userId;

    /**
    * 股票代码
    */
    @ApiModelProperty(value="股票代码")
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
    * 已成交股数
    */
    @ApiModelProperty(value="已成交股数")
    private Integer dealLots;

    /**
    * 委托价
    */
    @ApiModelProperty(value="委托价")
    private BigDecimal price;

    /**
    * 委托单类型：1.限价单 2.市价单
    */
    @ApiModelProperty(value="委托单类型：1.限价单 2.市价单")
    private Byte type;

    /**
    * 委托单方向：1.买 2.卖
    */
    @ApiModelProperty(value="委托单方向：1.买 2.卖")
    private Byte direction;

    /**
    * 订单状态：1.未成交 2. 已成交 3.部分成交 4. 用户撤销 5.待提交 6.非交易时间单
    */
    @ApiModelProperty(value="订单状态：1.未成交 2. 已成交 3.部分成交 4. 用户撤销 5.待提交 6.非交易时间单")
    private Byte status;

    /**
    * 保证金倍数:卖单时为0
    */
    @ApiModelProperty(value="保证金倍数:卖单时为0")
    private Byte marginRate;

    /**
    * 试算保证金：买单存在，卖单为0
    */
    @ApiModelProperty(value="试算保证金：买单存在，卖单为0")
    private BigDecimal margin;

    /**
    * 综合服务费
    */
    @ApiModelProperty(value="综合服务费")
    private BigDecimal serviceFee;

    /**
    * 过期递延费
    */
    @ApiModelProperty(value="过期递延费")
    private BigDecimal delayFee;

    /**
    * 系统平仓时指定持仓单ID
    */
    @ApiModelProperty(value="系统平仓时指定持仓单ID")
    private Integer holdOrderId;

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
    private Date createdDate;

    /**
    * 订单创建时间
    */
    @ApiModelProperty(value="订单创建时间")
    private Date createdTime;

    /**
    * 订单最后修改时间
    */
    @ApiModelProperty(value="订单最后修改时间")
    private Date updatedAt;
}
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

@ApiModel(value="com.farmershao.stock.persistence.model.RiskManagement")
@Getter
@Setter
@ToString
public class RiskManagement {
    /**
    * 唯一识别
    */
    @ApiModelProperty(value="唯一识别")
    private Integer id;

    /**
    * 最小杠杆比例
    */
    @ApiModelProperty(value="最小杠杆比例")
    private BigDecimal minLr;

    /**
    * 最大杠杆比例
    */
    @ApiModelProperty(value="最大杠杆比例")
    private BigDecimal maxLr;

    /**
    * 默认止盈比例
    */
    @ApiModelProperty(value="默认止盈比例")
    private BigDecimal defaultStopPr;

    /**
    * 最大止盈比例
    */
    @ApiModelProperty(value="最大止盈比例")
    private BigDecimal maxStopPr;

    /**
    * 最小保证金金额
    */
    @ApiModelProperty(value="最小保证金金额")
    private BigDecimal minMargin;

    /**
    * 保证金最大亏损比例
    */
    @ApiModelProperty(value="保证金最大亏损比例")
    private BigDecimal maxLossMr;

    /**
    * 维持保证金担保比例
    */
    @ApiModelProperty(value="维持保证金担保比例")
    private BigDecimal keepingMr;

    /**
    * 单个策略最大点买金额
    */
    @ApiModelProperty(value="单个策略最大点买金额")
    private BigDecimal strategyMaxPoint;

    /**
    * 个人最大点买金额
    */
    @ApiModelProperty(value="个人最大点买金额")
    private BigDecimal userMaxPoint;

    /**
    * 个人持仓总市值最大限额
    */
    @ApiModelProperty(value="个人持仓总市值最大限额")
    private BigDecimal userMaxBuy;

    /**
    * 允许最大跌幅
    */
    @ApiModelProperty(value="允许最大跌幅")
    private BigDecimal userMaxLose;

    /**
    * 允许最大涨幅
    */
    @ApiModelProperty(value="允许最大涨幅")
    private BigDecimal userMaxIncrease;

    /**
    * 个人单支股票最大持仓市值
    */
    @ApiModelProperty(value="个人单支股票最大持仓市值")
    private BigDecimal userMaxHold;

    /**
    * 是否开启假盘：1. 开启 2.关闭
    */
    @ApiModelProperty(value="是否开启假盘：1. 开启 2.关闭")
    private Short isFaker;

    /**
    * 更新时间
    */
    @ApiModelProperty(value="更新时间")
    private Date updatedAt;
}
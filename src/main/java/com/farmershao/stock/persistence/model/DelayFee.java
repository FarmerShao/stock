package com.farmershao.stock.persistence.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * DelayFee
 *
 * @author Shao Yu
 * @since 2019/5/15 22:19
 **/
@ApiModel(value="com.farmershao.stock.persistence.model.DelayFee")
@Getter
@Setter
@ToString
public class DelayFee {
    @ApiModelProperty(value="null")
    private Integer id;

    /**
    * 持仓单ID
    */
    @ApiModelProperty(value="持仓单ID")
    private Integer holdingOrderId;

    /**
    * 股票
    */
    @ApiModelProperty(value="股票")
    private String stockName;

    /**
    * 用户ID
    */
    @ApiModelProperty(value="用户ID")
    private Integer userId;

    /**
    * 保证金倍数
    */
    @ApiModelProperty(value="保证金倍数")
    private BigDecimal delayFee;

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
}
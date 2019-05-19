package com.farmershao.stock.persistence.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 风控-策略杠杆倍数
 *
 * @author Shao Yu
 * @since 2019/5/19 20:54
 **/
@Setter
@Getter
public class StrategyLeverage {

    private Long id;
    /**
     * 默认标记
     */
    private Integer defaultTag;
    /**
     * 默认倍数
     */
    private BigDecimal defaultLeverage;
    /**
     * 最大止损比例
     */
    private BigDecimal maxStopLossRate;
    /**
     * 预警线
     */
    private BigDecimal warningRate;

}

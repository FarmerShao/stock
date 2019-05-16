package com.farmershao.stock.trade.dto.eastmoney.trade;

/**
 * 东方财富TradeBaseDto
 *
 * @author Shao Yu
 * @since 2018/4/25 13:56
 **/
public abstract class TradeBaseDto {

    public TradeBaseDto(){}

    public abstract void read(String[] dataRow);

}

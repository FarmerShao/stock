package com.farmershao.stock.trade.dto.eastmoney.history;

/**
 * 东方财富历史信息种类实体抽象父类
 *
 * @author Shao Yu
 * @since 2018/4/25 13:52
 **/
public abstract class HistoryDto {

    public HistoryDto(){}

    public abstract void read(String[] dataRow) ;

}

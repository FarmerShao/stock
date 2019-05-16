package com.farmershao.stock.trade.constant.eastmoney;


import com.farmershao.stock.trade.constant.NameValue;
import com.farmershao.stock.trade.dto.eastmoney.history.BankrollSerialDto;
import com.farmershao.stock.trade.dto.eastmoney.history.DeliveryOrderDto;
import com.farmershao.stock.trade.dto.eastmoney.history.HistoryDealDto;
import com.farmershao.stock.trade.dto.eastmoney.history.HistoryEntrustDto;

/**
 * 查询历史信息种类
 *
 * @author Shao Yu
 * @since 2018/4/25 9:39
 **/
public enum EaseMoneyHistoryCate implements NameValue<String,Integer> {

    LSWT("历史委托", 0){
        @Override
        public Object getObject(){
            return new HistoryEntrustDto();
        }
    },
    LSCJ("历史成交", 1){
        @Override
        public Object getObject(){
            return new HistoryDealDto();
        }
    },
    ZJLS("资金流水", 2){
        @Override
        public Object getObject(){
            return new BankrollSerialDto();
        }
    },
    JGD("交割单", 3){
        @Override
        public Object getObject(){
            return new DeliveryOrderDto();
        }
    };

    private String name;
    private int id;

    EaseMoneyHistoryCate(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return id;
    }

}

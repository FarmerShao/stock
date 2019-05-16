package com.farmershao.stock.trade.constant.eastmoney;


import com.farmershao.stock.trade.constant.NameValue;
import com.farmershao.stock.trade.dto.eastmoney.trade.*;

/**
 *  东方财富查询信息的种类
 *
 * @author Shao Yu
 * @since 2018/4/25 9:29
 **/
public enum EastMoneyCate implements NameValue<String, Integer> {

    ZJ("资金", 0){
        @Override
        public Object getObject(){
            return new BankrollDto();
        }
    },
    GF("股份", 1){
        @Override
        public Object getObject(){
            return new StockDto();
        }
    },
    DRWT("当日委托", 2){
        @Override
        public Object getObject(){
            return new SameDayEntrustDto();
        }
    },
    DRCJ("当日成交", 3){
        @Override
        public Object getObject(){
            return new SameDayDealDto();
        }
    },
    KCD("可撤单", 4){
        @Override
        public Object getObject(){
            return new RevocableOrderDto();
        }
    },
    GDDM("股东代码", 5){
        @Override
        public Object getObject(){
            return new StockholderCodeDto();
        }
    },
    RZYE("融资余额", 6){
        @Override
        public Object getObject(){
            return null;
        }
    },
    RQYE("融券余额", 7){
        @Override
        public Object getObject(){
            return null;
        }
    },
    KRZQ("可融证券", 8){
        @Override
        public Object getObject(){
            return null;
        }
    },
    KSGXGCX("可申购新股查询", 12){
        @Override
        public Object getObject(){
            return new CanApplyStockDto();
        }
    },
    XGSGEDCX("新股申购额度查询", 13){
        @Override
        public Object getObject(){
            return new ApplyLimitQueryDto();
        }
    },
    PHCX("配号查询", 14){
        @Override
        public Object getObject(){
            return null;
        }
    },
    ZQCX("中签查询", 15){
        @Override
        public Object getObject(){
            return new LotteryQueryDto();
        }
    };

    private String name;
    private int id;

    EastMoneyCate(String name, int id) {
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

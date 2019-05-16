package com.farmershao.stock.trade.constant;


import com.farmershao.stock.trade.dto.eastmoney.EastMoneyWrapDto;

/**
 * 证券商名称
 *
 * @author: Zhao Da
 * @since: 2018/4/25 21:32
 */
public enum MarketEnum {
    eastrich {
        // 东方证券
        public Object getObject() {
            return new EastMoneyWrapDto();
        }
    },

    ;

    public abstract Object getObject();

}

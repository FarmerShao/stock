package com.farmershao.stock.trade.dto;


import com.farmershao.stock.trade.constant.NameValue;

/**
 * 证券结果包装结果
 *
 * @author: Zhao Da
 * @since: 2018/4/26 11:27
 */
public class WrapResultDto {

    public WrapResultDto(){}

    public String queryData(DataTableDto dataTableDto, NameValue dataCate){
        return null;
    };

    public String sendOrder(){
        return null;
    };

    public String cancelOrder(){
        return null;
    }

    public String getQuote(DataTableDto dt) {
        return null;
    }

    public String getTrandableQuantity(DataTableDto dataTableDto) {
        return null;
    }

    public String repay() {
        return null;
    }

    public String queryHistoryData(DataTableDto dataTableDto, NameValue histDataCate) {
        return null;
    }
}

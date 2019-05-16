package com.farmershao.stock.trade.dto.eastmoney;

import com.alibaba.fastjson.JSONObject;
import com.farmershao.stock.trade.constant.NameValue;
import com.farmershao.stock.trade.dto.DataTableDto;
import com.farmershao.stock.trade.dto.WrapResultDto;
import com.farmershao.stock.trade.dto.eastmoney.history.HistoryDto;
import com.farmershao.stock.trade.dto.eastmoney.trade.TradeBaseDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 东方财富结果
 *
 * @author: Zhao Da
 * @since: 2018/4/26 10:07
 */
public class EastMoneyWrapDto extends WrapResultDto {

    public EastMoneyWrapDto() {}

    public String queryData(DataTableDto dataTableDto, NameValue dataCate){
        int rows = dataTableDto.rows();
        List<TradeBaseDto> lists = new ArrayList<>();
        TradeBaseDto tradeBaseDto = null;
        for (int i=0; i<rows; i++) {
            tradeBaseDto = (TradeBaseDto)dataCate.getObject();
            tradeBaseDto.read(dataTableDto.getRow(i));
            lists.add(tradeBaseDto);
        }
        return JSONObject.toJSON(lists).toString();
    };

    @Override
    public String getQuote(DataTableDto dataTableDto) {
        QuoteDto quote = new QuoteDto();
        if (dataTableDto.rows() == 1) {
            quote.read(dataTableDto.getRow(0));
        }
        return JSONObject.toJSON(quote).toString();
    }

    @Override
    public String getTrandableQuantity(DataTableDto dataTable) {
        List<TradableQuantityDto> lists = new ArrayList<TradableQuantityDto>();
        int rows = dataTable.rows();

        TradableQuantityDto tradableQuantity = new TradableQuantityDto();
        for (int i = 0; i < rows; i++) {
            tradableQuantity.read(dataTable.getRow(i));
            lists.add(tradableQuantity);
        }
        return JSONObject.toJSON(lists).toString();
    }

    @Override
    public String queryHistoryData(DataTableDto dataTableDto, NameValue histDataCate) {
        int rows = dataTableDto.rows();
        List<HistoryDto> lists = new ArrayList<HistoryDto>();
        HistoryDto historyDto = null;

        for (int i = 0; i < rows ; i++) {
            historyDto = (HistoryDto) histDataCate.getObject();
            historyDto.read(dataTableDto.getRow(i));
            lists.add(historyDto);
        }

        return JSONObject.toJSON(lists).toString();
    }


}

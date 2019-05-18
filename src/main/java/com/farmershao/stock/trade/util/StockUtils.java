package com.farmershao.stock.trade.util;

import com.farmershao.stock.trade.enums.ExchangeId;
import org.apache.commons.lang3.StringUtils;


/**
 * Created by kongkp on 2017/1/7.
 */
public class StockUtils {

    public static ExchangeId getExchangeId(String stockCode) {
        String market = getMarket(stockCode);
        if (market.startsWith("SZ.")) {
            return ExchangeId.SZ;
        }
        if (market.startsWith("SH.")) {
            return ExchangeId.SH;
        }
        return null;
    }

    public static String getMarket(String stockCode) {
        if (StringUtils.isNotBlank(stockCode)) {
            if (stockCode.startsWith("000")) {
                return "SZ.A";//深圳A
            }
            if (stockCode.startsWith("200")) {
                return "SZ.B";//深圳B
            }
            if (stockCode.startsWith("002")) {
                return "SZ.ZX";//深圳中小板
            }
            if (stockCode.startsWith("300")) {
                return "SZ.CY";//深圳创业板
            }
            if (stockCode.startsWith("080")) {
                return "SZ.PG";//深圳配股
            }
            if (stockCode.startsWith("031")) {
                return "SZ.QZ";//深圳权证
            }
            if (stockCode.startsWith("600") || stockCode.startsWith("601") || stockCode.startsWith("603")) {
                return "SH.A";//上海A
            }
            if (stockCode.startsWith("900")) {
                return "SH.B";//上海B
            }
            if (stockCode.startsWith("730")) {
                return "SH.SG";//沪市新股申购
            }
            if (stockCode.startsWith("700")) {
                return "SH.PG";//沪市配股
            }
            if (stockCode.startsWith("580")) {
                return "SH.QZ";//沪市权证
            }
        }
        throw new IllegalArgumentException("股票代码(" + stockCode + ")不属于中国大陆证券代码.");
    }
}

package com.farmershao.stock.trade.utils;


import com.farmershao.stock.trade.constant.ExchangeId;
import org.apache.commons.lang3.StringUtils;

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
            if (stockCode.startsWith("000") || "001696".equals(stockCode) || "001896".equals(stockCode) || "001979".equals(stockCode) || "001965".equals(stockCode)) {
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
            if ("000001".equals(stockCode)) {
                return "SH.SZZS";// 上证指数
            }
            if ("399001".equals(stockCode)) {
                return "SZ.SZZH";// 深证指数
            }
            if ("399006".equals(stockCode)) {
                return "SZ.SZCY";// 创业
            }
        }
        throw new IllegalArgumentException("股票代码(" + stockCode + ")不属于中国大陆证券代码.");
    }

    public static String gePlate(String stockCode) {
        if (StringUtils.isNotBlank(stockCode)) {
            if (stockCode.startsWith("000") || "001696".equals(stockCode) || "001896".equals(stockCode) || "001979".equals(stockCode) || "001965".equals(stockCode)) {
                return "1";//深圳A
            }
            if (stockCode.startsWith("200")) {
                return "2";//深圳B
            }
            if (stockCode.startsWith("002")) {
                return "3";//深圳中小板
            }
            if (stockCode.startsWith("300")) {
                return "4";//深圳创业板
            }
            if (stockCode.startsWith("080")) {
                return "5";//深圳配股
            }
            if (stockCode.startsWith("031")) {
                return "6";//深圳权证
            }
            if (stockCode.startsWith("600") || stockCode.startsWith("601") || stockCode.startsWith("603")) {
                return "7";//上海A
            }
            if (stockCode.startsWith("900")) {
                return "8";//上海B
            }
            if (stockCode.startsWith("730")) {
                return "9";//沪市新股申购
            }
            if (stockCode.startsWith("700")) {
                return "10";//沪市配股
            }
            if (stockCode.startsWith("580")) {
                return "11";//沪市权证
            }
        }
        return "12";// 没有所属板块
    }

    public static String getOnlyMarcket(String stockCode) {
        if (StringUtils.isNotBlank(stockCode)) {
            if (stockCode.startsWith("000")||stockCode.startsWith("200")||stockCode.startsWith("002")
                    ||stockCode.startsWith("300")||stockCode.startsWith("080")||stockCode.startsWith("031") || "001696".equals(stockCode) || "001896".equals(stockCode) || "001979".equals(stockCode) || "001965".equals(stockCode)) {
                return "0";//深圳A
            }

            if (stockCode.startsWith("600") || stockCode.startsWith("601") || stockCode.startsWith("603")
                    ||stockCode.startsWith("900")||stockCode.startsWith("730")||stockCode.startsWith("700")
                    ||stockCode.startsWith("580")) {
                return "1";//上海A
            }
        }
        throw new IllegalArgumentException("股票代码(" + stockCode + ")不属于中国大陆证券代码.");
    }

    public static boolean stockCodesFilter(String stockCode) {
        if (StringUtils.isNotBlank(stockCode)) {
            if (stockCode.startsWith("000") || "001696".equals(stockCode) || "001896".equals(stockCode) || "001979".equals(stockCode) || "001965".equals(stockCode)) {
                return true;//深圳A
            }
            if (stockCode.startsWith("002")) {
                return true;//深圳中小板
            }
            if (stockCode.startsWith("300")) {
                return true;//深圳创业板
            }
            if (stockCode.startsWith("080")) {
                return true;//深圳配股
            }
            if (stockCode.startsWith("031")) {
                return true;//深圳权证
            }
            if (stockCode.startsWith("600") || stockCode.startsWith("601") || stockCode.startsWith("603")) {
                return true;//上海A
            }
            if (stockCode.startsWith("730")) {
                return true;//沪市新股申购
            }
            if (stockCode.startsWith("700")) {
                return true;//沪市配股
            }
            if (stockCode.startsWith("580")) {
                return true;//沪市权证
            }
//            if (stockCode.equals("399006")) {
//                return true;
//            }
//            if (stockCode.equals("399001")) {
//                return true;
//            }
        }
        return false;
    }
}

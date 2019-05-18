package com.farmershao.stock.trade.dto;

/**
 * 时间行情
 * @Author chenjz
 */
public class TimeDataDto {

    /**
     * 时间
     */
    private String time;

    /**
     * 开盘价
     */
    private double openPrice;

    /**
     * 收盘价
     */
    private double closePrice;

    /**
     * 最高价
     */
    private double maxPrice;

    /**
     * 最低价
     */
    private double minPrice;

    /**
     * 成交量
     */
    private int dealCount;

    /**
     * 成交额
     */
    private double dealPrice;

    /**
     * 涨家数
     */
    private int riseCount;

    /**
     * 跌家数
     */
    private int fallCount;

    public String getTime() {
        return time;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public int getDealCount() {
        return dealCount;
    }

    public double getDealPrice() {
        return dealPrice;
    }

    public int getRiseCount() {
        return riseCount;
    }

    public int getFallCount() {
        return fallCount;
    }



    public void read(String[] quoteDataRow) {
        if (quoteDataRow != null && quoteDataRow.length == 9) {
        this.time=quoteDataRow[0];
        this.openPrice=Float.valueOf(quoteDataRow[1]);
        this.closePrice=Float.valueOf(quoteDataRow[2]);
        this.maxPrice=Float.valueOf(quoteDataRow[3]);
        this.minPrice=Float.valueOf(quoteDataRow[4]);
        this.dealCount=Integer.valueOf(quoteDataRow[5]);
        this.dealPrice=Float.valueOf(quoteDataRow[6]);
        this.riseCount=Integer.valueOf(quoteDataRow[7]);
        this.fallCount=Integer.valueOf(quoteDataRow[8]);
        }
    }
}

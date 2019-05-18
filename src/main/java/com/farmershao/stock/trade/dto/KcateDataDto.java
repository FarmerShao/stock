package com.farmershao.stock.trade.dto;

/**
 * K线数据
 * @Author chenjz
 */
public class KcateDataDto {

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



    public void read(String[] quoteDataRow) {
        if (quoteDataRow != null && quoteDataRow.length == 7) {
            this.time=quoteDataRow[0];
            this.openPrice=Double.valueOf(quoteDataRow[1]);
            this.closePrice=Double.valueOf(quoteDataRow[2]);
            this.maxPrice=Double.valueOf(quoteDataRow[3]);
            this.minPrice=Double.valueOf(quoteDataRow[4]);
            this.dealCount=Integer.valueOf(quoteDataRow[5]);
            this.dealPrice=Double.valueOf(quoteDataRow[6]);
        }
    }
}

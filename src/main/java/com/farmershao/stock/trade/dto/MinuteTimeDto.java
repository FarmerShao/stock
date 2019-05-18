package com.farmershao.stock.trade.dto;

/**
 * 分时数据
 * @Author chenjz
 */
public class MinuteTimeDto {

    /**
     * 现价
     */
    private double nowPrice;

    /**
     * 成交量
     */
    private int dealCount;

    /**
     * 保留字段
     */
    private String reverse;

    public double getNowPrice() {
        return nowPrice;
    }

    public int getDealCount() {
        return dealCount;
    }

    public String getReverse() {
        return reverse;
    }

    public void read(String[] quoteDataRow) {
        if (quoteDataRow != null && quoteDataRow.length == 3) {
       this.nowPrice=Double.valueOf(quoteDataRow[0]);
       this.dealCount=Integer.valueOf(quoteDataRow[1]);
       this.reverse=quoteDataRow[2];
        }
    }
}

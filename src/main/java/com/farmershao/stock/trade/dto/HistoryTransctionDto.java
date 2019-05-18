package com.farmershao.stock.trade.dto;

/**
 * 历史分笔
 * @Author chenjz
 */
public class HistoryTransctionDto {

    /**
     * 时间
     */
    private String date;

    /**
     * 价格
     */
    private double price;

    /**
     * 现量
     */
    private int nowCount;


    /**
     * 买卖
     */
    private int nature;

    /**
     * 保留
     */
    private String reverse;

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public int getNowCount() {
        return nowCount;
    }


    public int getNature() {
        return nature;
    }

    public String getReverse() {
        return reverse;
    }

    public void read(String[] quoteDataRow) {
        if (quoteDataRow != null && quoteDataRow.length == 5) {
            this.date=quoteDataRow[0];
            this.price=Double.valueOf(quoteDataRow[1]);
            this.nowCount=Integer.valueOf(quoteDataRow[2]);
            this.nature=Integer.valueOf(quoteDataRow[3]);
            this.reverse=quoteDataRow[4];
        }
    }
}

package com.farmershao.stock.trade.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 除权除息 市场 证券代码 日期 保留 送现金  配股价  送股数  配股比例
 * @Author chenjz
 */
public class ExclusionExdividendDto {

    /**
     * 市场
     */
    private int marcketCode;

    /**
     * 证券代码
     */
    private String stockCode;

    /**
     * 日期
     */
    private Date date;

    /**
     * 保留字段
     */
    private String reverse;

    /**
     * 送现金
     */
    private double cashGift;

    /**
     * 配股价
     */
    private double sharesPrice;

    /**
     * 送股数
     */
    private double sharesGiftCount;

    /**
     * 配股比例
     */
    private double sharesPercent;

    public int getMarcketCode() {
        return marcketCode;
    }

    public String getStockCode() {
        return stockCode;
    }

    public Date getDate() {
        return date;
    }

    public String getReverse() {
        return reverse;
    }

    public double getCashGift() {
        return cashGift;
    }

    public double getSharesPrice() {
        return sharesPrice;
    }

    public double getSharesGiftCount() {
        return sharesGiftCount;
    }

    public double getSharesPercent() {
        return sharesPercent;
    }

    public void read(String[] quoteDataRow) throws ParseException {
        if (quoteDataRow != null && quoteDataRow.length == 8) {
            SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
            this.marcketCode=Integer.valueOf(quoteDataRow[0]);
            this.stockCode=quoteDataRow[1];
            this.date=df.parse(quoteDataRow[2]);
            this.reverse=quoteDataRow[3];
            this.cashGift=Double.valueOf(quoteDataRow[4]);
            this.sharesPrice=Double.valueOf(quoteDataRow[5]);
            this.sharesGiftCount=Double.valueOf(quoteDataRow[6]);
            this.sharesPercent=Double.valueOf(quoteDataRow[7]);
        }
    }
}

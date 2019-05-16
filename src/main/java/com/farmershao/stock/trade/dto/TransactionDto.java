package com.farmershao.stock.trade.dto;



/**
 * 分笔数据 时间 价格 现量  笔数  买卖 保留
 * @Author chenjz
 */
public class TransactionDto {
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
     * 笔数
     */
    private int count;

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

    public int getCount() {
        return count;
    }

    public int getNature() {
        return nature;
    }

    public String getReverse() {
        return reverse;
    }

    public void read(String[] quoteDataRow) {
        if (quoteDataRow != null && quoteDataRow.length == 6) {
   this.date=quoteDataRow[0];
   this.price=Double.valueOf(quoteDataRow[1]);
   this.nowCount=Integer.valueOf(quoteDataRow[2]);
   this.count=Integer.valueOf(quoteDataRow[3]);
   this.nature=Integer.valueOf(quoteDataRow[4]);
   this.reverse=quoteDataRow[5];
        }
    }
}

package com.farmershao.stock.trade.dto;

import java.text.ParseException;

/**
 * 证券数目 市场 代码 活跃度 现价 昨收  开盘 最高 最低 保留  保留 总量	现量	总金额	内盘	外盘	保留	保留
 * 买一价	卖一价	买一量	卖一量	买二价	卖二价	买二量	卖二量	买三价	卖三价	买三量	卖三量	买四价	卖四价
 * 买四量	卖四量	买五价	卖五价	买五量	卖五量	保留	保留	保留	保留	保留	涨速	活跃度
 * @Author chenjz
 */
public class StrockInfoDto {

   /**
    * 市场
    */
   private int marcketCode;

   /**
    * 代码
    */
   private String stockCode;

   /**
    * 活跃度
    */
   private String activity;

   /**
    * 现价
    */
   private double nowPrice;

   /**
    * 昨收
    */
   private double ystdIncome;

   /**
    * 开盘
    */
   private double openPrice;

   /**
    * 最高价
    */
   private double maxPrice;

   /**
    * 最低价
    */
   private double minPrice;

   /**
    * 保留字段1
    */
   private String reverse1;

   /**
    * 保留字段2
    */
   private String reverse2;

   /**
    * 总量
    */
   private int sumCount;

   /**
    * 现量
    */
   private int nowCount;

   /**
    * 总金额
    */
   private double sumMoney;

   /**
    * 内盘
    */
   private double inner;

   /**
    * 外盘
    */
   private double outer;

   /**
    * 保留字段3
    */
   private String reverse3;

   /**
    * 保留字段4
    */
   private String reverse4;
   /**
    * 买一
    */
   private double buyOne;

   /**
    * 卖一
    */
   private double sellOne;

   /**
    * 买一量
    */
   private int  buyOneCount;

   /**
    * 卖一量
    */
   private int  sellOneCount;

   /**
    * 买二
    */
   private double buyTwo;

   /**
    * 卖二
    */
   private double sellTwo;

   /**
    * 买二量
    */
   private int  buyTwoCount;

   /**
    * 卖二量
    */
   private int sellTwoCount;


   /**
    * 买三
    */
   private double buyThree;

   /**
    * 卖三
    */
   private double sellThree;

   /**
    * 买三量
    */
   private int  buyThreeCount;

   /**
    * 卖三量
    */
   private int  sellThreeCount;

   /**
    * 买四
    */
   private double buyFour;

   /**
    * 卖四
    */
   private double sellFour;

   /**
    * 买四量
    */
   private int  buyFourCount;

   /**
    * 卖四量
    */
   private int  sellFourCount;

   /**
    * 买五
    */
   private double buyFive;

   /**
    * 卖五
    */
   private double sellFive;

   /**
    * 买五量
    */
   private int  buyFiveCount;

   /**
    * 卖五量
    */
   private int  sellFiveCount;

   /**
    * 保留字段5
    */
   private String reverse5;

   /**
    * 保留字段6
    */
   private String reverse6;

   /**
    * 保留字段7
    */
   private String reverse7;

   /**
    * 保留字段8
    */
   private String reverse8;

   /**
    * 保留字段9
    */
   private String reverse9;

   /**
    * 涨速
    */
   private double riseSpeed;

   /**
    * 活跃度
    */
   private String activity1;

   public int getMarcketCode() {
      return marcketCode;
   }

   public String getStockCode() {
      return stockCode;
   }

   public String getActivity() {
      return activity;
   }

   public double getNowPrice() {
      return nowPrice;
   }

   public double getYstdIncome() {
      return ystdIncome;
   }

   public double getOpenPrice() {
      return openPrice;
   }

   public double getMaxPrice() {
      return maxPrice;
   }

   public double getMinPrice() {
      return minPrice;
   }

   public String getReverse1() {
      return reverse1;
   }

   public String getReverse2() {
      return reverse2;
   }

   public int getSumCount() {
      return sumCount;
   }

   public int getNowCount() {
      return nowCount;
   }

   public double getSumMoney() {
      return sumMoney;
   }

   public double getInner() {
      return inner;
   }

   public double getOuter() {
      return outer;
   }

   public String getReverse3() {
      return reverse3;
   }

   public String getReverse4() {
      return reverse4;
   }

   public double getBuyOne() {
      return buyOne;
   }

   public double getSellOne() {
      return sellOne;
   }

   public int getBuyOneCount() {
      return buyOneCount;
   }

   public int getSellOneCount() {
      return sellOneCount;
   }

   public double getBuyTwo() {
      return buyTwo;
   }

   public double getSellTwo() {
      return sellTwo;
   }

   public int getBuyTwoCount() {
      return buyTwoCount;
   }

   public int getSellTwoCount() {
      return sellTwoCount;
   }

   public double getBuyThree() {
      return buyThree;
   }

   public double getSellThree() {
      return sellThree;
   }

   public int getBuyThreeCount() {
      return buyThreeCount;
   }

   public int getSellThreeCount() {
      return sellThreeCount;
   }

   public double getBuyFour() {
      return buyFour;
   }

   public double getSellFour() {
      return sellFour;
   }

   public int getBuyFourCount() {
      return buyFourCount;
   }

   public int getSellFourCount() {
      return sellFourCount;
   }

   public double getBuyFive() {
      return buyFive;
   }

   public double getSellFive() {
      return sellFive;
   }

   public int getBuyFiveCount() {
      return buyFiveCount;
   }

   public int getSellFiveCount() {
      return sellFiveCount;
   }

   public String getReverse5() {
      return reverse5;
   }

   public String getReverse6() {
      return reverse6;
   }

   public String getReverse7() {
      return reverse7;
   }

   public String getReverse8() {
      return reverse8;
   }

   public String getReverse9() {
      return reverse9;
   }

   public double getRiseSpeed() {
      return riseSpeed;
   }

   public String getActivity1() {
      return activity1;
   }

   public void read(String[] quoteDataRow) throws ParseException {
      if (quoteDataRow != null && quoteDataRow.length == 44) {
      this.marcketCode=Integer.valueOf(quoteDataRow[0]);
         this.stockCode=quoteDataRow[1];
         this.activity=quoteDataRow[2];
         this.nowPrice=Float.valueOf(quoteDataRow[3]);
         this.ystdIncome=Float.valueOf(quoteDataRow[4]);
         this.openPrice=Float.valueOf(quoteDataRow[5]);
         this.maxPrice=Float.valueOf(quoteDataRow[6]);
         this.minPrice=Float.valueOf(quoteDataRow[7]);
         this.reverse1=quoteDataRow[8];
         this.reverse2=quoteDataRow[9];
         this.sumCount=Integer.valueOf(quoteDataRow[10]);
         this.nowCount=Integer.valueOf(quoteDataRow[11]);
         this.sumMoney=Float.valueOf(quoteDataRow[12]);
         this.inner=Float.valueOf(quoteDataRow[13]);
         this.outer=Float.valueOf(quoteDataRow[14]);
         this.reverse3=quoteDataRow[15];
         this.reverse4=quoteDataRow[16];
         this.buyOne=Float.valueOf(quoteDataRow[17]);
         this.sellOne=Float.valueOf(quoteDataRow[18]);
         this.buyOneCount=Integer.valueOf(quoteDataRow[19]);
         this.sellOneCount=Integer.valueOf(quoteDataRow[20]);
         this.buyTwo=Float.valueOf(quoteDataRow[21]);
         this.sellTwo=Float.valueOf(quoteDataRow[22]);
         this.buyTwoCount=Integer.valueOf(quoteDataRow[23]);
         this.sellTwoCount=Integer.valueOf(quoteDataRow[24]);
         this.buyThree=Float.valueOf(quoteDataRow[25]);
         this.sellThree=Float.valueOf(quoteDataRow[26]);
         this.buyThreeCount=Integer.valueOf(quoteDataRow[27]);
         this.sellThreeCount=Integer.valueOf(quoteDataRow[28]);
         this.buyFour=Float.valueOf(quoteDataRow[29]);
         this.sellFour=Float.valueOf(quoteDataRow[30]);
         this.buyFourCount=Integer.valueOf(quoteDataRow[31]);
         this.sellFourCount=Integer.valueOf(quoteDataRow[32]);
         this.buyFive=Float.valueOf(quoteDataRow[33]);
         this.sellFive=Float.valueOf(quoteDataRow[34]);
         this.buyFiveCount=Integer.valueOf(quoteDataRow[35]);
         this.sellFiveCount=Integer.valueOf(quoteDataRow[36]);
         this.reverse5=quoteDataRow[37];
         this.reverse6=quoteDataRow[38];
         this.reverse7=quoteDataRow[39];
         this.reverse8=quoteDataRow[40];
         this.reverse9=quoteDataRow[41];
         this.riseSpeed=Float.valueOf(quoteDataRow[42]);
         this.activity1=quoteDataRow[43];

      }
   }
}

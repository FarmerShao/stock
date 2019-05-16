package com.farmershao.stock.trade.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 五档报价数据
 *
 * 证券数目 市场 代码 活跃度 现价 昨收  开盘 最高 最低 保留  保留 总量	现量	总金额	内盘	外盘	保留	保留
 * 买一价	卖一价	买一量	卖一量	买二价	卖二价	买二量	卖二量	买三价	卖三价	买三量	卖三量	买四价	卖四价
 * 买四量	卖四量	买五价	卖五价	买五量	卖五量	保留	保留	保留	保留	保留	涨速	活跃度
 * @Author chenjz
 */
public class QuoteDto {

   private static final DecimalFormat decimalFormat = new DecimalFormat("+0.00;-0.00");

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
   private String nowPrice;

   /**
    * 昨收
    */
   private String ystdIncome;

   /**
    * 开盘
    */
   private String openPrice;

   /**
    * 最高价
    */
   private String maxPrice;

   /**
    * 最低价
    */
   private String minPrice;

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
   private String sumCount;

   /**
    * 现量
    */
   private String nowCount;

   /**
    * 总金额
    */
   private String sumMoney;

   /**
    * 内盘
    */
   private String inner;

   /**
    * 外盘
    */
   private String outer;

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
   private String buyOne;

   /**
    * 卖一
    */
   private String sellOne;

   /**
    * 买一量
    */
   private String  buyOneCount;

   /**
    * 卖一量
    */
   private String  sellOneCount;

   /**
    * 买二
    */
   private String buyTwo;

   /**
    * 卖二
    */
   private String sellTwo;

   /**
    * 买二量
    */
   private String  buyTwoCount;

   /**
    * 卖二量
    */
   private String sellTwoCount;


   /**
    * 买三
    */
   private String buyThree;

   /**
    * 卖三
    */
   private String sellThree;

   /**
    * 买三量
    */
   private String  buyThreeCount;

   /**
    * 卖三量
    */
   private String  sellThreeCount;

   /**
    * 买四
    */
   private String buyFour;

   /**
    * 卖四
    */
   private String sellFour;

   /**
    * 买四量
    */
   private String  buyFourCount;

   /**
    * 卖四量
    */
   private String  sellFourCount;

   /**
    * 买五
    */
   private String buyFive;

   /**
    * 卖五
    */
   private String sellFive;

   /**
    * 买五量
    */
   private String  buyFiveCount;

   /**
    * 卖五量
    */
   private String  sellFiveCount;

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
   private String riseSpeed;

   /**
    * 活跃度
    */
   private String activity1;

   // 跌涨幅
   private String range;

   // pinyin
   private String pinyin;

   private String suspension;

   public String getPinyin() {
      return pinyin;
   }

   public void setPinyin(String pinyin) {
      this.pinyin = pinyin;
   }

   public int getMarcketCode() {
      return marcketCode;
   }

   public String getStockCode() {
      return stockCode;
   }

   public String getActivity() {
      return activity;
   }

   public String getSuspension() {
      return suspension;
   }

   public static DecimalFormat getDecimalFormat() {
      return decimalFormat;
   }

   public void setMarcketCode(int marcketCode) {
      this.marcketCode = marcketCode;
   }

   public void setStockCode(String stockCode) {
      this.stockCode = stockCode;
   }

   public void setActivity(String activity) {
      this.activity = activity;
   }

   public void setNowPrice(String nowPrice) {
      this.nowPrice = nowPrice;
   }

   public void setYstdIncome(String ystdIncome) {
      this.ystdIncome = ystdIncome;
   }

   public void setOpenPrice(String openPrice) {
      this.openPrice = openPrice;
   }

   public void setMaxPrice(String maxPrice) {
      this.maxPrice = maxPrice;
   }

   public void setMinPrice(String minPrice) {
      this.minPrice = minPrice;
   }

   public void setReverse1(String reverse1) {
      this.reverse1 = reverse1;
   }

   public void setReverse2(String reverse2) {
      this.reverse2 = reverse2;
   }

   public void setSumCount(String sumCount) {
      this.sumCount = sumCount;
   }

   public void setNowCount(String nowCount) {
      this.nowCount = nowCount;
   }

   public void setSumMoney(String sumMoney) {
      this.sumMoney = sumMoney;
   }

   public void setInner(String inner) {
      this.inner = inner;
   }

   public void setOuter(String outer) {
      this.outer = outer;
   }

   public void setReverse3(String reverse3) {
      this.reverse3 = reverse3;
   }

   public void setReverse4(String reverse4) {
      this.reverse4 = reverse4;
   }

   public void setBuyOne(String buyOne) {
      this.buyOne = buyOne;
   }

   public void setSellOne(String sellOne) {
      this.sellOne = sellOne;
   }

   public void setBuyOneCount(String buyOneCount) {
      this.buyOneCount = buyOneCount;
   }

   public void setSellOneCount(String sellOneCount) {
      this.sellOneCount = sellOneCount;
   }

   public void setBuyTwo(String buyTwo) {
      this.buyTwo = buyTwo;
   }

   public void setSellTwo(String sellTwo) {
      this.sellTwo = sellTwo;
   }

   public void setBuyTwoCount(String buyTwoCount) {
      this.buyTwoCount = buyTwoCount;
   }

   public void setSellTwoCount(String sellTwoCount) {
      this.sellTwoCount = sellTwoCount;
   }

   public void setBuyThree(String buyThree) {
      this.buyThree = buyThree;
   }

   public void setSellThree(String sellThree) {
      this.sellThree = sellThree;
   }

   public void setBuyThreeCount(String buyThreeCount) {
      this.buyThreeCount = buyThreeCount;
   }

   public void setSellThreeCount(String sellThreeCount) {
      this.sellThreeCount = sellThreeCount;
   }

   public void setBuyFour(String buyFour) {
      this.buyFour = buyFour;
   }

   public void setSellFour(String sellFour) {
      this.sellFour = sellFour;
   }

   public void setBuyFourCount(String buyFourCount) {
      this.buyFourCount = buyFourCount;
   }

   public void setSellFourCount(String sellFourCount) {
      this.sellFourCount = sellFourCount;
   }

   public void setBuyFive(String buyFive) {
      this.buyFive = buyFive;
   }

   public void setSellFive(String sellFive) {
      this.sellFive = sellFive;
   }

   public void setBuyFiveCount(String buyFiveCount) {
      this.buyFiveCount = buyFiveCount;
   }

   public void setSellFiveCount(String sellFiveCount) {
      this.sellFiveCount = sellFiveCount;
   }

   public void setReverse5(String reverse5) {
      this.reverse5 = reverse5;
   }

   public void setReverse6(String reverse6) {
      this.reverse6 = reverse6;
   }

   public void setReverse7(String reverse7) {
      this.reverse7 = reverse7;
   }

   public void setReverse8(String reverse8) {
      this.reverse8 = reverse8;
   }

   public void setReverse9(String reverse9) {
      this.reverse9 = reverse9;
   }

   public void setRiseSpeed(String riseSpeed) {
      this.riseSpeed = riseSpeed;
   }

   public void setActivity1(String activity1) {
      this.activity1 = activity1;
   }

   public void setRange(String range) {
      this.range = range;
   }

   public void setSuspension(String suspension) {
      this.suspension = suspension;
   }

   public String getNowPrice() {
      return nowPrice;
   }

   public String getYstdIncome() {
      return ystdIncome;
   }

   public String getOpenPrice() {
      return openPrice;
   }

   public String getMaxPrice() {
      return maxPrice;
   }

   public String getMinPrice() {
      return minPrice;
   }

   public String getReverse1() {
      return reverse1;
   }

   public String getReverse2() {
      return reverse2;
   }

   public String getSumCount() {
      return sumCount;
   }

   public String getNowCount() {
      return nowCount;
   }

   public String getSumMoney() {
      return sumMoney;
   }

   public String getInner() {
      return inner;
   }

   public String getOuter() {
      return outer;
   }

   public String getReverse3() {
      return reverse3;
   }

   public String getReverse4() {
      return reverse4;
   }

   public String getBuyOne() {
      return buyOne;
   }

   public String getSellOne() {
      return sellOne;
   }

   public String getBuyOneCount() {
      return buyOneCount;
   }

   public String getSellOneCount() {
      return sellOneCount;
   }

   public String getBuyTwo() {
      return buyTwo;
   }

   public String getSellTwo() {
      return sellTwo;
   }

   public String getBuyTwoCount() {
      return buyTwoCount;
   }

   public String getSellTwoCount() {
      return sellTwoCount;
   }

   public String getBuyThree() {
      return buyThree;
   }

   public String getSellThree() {
      return sellThree;
   }

   public String getBuyThreeCount() {
      return buyThreeCount;
   }

   public String getSellThreeCount() {
      return sellThreeCount;
   }

   public String getBuyFour() {
      return buyFour;
   }

   public String getSellFour() {
      return sellFour;
   }

   public String getBuyFourCount() {
      return buyFourCount;
   }

   public String getSellFourCount() {
      return sellFourCount;
   }

   public String getBuyFive() {
      return buyFive;
   }

   public String getSellFive() {
      return sellFive;
   }

   public String getBuyFiveCount() {
      return buyFiveCount;
   }

   public String getSellFiveCount() {
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

   public String getRiseSpeed() {
      return riseSpeed;
   }

   public String getActivity1() {
      return activity1;
   }

   public String getRange() {
      return range;
   }

   public void read(String[] quoteDataRow) {
      if (quoteDataRow != null && quoteDataRow.length == 44) {
      this.marcketCode=Integer.valueOf(quoteDataRow[0]);
         this.stockCode=quoteDataRow[1];
         this.activity=quoteDataRow[2];
         this.ystdIncome= new BigDecimal(quoteDataRow[4]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.nowPrice= new BigDecimal(quoteDataRow[3]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.openPrice= new BigDecimal(quoteDataRow[5]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.maxPrice= new BigDecimal(quoteDataRow[6]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.minPrice= new BigDecimal(quoteDataRow[7]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.reverse1=quoteDataRow[8];
         this.reverse2=quoteDataRow[9];
         this.sumCount= new BigDecimal(quoteDataRow[10]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.nowCount= new BigDecimal(quoteDataRow[11]).setScale(0,BigDecimal.ROUND_HALF_UP).toString();
         this.sumMoney= new BigDecimal(quoteDataRow[12]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.inner= new BigDecimal(quoteDataRow[13]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.outer= new BigDecimal(quoteDataRow[14]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.reverse3=quoteDataRow[15];
         this.reverse4=quoteDataRow[16];
         this.buyOne= new BigDecimal(quoteDataRow[17]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.sellOne= new BigDecimal(quoteDataRow[18]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.buyOneCount= getCount(quoteDataRow[19]);
         this.sellOneCount= getCount(quoteDataRow[20]);
         this.buyTwo= new BigDecimal(quoteDataRow[21]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.sellTwo= new BigDecimal(quoteDataRow[22]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.buyTwoCount= getCount(quoteDataRow[23]);
         this.sellTwoCount= getCount(quoteDataRow[24]);
         this.buyThree= new BigDecimal(quoteDataRow[25]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.sellThree= new BigDecimal(quoteDataRow[26]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.buyThreeCount= getCount(quoteDataRow[27]);
         this.sellThreeCount= getCount(quoteDataRow[28]);
         this.buyFour= new BigDecimal(quoteDataRow[29]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.sellFour= new BigDecimal(quoteDataRow[30]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.buyFourCount= getCount(quoteDataRow[31]);
         this.sellFourCount= getCount(quoteDataRow[32]);
         this.buyFive= new BigDecimal(quoteDataRow[33]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.sellFive= new BigDecimal(quoteDataRow[34]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.buyFiveCount= getCount(quoteDataRow[35]);
         this.sellFiveCount= getCount(quoteDataRow[36]);
         this.reverse5=quoteDataRow[37];
         this.reverse6=quoteDataRow[38];
         this.reverse7=quoteDataRow[39];
         this.reverse8=quoteDataRow[40];
         this.reverse9=quoteDataRow[41];
         this.riseSpeed=new BigDecimal(quoteDataRow[42]).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
         this.activity1=quoteDataRow[43];
         BigDecimal rangeDecimal = getRange(Double.valueOf(this.nowPrice), Double.valueOf(this.ystdIncome));
         this.range = rangeDecimal.compareTo(BigDecimal.ZERO) ==0 ? rangeDecimal.toString() : decimalFormat.format(rangeDecimal);
      }
   }

   public BigDecimal getRange(double nowPrice, double ystdIncome){

      if (!(nowPrice > 0) || !(ystdIncome > 0)) {
         return new BigDecimal(0.00);
      }

      BigDecimal now = BigDecimal.valueOf(nowPrice);// 现价
      BigDecimal yes = BigDecimal.valueOf(ystdIncome);// 昨收
      BigDecimal sub = now.subtract(yes);// 涨跌
      BigDecimal divide = sub.divide(yes, 4, BigDecimal.ROUND_HALF_UP);

      return divide.multiply(BigDecimal.valueOf(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
   }

   private String getCount(String value) {
       String result = "0";
       BigDecimal buyOneCountDecimal = new BigDecimal(value).setScale(0, BigDecimal.ROUND_HALF_UP);
       if (buyOneCountDecimal.compareTo(new BigDecimal(10000)) != -1) {
           BigDecimal divide = buyOneCountDecimal.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP);
           result = divide.toString() + "万";
       } else {
           result = new BigDecimal(value).setScale(0,BigDecimal.ROUND_HALF_UP).toString();
       }
       return result;
   }

}

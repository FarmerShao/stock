package com.farmershao.stock.trade.model.suport;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * 盤口信息封裝
 * <p>
 * Created by kongkp on 2017/1/7.
 */
public class Quote implements Serializable {
    private static final long serialVersionUID = 1L;

    private String stockCode;
    private String stockName;
    private float lastClosePrice;
    private float openPrice;
    private float nationalDebtRate;
    private float instantPrice;
    private float[] buyPrices = new float[5];
    private int[] buyAmounts = new int[5];
    private float[] sellPrices = new float[5];
    private int[] sellAmounts = new int[5];

    private String stockExchangeCode;
    private int minTradeAmount;
    private float minBuyPriceUnit;
    private float minSellPriceUnit;
    private int accountType;
    private int moneyType;
    private int nationalDebtCode;
    private String extMessage;

    private String[] data = null;

    public void read(String[] quoteDataRow) {
        if (quoteDataRow != null && quoteDataRow.length == 34) {
            this.stockCode = quoteDataRow[0];
            this.stockName = quoteDataRow[1];
            this.lastClosePrice = Float.valueOf(quoteDataRow[2]);
            this.openPrice = Float.valueOf(quoteDataRow[3]);
            this.nationalDebtRate = Float.valueOf(quoteDataRow[4]);
            this.instantPrice = Float.valueOf(quoteDataRow[5]);
            for (int i = 0; i < 5; i++) {
                buyPrices[i] = Float.valueOf(quoteDataRow[6 + i]);
            }
            for (int i = 0; i < 5; i++) {
                buyAmounts[i] = Integer.valueOf(quoteDataRow[11 + i]);
            }
            for (int i = 0; i < 5; i++) {
                sellPrices[i] = Float.valueOf(quoteDataRow[16 + i]);
            }
            for (int i = 0; i < 5; i++) {
                sellAmounts[i] = Integer.valueOf(quoteDataRow[21 + i]);
            }

            this.stockExchangeCode = quoteDataRow[26];
            this.minTradeAmount = Integer.valueOf(quoteDataRow[27]);
            this.minBuyPriceUnit = Float.valueOf(quoteDataRow[28]);
            this.minSellPriceUnit = Float.valueOf(quoteDataRow[29]);
            this.accountType = Integer.valueOf(quoteDataRow[30]);
            this.moneyType = Integer.valueOf(quoteDataRow[31]);
            this.nationalDebtCode = Integer.valueOf(quoteDataRow[32]);
            this.extMessage = quoteDataRow[33];

            this.data = quoteDataRow;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("证券代码\t证券名称\t昨收价\t今开价\t国债利息\t当前价\t买一价\t买二价\t买三价\t买四价\t买五价\t买一量\t买二量\t买三量\t买四量\t买五量\t卖一价\t卖二价\t卖三价\t卖四价\t卖五价\t卖一量\t卖二量\t卖三量\t卖四量\t卖五量\t交易所代码\t最小交易股数\t最小买入变动价位\t最小卖出变动价位\t帐号类别\t币种\t国债标识\t保留信息");
        if (data != null) {
            sb.append("\n");
            sb.append(StringUtils.join(data, "\t"));
        }
        return sb.toString();
    }

    public float getNationalDebtRate() {
        return nationalDebtRate;
    }

    public String getStockCode() {
        return stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public float getLastClosePrice() {
        return lastClosePrice;
    }

    public float getOpenPrice() {
        return openPrice;
    }

    public float getInstantPrice() {
        return instantPrice;
    }

    public float[] getBuyPrices() {
        return buyPrices;
    }

    public float[] getSellPrices() {
        return sellPrices;
    }

    public int[] getBuyAmounts() {
        return buyAmounts;
    }

    public int[] getSellAmounts() {
        return sellAmounts;
    }

    public String getStockExchangeCode() {
        return stockExchangeCode;
    }

    public int getMinTradeAmount() {
        return minTradeAmount;
    }

    public float getMinBuyPriceUnit() {
        return minBuyPriceUnit;
    }

    public float getMinSellPriceUnit() {
        return minSellPriceUnit;
    }

    public int getAccountType() {
        return accountType;
    }

    public int getMoneyType() {
        return moneyType;
    }

    public int getNationalDebtCode() {
        return nationalDebtCode;
    }

    public String getExtMessage() {
        return extMessage;
    }
}

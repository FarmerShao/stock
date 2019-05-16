package com.farmershao.stock.trade.dto.eastmoney;

import org.apache.commons.lang3.StringUtils;

/**
 * 东方财富五档行情实体
 *
 * @author Shao Yu
 * @since 2018/4/25 13:43
 **/
public class QuoteDto {
    // 证券代码
    private String stockCode;
    // 证券名称
    private String stockName;
    // 昨收价
    private double lastClosePrice;
    // 今开价
    private double openPrice;
    // 国债利息
    private double nationalDebtRate;
    // 当前价
    private double instantPrice;
    // 买价（一、二、三）
    private String[] buyPrice123;
    // 买量（一、二、三）
    private String[] buyAmount123;
    // 卖价（一、二、三）
    private String[] sellPrice123;
    // 卖量（一、二、三）
    private String[] sellAmount123;
    // 买价（四、五）
    private String[] buyPrice45;
    // 买量（四、五）
    private String[] buyAmount45;
    // 卖价（四、五）
    private String[] sellPrice45;
    // 卖量（四、五）
    private String[] sellAmount45;
    // 交易所代码
    private String stockExchangeCode;
    // 最小交易股数
    private int minTradeAmount;
    // 最小买入变动价位
    private double minBuyPriceUnit;
    // 最小卖出变动价位
    private double minSellPriceUnit;
    // 帐号类别
    private int accountType;
    // 币种
    private int moneyType;
    // 国债标识
    private int nationalDebtCode;
    // 句柄
    private String handle;
    // 保留信息
    private String extMessage;

    private String[] data = null;

    public void read(String[] quoteDataRow) {
        if (quoteDataRow != null && quoteDataRow.length == 35) {
            this.stockCode = quoteDataRow[0];
            this.stockName = quoteDataRow[1];
            this.lastClosePrice = Double.valueOf(quoteDataRow[2]);
            this.openPrice = Double.valueOf(quoteDataRow[3]);
            this.nationalDebtRate = Double.valueOf(quoteDataRow[4]);
            this.instantPrice = Double.valueOf(quoteDataRow[5]);
            buyPrice123=new String[3];
            for (int i = 0; i < 3; i++) {
                buyPrice123[i] = quoteDataRow[6 + i];
            }
            buyAmount123=new String[3];
            for (int i = 0; i < 3; i++) {
                buyAmount123[i] = quoteDataRow[9 + i];
            }
            sellPrice123=new String[3];
            for (int i = 0; i < 3; i++) {
                sellPrice123[i] = quoteDataRow[12 + i];
            }
            sellAmount123=new String[3];
            for (int i = 0; i < 3; i++) {
                sellAmount123[i] = quoteDataRow[15 + i];
            }
            buyPrice45=new String[3];
            for (int i = 0; i < 2; i++) {
                buyPrice45[i] = quoteDataRow[18 + i];
            }
            buyAmount45=new String[3];
            for (int i = 0; i < 2; i++) {
                buyAmount45[i] = quoteDataRow[20 + i];
            }
            sellPrice45=new String[3];
            for (int i = 0; i < 2; i++) {
                sellPrice45[i] = quoteDataRow[22 + i];
            }
            sellAmount45=new String[3];
            for (int i = 0; i < 2; i++) {
                sellAmount45[i] = quoteDataRow[24 + i];
            }
            this.stockExchangeCode = quoteDataRow[26];
            if (StringUtils.isNotBlank(quoteDataRow[27])) {
                this.minTradeAmount = Integer.valueOf(quoteDataRow[27]);
            }
            if (StringUtils.isNotBlank(quoteDataRow[28])) {
                this.minBuyPriceUnit = Double.valueOf(quoteDataRow[28]);
            }
            if (StringUtils.isNotBlank(quoteDataRow[29])) {
                this.minSellPriceUnit = Double.valueOf(quoteDataRow[29]);
            }
            if (StringUtils.isNotBlank(quoteDataRow[30])) {
                this.accountType = Integer.valueOf(quoteDataRow[30]);
            }
            if (StringUtils.isNotBlank(quoteDataRow[31])) {
                this.moneyType = Integer.valueOf(quoteDataRow[31]);
            }
            if (StringUtils.isNotBlank(quoteDataRow[32])) {
                this.nationalDebtCode = Integer.valueOf(quoteDataRow[32]);
            }
            this.handle = quoteDataRow[33];
            this.extMessage = quoteDataRow[34];

            this.data = quoteDataRow;
        }
    }

    public double getNationalDebtRate() {
        return nationalDebtRate;
    }

    public String getStockCode() {
        return stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public double getLastClosePrice() {
        return lastClosePrice;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public double getInstantPrice() {
        return instantPrice;
    }

    public String[] getBuyPrice123() {
        return buyPrice123;
    }

    public String[] getBuyAmount123() {
        return buyAmount123;
    }

    public String[] getSellPrice123() {
        return sellPrice123;
    }

    public String[] getSellAmount123() {
        return sellAmount123;
    }

    public String[] getBuyPrice45() {
        return buyPrice45;
    }

    public String[] getBuyAmount45() {
        return buyAmount45;
    }

    public String[] getSellPrice45() {
        return sellPrice45;
    }

    public String[] getSellAmount45() {
        return sellAmount45;
    }

    public String getHandle() {
        return handle;
    }

    public String getStockExchangeCode() {
        return stockExchangeCode;
    }

    public int getMinTradeAmount() {
        return minTradeAmount;
    }

    public double getMinBuyPriceUnit() {
        return minBuyPriceUnit;
    }

    public double getMinSellPriceUnit() {
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

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setLastClosePrice(double lastClosePrice) {
        this.lastClosePrice = lastClosePrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public void setNationalDebtRate(double nationalDebtRate) {
        this.nationalDebtRate = nationalDebtRate;
    }

    public void setInstantPrice(double instantPrice) {
        this.instantPrice = instantPrice;
    }

    public void setBuyPrice123(String[] buyPrice123) {
        this.buyPrice123 = buyPrice123;
    }

    public void setBuyAmount123(String[] buyAmount123) {
        this.buyAmount123 = buyAmount123;
    }

    public void setSellPrice123(String[] sellPrice123) {
        this.sellPrice123 = sellPrice123;
    }

    public void setSellAmount123(String[] sellAmount123) {
        this.sellAmount123 = sellAmount123;
    }

    public void setBuyPrice45(String[] buyPrice45) {
        this.buyPrice45 = buyPrice45;
    }

    public void setBuyAmount45(String[] buyAmount45) {
        this.buyAmount45 = buyAmount45;
    }

    public void setSellPrice45(String[] sellPrice45) {
        this.sellPrice45 = sellPrice45;
    }

    public void setSellAmount45(String[] sellAmount45) {
        this.sellAmount45 = sellAmount45;
    }

    public void setStockExchangeCode(String stockExchangeCode) {
        this.stockExchangeCode = stockExchangeCode;
    }

    public void setMinTradeAmount(int minTradeAmount) {
        this.minTradeAmount = minTradeAmount;
    }

    public void setMinBuyPriceUnit(double minBuyPriceUnit) {
        this.minBuyPriceUnit = minBuyPriceUnit;
    }

    public void setMinSellPriceUnit(double minSellPriceUnit) {
        this.minSellPriceUnit = minSellPriceUnit;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public void setMoneyType(int moneyType) {
        this.moneyType = moneyType;
    }

    public void setNationalDebtCode(int nationalDebtCode) {
        this.nationalDebtCode = nationalDebtCode;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setExtMessage(String extMessage) {
        this.extMessage = extMessage;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}

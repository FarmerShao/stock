package com.farmershao.stock.trade.dto.eastmoney.trade;

/**
 * 东方财富当日成交
 *
 * @author Shao Yu
 * @since 2018/4/25 14:05
 **/
public class SameDayDealDto extends TradeBaseDto {
    // 成交日期
    private String dealDate;
    // 成交时间
    private String dealTime;
    // 证券代码
    private String stockCode;
    // 证券名称
    private String stockName;
    // 买卖标志
    private String tradeNature1;
    // 买卖标志
    private String tradeNature2;
    // 业务类型
    private String busiType;
    // 委托价格
    private String entrustPrice;
    // 委托数量
    private String entrustCount;
    // 委托编号
    private String entrustNo;
    // 成交价格
    private double dealPrice;
    // 成交数量
    private double dealCount;
    // 成交金额
    private double dealAmount;
    // 成交编号
    private String dealCode;
    // 股东代码
    private String gddm;
    // 账号类别
    private int accountType;
    // 状态说明
    private String status;
    // 撤单标志
    private String revokeSign;
    // 句柄
    private String handle;
    // 保留信息
    private String reserve;

    private String[] data;

    public SameDayDealDto() {

    }

    @Override
    public void read(String[] dataRow) {
        if (dataRow != null && dataRow.length == 20) {
            this.dealDate = dataRow[0];
            this.dealTime = dataRow[1];
            this.stockCode = dataRow[2];
            this.stockName = dataRow[3];
            this.tradeNature1 = dataRow[4];
            this.tradeNature2 = dataRow[5];
            this.busiType = dataRow[6];
            this.entrustPrice = dataRow[7];
            this.entrustCount = dataRow[8];
            this.entrustNo = dataRow[9];

            if (dataRow[10] != null) {
                this.dealPrice = Double.valueOf(dataRow[10]);
            }
            if (dataRow[11] != null) {
                this.dealCount = Double.valueOf(dataRow[11]);
            }
            if (dataRow[12] != null) {
                this.dealAmount = Double.valueOf(dataRow[12]);
            }
            this.dealCode = dataRow[13];
            this.gddm = dataRow[14];
            if (dataRow[15] != null) {
                this.accountType = Integer.valueOf(dataRow[15]);
            }
            this.status = dataRow[16];
            this.revokeSign = dataRow[17];
            this.handle = dataRow[18];
            this.reserve = dataRow[19];
            data = dataRow;
        }
    }

    public String getStockCode() {
        return stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public String getTradeNature1() {
        return tradeNature1;
    }

    public String getTradeNature2() {
        return tradeNature2;
    }

    public double getDealPrice() {
        return dealPrice;
    }

    public double getDealCount() {
        return dealCount;
    }

    public double getDealAmount() {
        return dealAmount;
    }

    public String getDealCode() {
        return dealCode;
    }

    public String getGddm() {
        return gddm;
    }

    public int getAccountType() {
        return accountType;
    }

    public String getDealDate() {
        return dealDate;
    }

    public String getDealTime() {
        return dealTime;
    }

    public String getBusiType() {
        return busiType;
    }

    public String getEntrustPrice() {
        return entrustPrice;
    }

    public String getEntrustCount() {
        return entrustCount;
    }

    public String getEntrustNo() {
        return entrustNo;
    }

    public String getStatus() {
        return status;
    }

    public String getRevokeSign() {
        return revokeSign;
    }

    public String getHandle() {
        return handle;
    }

    public String getReserve() {
        return reserve;
    }

    public void setDealDate(String dealDate) {
        this.dealDate = dealDate;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setTradeNature1(String tradeNature1) {
        this.tradeNature1 = tradeNature1;
    }

    public void setTradeNature2(String tradeNature2) {
        this.tradeNature2 = tradeNature2;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public void setEntrustPrice(String entrustPrice) {
        this.entrustPrice = entrustPrice;
    }

    public void setEntrustCount(String entrustCount) {
        this.entrustCount = entrustCount;
    }

    public void setEntrustNo(String entrustNo) {
        this.entrustNo = entrustNo;
    }

    public void setDealPrice(double dealPrice) {
        this.dealPrice = dealPrice;
    }

    public void setDealCount(double dealCount) {
        this.dealCount = dealCount;
    }

    public void setDealAmount(double dealAmount) {
        this.dealAmount = dealAmount;
    }

    public void setDealCode(String dealCode) {
        this.dealCode = dealCode;
    }

    public void setGddm(String gddm) {
        this.gddm = gddm;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRevokeSign(String revokeSign) {
        this.revokeSign = revokeSign;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

}

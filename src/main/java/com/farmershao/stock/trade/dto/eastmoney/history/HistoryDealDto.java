package com.farmershao.stock.trade.dto.eastmoney.history;

/**
 * 东方财富历史成交实体
 *
 * @author Shao Yu
 * @since 2018/4/25 13:53
 **/
public class HistoryDealDto extends HistoryDto {
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
    private String dealAmount;
    // 净佣金
    private String netCommission;
    // 交易规费
    private String fee;
    // 成交编号
    private String dealCode;
    // 股东代码
    private String gddm;
    // 账号类别
    private int accountType;
    // 句柄
    private String handle;
    // 保留信息
    private String reserve;

    private String[] data = null;

    public HistoryDealDto() {

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
            this.entrustNo =  dataRow[9];
            if (dataRow[10] != null) {
                this.dealPrice = Double.valueOf(dataRow[10]);
            }
            if (dataRow[11] != null) {
                this.dealCount = Double.valueOf(dataRow[11]);
            }
            this.dealAmount = dataRow[12];
            this.netCommission = dataRow[13];
            this.fee = dataRow[14];
            this.dealCode = dataRow[15];
            this.gddm = dataRow[16];
            if (dataRow[17] != null) {
                this.accountType = Integer.valueOf(dataRow[17]);
            }
            this.handle = dataRow[18];
            this.reserve = dataRow[19];
            data = dataRow;
        }
    }

    public String getDealDate() {
        return dealDate;
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

    public String getDealCode() {
        return dealCode;
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

    public String getDealAmount() {
        return dealAmount;
    }

    public String getNetCommission() {
        return netCommission;
    }

    public String getFee() {
        return fee;
    }

    public String getHandle() {
        return handle;
    }

    public String getGddm() {
        return gddm;
    }

    public int getAccountType() {
        return accountType;
    }

    public String getReserve() {
        return reserve;
    }
}

package com.farmershao.stock.trade.dto.eastmoney.trade;

/**
 * 东方财富交易数据-中签查询方式查询实体
 *
 * @author Shao Yu
 * @since 2018/4/25 14:13
 **/
public class LotteryQueryDto extends TradeBaseDto {
    // 证券代码
    private String stockCode;
    // 证券名称
    private String stockName;
    // 委托日期
    private String entrustDate;
    // 中签日期
    private String winDate;
    // 成交数量
    private String dealCount;
    // 委托价格
    private String entrustPrice;
    // 中签金额
    private String winAmount;
    // 缴款数量
    private String paymentCount;
    // 缴款金额
    private String pamentAmount;
    // 放弃数量
    private String giveUpCount;
    // 缴款顺序
    private String paymentOrder;
    // 发行方式
    private String issueMode;
    // 状态说明
    private String status;
    // 股东代码
    private String ShareholderCode;
    // 帐号类别
    private String accountType;
    // 交易所名称
    private String bourseName;
    // 句柄
    private String handle;
    // 保留信息
    private String reserve;

    private String[] data;

    public LotteryQueryDto(){}

    @Override
    public void read(String[] dataRow) {
        if (dataRow != null && dataRow.length == 18) {
            this.stockCode = dataRow[0];
            this.stockName = dataRow[1];
            this.entrustDate = dataRow[2];
            this.winDate = dataRow[3];
            this.dealCount = dataRow[4];
            this.entrustPrice = dataRow[5];
            this.winAmount = dataRow[6];
            this.paymentCount = dataRow[7];
            this.pamentAmount = dataRow[8];
            this.giveUpCount = dataRow[9];
            this.paymentOrder = dataRow[10];
            this.issueMode = dataRow[11];
            this.status = dataRow[12];
            this.ShareholderCode = dataRow[13];
            this.accountType = dataRow[14];
            this.bourseName = dataRow[15];
            this.handle = dataRow[16];
            this.reserve = dataRow[17];
            data = dataRow;
        }
    }


    public String getStockCode() {
        return stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public String getEntrustDate() {
        return entrustDate;
    }

    public String getWinDate() {
        return winDate;
    }

    public String getDealCount() {
        return dealCount;
    }

    public String getEntrustPrice() {
        return entrustPrice;
    }

    public String getWinAmount() {
        return winAmount;
    }

    public String getPaymentCount() {
        return paymentCount;
    }

    public String getPamentAmount() {
        return pamentAmount;
    }

    public String getGiveUpCount() {
        return giveUpCount;
    }

    public String getPaymentOrder() {
        return paymentOrder;
    }

    public String getIssueMode() {
        return issueMode;
    }

    public String getStatus() {
        return status;
    }

    public String getShareholderCode() {
        return ShareholderCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getBourseName() {
        return bourseName;
    }

    public String getHandle() {
        return handle;
    }

    public String getReserve() {
        return reserve;
    }
}

package com.farmershao.stock.trade.dto.eastmoney.trade;

/**
 * 东方财富交易数据-可申购新股查询方式查询实体
 *
 * @author Shao Yu
 * @since 2018/4/25 14:08
 **/
public class CanApplyStockDto extends TradeBaseDto {
    // 证券代码
    private String stockCode;
    // 证券名称
    private String stockName;
    // 发行价格
    private String issuePrice;
    // 最低数量
    private String purchaseLower;
    // 最高数量
    private String purchaseupper;
    // 参数
    private String param;
    // 委托日期
    private String commissionDate;
    // 申购方式
    private String applyMode;
    // 帐号类别
    private String accountType;
    // 交易所名称
    private String marketName;
    // 句柄
    private String handle;
    // 保留信息
    private String reserve;

    private String[] data = null;

    public CanApplyStockDto() {

    }

    @Override
    public void read(String[] dataRow) {
        if (dataRow != null && dataRow.length == 12) {
            this.stockCode = dataRow[0];
            this.stockName = dataRow[1];
            this.issuePrice = dataRow[2];
            this.purchaseLower = dataRow[3];
            this.purchaseupper = dataRow[4];
            this.param = dataRow[5];
            this.commissionDate = dataRow[6];
            this.applyMode = dataRow[7];
            this.accountType = dataRow[8];
            this.marketName = dataRow[9];
            this.handle = dataRow[10];
            this.reserve = dataRow[11];
            data = dataRow;
        }
    }

    public String getStockCode() {
        return stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public String getParam() {
        return param;
    }

    public String getPurchaseLower() {
        return purchaseLower;
    }

    public String getPurchaseupper() {
        return purchaseupper;
    }

    public String getMarketName() {
        return marketName;
    }

    public String getCommissionDate() {
        return commissionDate;
    }

    public String getIssuePrice() {
        return issuePrice;
    }

    public String getApplyMode() {
        return applyMode;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getHandle() {
        return handle;
    }

    public String getReserve() {
        return reserve;
    }
}

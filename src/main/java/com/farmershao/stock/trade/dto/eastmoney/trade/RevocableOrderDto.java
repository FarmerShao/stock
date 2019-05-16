package com.farmershao.stock.trade.dto.eastmoney.trade;

/**
 * 东方财富交易数据-可撤单方式查询实体
 *
 * @author Shao Yu
 * @since 2018/4/25 14:08
 **/
public class RevocableOrderDto extends TradeBaseDto {
    // 委托日期
    private String commissionDate;
    // 委托时间
    private String commissionTime;
    // 证券代码
    private String stockCode;
    // 证券名称
    private String stockName;
    // 状态说明
    private String statusMes;
    // 买卖标志
    private String tradeNature1;
    // 买卖标志
    private String tradeNature2;
    // 委托价格
    private double commissionPrice;
    // 委托数量
    private double commissionCount;
    // 委托编号
    private String commissionCode;
    // 成交数量
    private double dealCount;
    // 撤单数量
    private double revokeCount;
    // 股东代码
    private String gddm;
    // 账号类别
    private int accountType;
    // 句柄
    private String handle;
    // 保留信息
    private String reserve;

    private String[] data = null;

    public RevocableOrderDto() {

    }

    @Override
    public void read(String[] dataRow) {
        if (dataRow != null && dataRow.length == 16) {
            this.commissionDate = dataRow[0];
            this.commissionTime = dataRow[1];
            this.stockCode = dataRow[2];
            this.stockName = dataRow[3];
            this.statusMes = dataRow[4];
            this.tradeNature1 = dataRow[5];
            this.tradeNature2 = dataRow[6];
            if (dataRow[7] != null) {
                this.commissionPrice = Double.valueOf(dataRow[7]);
            }
            if (dataRow[8] != null) {
                this.commissionCount = Double.valueOf(dataRow[8]);
            }
            this.commissionCode = dataRow[9];
            if (dataRow[10] != null) {
                this.dealCount = Double.valueOf(dataRow[10]);
            }
            if (dataRow[11] != null) {
                this.revokeCount = Double.valueOf(dataRow[11]);
            }
            this.gddm = dataRow[12];
            if (dataRow[13] != null) {
                this.accountType = Integer.valueOf(dataRow[13]);
            }
            this.handle = dataRow[14];
            this.reserve = dataRow[15];
            data = dataRow;
        }
    }

    public String getCommissionDate() {
        return commissionDate;
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

    public String getStatusMes() {
        return statusMes;
    }

    public double getCommissionPrice() {
        return commissionPrice;
    }

    public double getCommissionCount() {
        return commissionCount;
    }

    public String getCommissionCode() {
        return commissionCode;
    }

    public double getDealCount() {
        return dealCount;
    }

    public String getGddm() {
        return gddm;
    }

    public int getAccountType() {
        return accountType;
    }

    public String getCommissionTime() {
        return commissionTime;
    }

    public double getRevokeCount() {
        return revokeCount;
    }

    public String getHandle() {
        return handle;
    }

    public String getReserve() {
        return reserve;
    }
}

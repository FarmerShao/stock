package com.farmershao.stock.trade.dto.eastmoney.trade;

/**
 * 东方财富当日委托
 *
 * @author Shao Yu
 * @since 2018/4/25 14:05
 **/
public class SameDayEntrustDto extends TradeBaseDto {
    // 委托日期
    private String commissionDate;
    // 委托时间
    private String commissionTime;
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
    private double commissionPrice;
    // 委托数量
    private double commissionCount;
    // 委托编号
    private String commissionCode;
    // 成交数量
    private double dealCount;
    // 成交金额
    private double dealPrice;
    // 撤单数量
    private double revokeCount;
    // 状态说明
    private String statusMes;
    // 撤单标志
    private String revokeSign;
    // 股东代码
    private String gddm;
    // 帐号类别
    private int accountType;
    // 操作日期
    private String operationDate;
    // 句柄
    private String handle;
    // 保留信息
    private String reserve;

    private String[] data = null;

    public SameDayEntrustDto() {

    }

    @Override
    public void read(String[] dataRow) {
        if (dataRow != null && dataRow.length == 20) {
            this.commissionDate = dataRow[0];
            this.commissionTime = dataRow[1];
            this.stockCode = dataRow[2];
            this.stockName = dataRow[3];
            this.tradeNature1 = dataRow[4];
            this.tradeNature2 = dataRow[5];
            this.busiType = dataRow[6];
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
                this.dealPrice = Double.valueOf(dataRow[11]);
            }
            if (dataRow[12] != null) {
                this.revokeCount = Double.valueOf(dataRow[12]);
            }
            this.statusMes = dataRow[13];
            this.revokeSign = dataRow[14];
            this.gddm = dataRow[15];
            if (dataRow[16] != null) {
                this.accountType = Integer.valueOf(dataRow[16]);
            }
            this.operationDate = dataRow[17];
            this.handle = dataRow[18];
            this.reserve = dataRow[19];
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

    public double getDealPrice() {
        return dealPrice;
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

    public String getReserve() {
        return reserve;
    }

    public String getCommissionTime() {
        return commissionTime;
    }

    public String getBusiType() {
        return busiType;
    }

    public double getRevokeCount() {
        return revokeCount;
    }

    public String getRevokeSign() {
        return revokeSign;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public String getHandle() {
        return handle;
    }
}

package com.farmershao.stock.trade.dto.eastmoney.history;

/**
 * 东方财富历史委托实体
 *
 * @author Shao Yu
 * @since 2018/4/25 13:54
 **/
public class HistoryEntrustDto extends HistoryDto {
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
    private String revokeCount;
    // 状态说明
    private String statusMes;
    // 股东代码
    private String gddm;
    // 账号类别
    private int accountType;
    // 操作日期
    private String operationDate;
    // 句柄
    private String handle;
    // 保留信息
    private String reserve;

    private String[] data = null;

    public HistoryEntrustDto() {

    }

    @Override
    public void read(String[] dataRow) {
        if (dataRow != null && dataRow.length == 19) {
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
            this.revokeCount = dataRow[12];
            this.statusMes = dataRow[13];
            this.gddm = dataRow[14];
            if (dataRow[15] != null) {
                this.accountType = Integer.valueOf(dataRow[15]);
            }
            this.operationDate = dataRow[16];
            this.handle = dataRow[17];
            this.reserve = dataRow[18];
            data = dataRow;
        }
    }

    public String getCommissionDate() {
        return commissionDate;
    }

    public String getCommissionTime() {
        return commissionTime;
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

    public String getBusiType() {
        return busiType;
    }

    public String getRevokeCount() {
        return revokeCount;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public String getHandle() {
        return handle;
    }

    public String getReserve() {
        return reserve;
    }
}

package com.farmershao.stock.trade.dto.eastmoney.trade;

import org.apache.commons.lang3.StringUtils;

/**
 * 东方财富交易数据-资金方式查询实体
 *
 * @author Shao Yu
 * @since 2018/4/25 14:12
 **/
public class BankrollDto extends TradeBaseDto {
    // 币种
    private int moneyType;
    // 资金余额
    private double capitalBalance;
    // 可用资金
    private double usableCapital;
    // 冻结资金
    private double freezeCapital;
    // 最新市值
    private double latestMarketValue;
    // 总资产
    private double totalCapital;
    // 浮动盈亏
    private String floatPL;
    // 操作标志
    private String operationSign;
    // 可取资金
    private double drawableCapital;
    // 融券卖出资金
    private double sellOutBankroll;
    // 取柜台可买数量
    private double canSellCount;
    // 句柄
    private String handle;
    // 保留信息
    private String reserve;

    private String[] data = null;

    public BankrollDto() {

    }

    @Override
    public void read(String[] dataRow) {
        if (dataRow != null && dataRow.length == 13) {
            if (dataRow[0] != null) {
                this.moneyType = Integer.valueOf(dataRow[0]);
            }
            if (StringUtils.isNotBlank(dataRow[1])) {
                this.capitalBalance = Double.valueOf(dataRow[1]);
            }
            if (StringUtils.isNotBlank(dataRow[2])) {
                this.usableCapital = Double.valueOf(dataRow[2]);
            }
            if (StringUtils.isNotBlank(dataRow[3])) {
                this.freezeCapital = Double.valueOf(dataRow[3]);
            }
            if (StringUtils.isNotBlank(dataRow[4])) {
                this.latestMarketValue = Double.valueOf(dataRow[4]);
            }
            if (StringUtils.isNotBlank(dataRow[5])) {
                this.totalCapital = Double.valueOf(dataRow[5]);
            }
            this.floatPL = dataRow[6];
            this.operationSign = dataRow[7];
            if (StringUtils.isNotBlank(dataRow[8])) {
                this.drawableCapital = Double.valueOf(dataRow[8]);
            }
            if (StringUtils.isNotBlank(dataRow[9])) {
                this.sellOutBankroll = Double.valueOf(dataRow[9]);
            }
            if (StringUtils.isNotBlank(dataRow[10])) {
                this.canSellCount = Double.valueOf(dataRow[10]);
            }
            this.handle = dataRow[11];
            this.reserve = dataRow[12];
            this.data = dataRow;
        }
    }

    public int getMoneyType() {
        return moneyType;
    }

    public double getCapitalBalance() {
        return capitalBalance;
    }

    public double getUsableCapital() {
        return usableCapital;
    }

    public double getFreezeCapital() {
        return freezeCapital;
    }

    public double getTotalCapital() {
        return totalCapital;
    }

    public double getLatestMarketValue() {
        return latestMarketValue;
    }

    public String getFloatPL() {
        return floatPL;
    }

    public String getOperationSign() {
        return operationSign;
    }

    public double getDrawableCapital() {
        return drawableCapital;
    }

    public double getSellOutBankroll() {
        return sellOutBankroll;
    }

    public double getCanSellCount() {
        return canSellCount;
    }

    public String getHandle() {
        return handle;
    }

    public String getReserve() {
        return reserve;
    }
}

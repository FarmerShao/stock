package com.farmershao.stock.trade.dto.eastmoney.trade;

/**
 * 东方财富交易数据-股份方式查询实体
 *
 * @author Shao Yu
 * @since 2018/4/25 14:07
 **/
public class StockDto extends TradeBaseDto {
    // 证券代码
    private String stockCode;
    // 证券名称
    private String stockName;
    // 证券数量
    private double stockCount;
    // 库存数量
    private double inventoryCount;
    // 可卖数量
    private double sellableCount;
    // 成本价
    private double costPrice;
    // 盈亏成本价
    private double plCost;
    // 当前价
    private double currentPrice;
    // 最新市值
    private double latestMarketValue;
    // 浮动盈亏
    private double floatProfitLoss;
    // 盈亏比例（%）
    private double profitLoss;
    // 股东代码
    private String gddm;
    // 账号类别
    private int accountType;
    // 交易所代码
    private int marketId;
    // 句柄
    private String handle;
    // 保留信息
    private String reserve;

    private String[] data = null;

    @Override
    public void read(String[] dataRow) {
        if (dataRow != null && dataRow.length == 16) {
            this.stockCode = dataRow[0];
            this.stockName = dataRow[1];
            if (dataRow[2] != null) {
                this.stockCount = Double.valueOf(dataRow[2]);
            }
            if (dataRow[3] != null) {
                this.inventoryCount = Double.valueOf(dataRow[3]);
            }
            if (dataRow[4] != null) {
                this.sellableCount = Double.valueOf(dataRow[4]);
            }
            if (dataRow[5] != null) {
                this.costPrice = Double.valueOf(dataRow[5]);
            }
            if (dataRow[6] != null) {
                this.plCost = Double.valueOf(dataRow[6]);
            }
            if (dataRow[7] != null) {
                this.currentPrice = Double.valueOf(dataRow[7]);
            }
            if (dataRow[8] != null) {
                this.latestMarketValue = Double.valueOf(dataRow[8]);
            }
            if (dataRow[9] != null) {
                this.floatProfitLoss = Double.valueOf(dataRow[9]);
            }
            if (dataRow[10] != null) {
                this.profitLoss = Double.valueOf(dataRow[10]);
            }
            if (dataRow[11] != null) {
                this.gddm = dataRow[11];
            }
            if (dataRow[12] != null) {
                this.accountType = Integer.valueOf(dataRow[12]);
            }
            if (dataRow[13] != null) {
                this.marketId = Integer.valueOf(dataRow[13]);
            }
            this.handle = dataRow[14];
            this.reserve = dataRow[15];
            this.data = dataRow;
        }
    }

    public String getStockCode() {
        return stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public double getStockCount() {
        return stockCount;
    }

    public double getSellableCount() {
        return sellableCount;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getLatestMarketValue() {
        return latestMarketValue;
    }

    public double getFloatProfitLoss() {
        return floatProfitLoss;
    }

    public double getProfitLoss() {
        return profitLoss;
    }

    public String getGddm() {
        return gddm;
    }

    public int getAccountType() {
        return accountType;
    }

    public int getMarketId() {
        return marketId;
    }

    public String getReserve() {
        return reserve;
    }

    public double getInventoryCount() {
        return inventoryCount;
    }

    public double getPlCost() {
        return plCost;
    }

    public String getHandle() {
        return handle;
    }

}

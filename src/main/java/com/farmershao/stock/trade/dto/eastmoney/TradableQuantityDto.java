package com.farmershao.stock.trade.dto.eastmoney;

/**
 * 东方财富查询当前可交易股票数量（T+1 交易，当前可卖出的股票数量）
 *
 * @author Shao Yu
 * @since 2018/4/25 13:48
 **/
public class TradableQuantityDto {
    // 可卖数量
    private String count;
    // 检查风险标志
    private String ristMark;
    // 返回信息
    private String returnInfo;
    // 可用资金
    private String availableBankroll;
    // 句柄
    private String handle;
    // 保留信息
    private String reserve;

    private String[] data = null;

    public TradableQuantityDto() {}

    public void read(String[] quoteDataRow) {
        if (quoteDataRow != null && quoteDataRow.length == 6) {
            this.count = quoteDataRow[0];
            this.ristMark = quoteDataRow[1];
            this.returnInfo = quoteDataRow[2];
            this.availableBankroll = quoteDataRow[3];
            this.handle = quoteDataRow[4];
            this.reserve = quoteDataRow[5];
            this.data = quoteDataRow;
        }
    }

    public String getCount() {
        return count;
    }

    public String getRistMark() {
        return ristMark;
    }

    public String getReturnInfo() {
        return returnInfo;
    }

    public String getAvailableBankroll() {
        return availableBankroll;
    }

    public String getHandle() {
        return handle;
    }

    public String getReserve() {
        return reserve;
    }
}

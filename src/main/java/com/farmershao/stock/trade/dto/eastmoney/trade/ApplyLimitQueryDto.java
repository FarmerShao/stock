package com.farmershao.stock.trade.dto.eastmoney.trade;

/**
 * 东方财富交易数据-新股申购额度查询方式查询实体
 *
 * @author Shao Yu
 * @since 2018/4/25 14:11
 **/
public class ApplyLimitQueryDto extends TradeBaseDto {
    // 客户市值额度
    private double marketValueLimit;
    // 参数
    private String param;
    // 股东代码
    private String shareholderCode;
    // 帐号类别
    private int accountType;
    // 交易所名称
    private String marketName;
    // 句柄
    private String handle;
    // 保留信息
    private String reserve;

    private String[] data;

    public ApplyLimitQueryDto() {}

    @Override
    public void read(String[] dataRow) {
        if (dataRow != null && dataRow.length == 7) {
            if (dataRow[0] != null) {
                this.marketValueLimit = Double.valueOf(dataRow[0]);
            }
            this.param = dataRow[1];
            this.shareholderCode = dataRow[2];
            if (dataRow[3] != null) {
                this.accountType = Integer.valueOf(dataRow[3]);
            }
            this.marketName = dataRow[4];
            this.handle = dataRow[5];
            this.reserve = dataRow[6];
            data = dataRow;
        }
    }

    public String getParam() {
        return param;
    }

    public int getAccountType() {
        return accountType;
    }

    public String getMarketName() {
        return marketName;
    }

    public double getMarketValueLimit() {
        return marketValueLimit;
    }

    public String getShareholderCode() {
        return shareholderCode;
    }

    public String getHandle() {
        return handle;
    }

    public String getReserve() {
        return reserve;
    }
}

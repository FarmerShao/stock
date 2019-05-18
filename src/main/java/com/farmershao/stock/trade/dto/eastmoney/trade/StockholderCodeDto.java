package com.farmershao.stock.trade.dto.eastmoney.trade;

/**
 * 东方财富交易数据-股东代码方式查询实体
 *
 * @author Shao Yu
 * @since 2018/4/25 14:06
 **/
public class StockholderCodeDto extends TradeBaseDto {
    // 股东代码
    private String gddm;
    // 股东名称
    private String gdName;
    // 账号类别
    private String accountType;
    // 融资融券标识
    private String financingNature;
    // 句柄
    private String handle;
    // 保留信息
    private String reserve;

    private String[] data;

    public StockholderCodeDto() {

    }

    @Override
    public void read(String[] dataRow) {
        if (dataRow != null && dataRow.length == 6) {
            this.gddm = dataRow[0];
            this.gdName = dataRow[1];
            this.accountType = dataRow[2];
            this.financingNature = dataRow[3];
            this.handle = dataRow[4];
            this.reserve = dataRow[5];
            data = dataRow;
        }
    }

    public String getGddm() {
        return gddm;
    }

    public String getGdName() {
        return gdName;
    }

    public String getFinancingNature() {
        return financingNature;
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

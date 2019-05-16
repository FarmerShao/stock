package com.farmershao.stock.trade.dto;


import org.apache.commons.lang3.StringUtils;

/***
 * 普通行情服务器连接返回信息
 */
public class HqConnectInfo {
    // 服务器名称
    private String serverName;
    // 最后交易日期(YYYYMMDD)
    private String lastDate;
    // 接口过期日期
    private long expireDate;

    private String[] data;

    public HqConnectInfo() {

    }

    public void read(String[] quoteDataRow) {
        if (quoteDataRow != null && quoteDataRow.length == 3) {
            this.serverName = quoteDataRow[0];
            this.lastDate = quoteDataRow[1];
            if (quoteDataRow[2] != null) {
                this.expireDate = Long.valueOf(quoteDataRow[2]);
            }
            this.data = quoteDataRow;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("服务器名称\t最后交易日期\t接口过期日期");
        if (data != null) {
            sb.append("\n");
            sb.append(StringUtils.join(data, "\t"));
        }
        return sb.toString();
    }

    public String getServerName() {
        return serverName;
    }

    public String getLastDate() {
        return lastDate;
    }

    public long getExpireDate() {
        return expireDate;
    }
}

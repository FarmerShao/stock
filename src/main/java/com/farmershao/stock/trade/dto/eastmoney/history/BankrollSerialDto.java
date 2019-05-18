package com.farmershao.stock.trade.dto.eastmoney.history;

/**
 * 东方财富资金流水实体
 *
 * @author Shao Yu
 * @since 2018/4/25 13:55
 **/
public class BankrollSerialDto extends HistoryDto {
    // 成交日期
    private String dealDate;
    // 业务名称
    private String busiType;
    // 证券代码
    private String stockCode;
    // 证券名称
    private String stockName;
    // 成交价格
    private String dealPrice;
    // 成交数量
    private String dealCount;
    // 剩余数量
    private String restCount;
    // 成交金额
    private String dealAmount;
    // 清算金额
    private String clearPricre;
    // 剩余金额
    private String restPrice;
    // 佣金
    private String commission;
    // 证管费
    private String certificateFee;
    // 经手费
    private String handleFee;
    // 交易规费
    private String fee;
    // 印花税
    private String stampDuty;
    // 过户费
    private String transferFee;
    // 结算费
    private String balanceFee;
    // 附加费
    private String annexFee;
    // 成交编号
    private String dealNo;
    // 股东代码
    private String shareholderCode;
    // 帐号类别
    private String accountType;
    // 币种
    private String moneyType;
    // 句柄
    private String handle;
    // 保留信息
    private String reserve;

    private String[] data = null;

    public BankrollSerialDto() {}

    @Override
    public void read(String[] dataRow) {
        if (dataRow != null && dataRow.length == 24) {
            this.dealDate = dataRow[0];
            this.busiType = dataRow[1];
            this.stockCode = dataRow[2];
            this.stockName = dataRow[3];
            this.dealPrice = dataRow[4];
            this.dealCount = dataRow[5];
            this.restCount = dataRow[6];
            this.dealAmount = dataRow[7];
            this.clearPricre = dataRow[8];
            this.restPrice = dataRow[9];
            this.commission = dataRow[10];
            this.certificateFee = dataRow[11];
            this.handleFee = dataRow[12];
            this.fee = dataRow[13];
            this.stampDuty = dataRow[14];
            this.transferFee = dataRow[15];
            this.balanceFee = dataRow[16];
            this.annexFee = dataRow[17];
            this.dealNo = dataRow[18];
            this.shareholderCode = dataRow[19];
            this.accountType = dataRow[20];
            this.moneyType = dataRow[21];
            this.handle = dataRow[22];
            this.reserve = dataRow[23];
            data = dataRow;
        }
    }

    public String getDealDate() {
        return dealDate;
    }

    public String getBusiType() {
        return busiType;
    }

    public String getStockCode() {
        return stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public String getDealPrice() {
        return dealPrice;
    }

    public String getDealCount() {
        return dealCount;
    }

    public String getRestCount() {
        return restCount;
    }

    public String getDealAmount() {
        return dealAmount;
    }

    public String getClearPricre() {
        return clearPricre;
    }

    public String getRestPrice() {
        return restPrice;
    }

    public String getCommission() {
        return commission;
    }

    public String getCertificateFee() {
        return certificateFee;
    }

    public String getHandleFee() {
        return handleFee;
    }

    public String getFee() {
        return fee;
    }

    public String getStampDuty() {
        return stampDuty;
    }

    public String getTransferFee() {
        return transferFee;
    }

    public String getBalanceFee() {
        return balanceFee;
    }

    public String getAnnexFee() {
        return annexFee;
    }

    public String getDealNo() {
        return dealNo;
    }

    public String getShareholderCode() {
        return shareholderCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public String getHandle() {
        return handle;
    }

    public String getReserve() {
        return reserve;
    }

}

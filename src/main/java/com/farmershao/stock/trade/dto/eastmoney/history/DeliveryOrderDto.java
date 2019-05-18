package com.farmershao.stock.trade.dto.eastmoney.history;

/**
 * 东方财富交割单类型历史交易数据
 *
 * @author Shao Yu
 * @since 2018/4/25 13:52
 **/
public class DeliveryOrderDto extends HistoryDto {
    // 成交日期
    private String date;
    // 业务名称
    private String busiName;
    // 证券代码
    private String stockCode;
    // 证券名称
    private String stockName;
    // 成交价格
    private String dealPrice;
    // 成交数量
    private String dealCount;
    // 剩余数量
    private double restCount;
    // 成交金额
    private double dealAmount;
    // 清算金额
    private double clearPricre;
    // 剩余金额
    private double restPrice;
    // 佣金
    private double commission;
    // 证管费
    private double certificateFee;
    // 经手费
    private double handleFee;
    // 交易规费
    private double fee;
    // 印花税
    private String stampDuty;
    // 过户费
    private String transferFee;
    // 结算费
    private double balanceFee;
    // 附加费
    private String annexFee;
    // 币种
    private String moneyType;
    // 成交编号
    private String dealNo;
    // 股东代码
    private String shareholderCode;
    // 帐号类别
    private int accountType;
    // 句柄
    private String handle;
    // 保留信息
    private String reserve;

    private String[] data = null;

    public DeliveryOrderDto() {}

    @Override
    public void read(String[] dataRow) {
        if (dataRow != null && dataRow.length == 24) {
            this.date = dataRow[0];
            this.busiName = dataRow[1];
            this.stockCode = dataRow[2];
            this.stockName = dataRow[3];
            this.dealPrice = dataRow[4];
            this.dealCount = dataRow[5];
            if (dataRow[6] != null) {
                this.restCount = Double.valueOf(dataRow[6]);
            }
            if (dataRow[7] != null) {
                this.dealAmount = Double.valueOf(dataRow[7]);
            }
            if (dataRow[8] != null) {
                this.clearPricre = Double.valueOf(dataRow[8]);
            }
            if (dataRow[9] != null) {
                this.restPrice = Double.valueOf(dataRow[9]);
            }
            if (dataRow[10] != null) {
                this.commission = Double.valueOf(dataRow[10]);
            }
            if (dataRow[11] != null && dataRow[11]!="") {
                String str="";
                if(dataRow[11].startsWith(".")){
                    str=0+dataRow[11];
                }
                this.certificateFee = Double.valueOf(str);
            }
            if (dataRow[12] != null && dataRow[12]!="") {
                String str="";
                if(dataRow[12].startsWith(".")){
                    str=0+dataRow[12];
                }
                this.handleFee = Double.valueOf(str);
            }
            if (dataRow[13] != null && dataRow[13]!="") {
                String str="";
                if(dataRow[13].startsWith(".")){
                    str=0+dataRow[13];
                }
                this.fee = Double.valueOf(str);
            }
            this.stampDuty = dataRow[14];
            this.transferFee = dataRow[15];
            if (dataRow[16] != null) {
                this.balanceFee = Double.valueOf(dataRow[16]);
            }
            this.annexFee = dataRow[17];
            this.moneyType = dataRow[18];
            this.dealNo = dataRow[19];
            this.shareholderCode = dataRow[20];
            if (dataRow[21] != null) {
                this.accountType = Integer.valueOf(dataRow[21]);
            }
            this.handle = dataRow[22];
            this.reserve = dataRow[23];
            data = dataRow;
        }
    }

    public String getDate() {
        return date;
    }

    public String getBusiName() {
        return busiName;
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

    public double getRestCount() {
        return restCount;
    }

    public double getDealAmount() {
        return dealAmount;
    }

    public double getClearPricre() {
        return clearPricre;
    }

    public double getRestPrice() {
        return restPrice;
    }

    public double getCommission() {
        return commission;
    }

    public double getCertificateFee() {
        return certificateFee;
    }

    public double getHandleFee() {
        return handleFee;
    }

    public double getFee() {
        return fee;
    }

    public String getStampDuty() {
        return stampDuty;
    }

    public String getTransferFee() {
        return transferFee;
    }

    public double getBalanceFee() {
        return balanceFee;
    }

    public String getAnnexFee() {
        return annexFee;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public String getDealNo() {
        return dealNo;
    }

    public String getShareholderCode() {
        return shareholderCode;
    }

    public double getAccountType() {
        return accountType;
    }

    public String getHandle() {
        return handle;
    }

    public String getReserve() {
        return reserve;
    }
}

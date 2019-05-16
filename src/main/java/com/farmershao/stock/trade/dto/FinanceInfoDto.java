package com.farmershao.stock.trade.dto;

import java.text.ParseException;

/**
 * 财务信息	证券代码	流通股本	所属省份	所属行业	财务更新日期	上市日期	总股本	国家股	发起人法人股
 * 法人股	B股	H股	职工股	总资产	流动资产	固定资产	无形资产	股东人数	流动负债	长期负债	资本公积金
 * 净资产	主营收入	主营利润	应收帐款	营业利润	投资收益	经营现金流	总现金流	存货	利润总额
 * 税后利润	净利润	未分利润	保留	保留
 * @Author chenjz
 */
public class FinanceInfoDto {
    /**
     * 市场
     */
    private int marcketCode;

    /**
     * 证券代码
     */
    private String stockCode;

    /**
     * 流通股本
     */
    private double circulShares;

    /**
     * 省份
     */
    private String province;

    /**
     * 所属行业
     */
    private String business;

    /**
     * 财务更新日期
     */
    private String updateString;

    /**
     * 上市日期
     */
    private String marcketString;

    /**
     * 总股本
     */
    private double sumShares;

    /**
     * 国家股
     */
    private double countryShares;

    /**
     * 发起人法人股
     */
    private double sponsorlegalhumanShares;

    /**
     * 法人股
     */
    private double legalPersonShares;

    /**
     * B股
     */
    private double bShares;

    /**
     * H股
     */
    private double hShares;

    /**
     * 职工股
     */
    private double emploeerShares;

    /**
     * 总资产
     */
    private double sumAssets;

    /**
     * 流动资产
     */
    private double flowAsserts;

    /**
     * 固定资产
     */
    private double fixedAsserts;

    /**
     * 无形资产
     */
    private double invisibleAsserts;

    /**
     * 股东人数
     */
    private double shareholderCount;

    /**
     * 流动负债
     */
    private double flowDebt;

    /**
     * 长期负债
     */
    private double longDebt;

    /**
     * 资本公积金
     */
    private double fund;

    /**
     * 净资产
     */
    private double netAssert;

    /**
     * 主营收入
     */
    private double mainIncome;

    /**
     * 主营利润
     */
    private double mainProfit;

    /**
     * 应收账款
     */
    private double shouldInBill;

    /**
     * 银业利润
     */
    private double businessProfit;

    /**
     * 投资收益
     */
    private double investmentProfit;

    /**
     * 经营现金流
     */
    private double businessCash;

    /**
     * 总现金流
     */
    private double sumCash;

    /**
     * 存货
     */
    private double saveGoods;

    /**
     * 利润总额
     */
    private double sumProfit;

    /**
     * 税后利润
     */
    private double afterTaxProfit;

    /**
     * 净利润
     */
    private double netProfit;

    /**
     * 未分利润
     */
    private double notDivideProfit;

    /**
     * 保留字段1
     */
    private String reserved1;

    /**
     * 保留字段2
     */
    private String reserved2;


    public int getMarcketCode() {
        return marcketCode;
    }

    public String getStockCode() {
        return stockCode;
    }

    public double getCirculShares() {
        return circulShares;
    }

    public String getProvince() {
        return province;
    }

    public String getBusiness() {
        return business;
    }

    public String getUpdateString() {
        return updateString;
    }

    public String getMarcketString() {
        return marcketString;
    }

    public double getSumShares() {
        return sumShares;
    }

    public double getCountryShares() {
        return countryShares;
    }

    public double getSponsorlegalhumanShares() {
        return sponsorlegalhumanShares;
    }

    public double getLegalPersonShares() {
        return legalPersonShares;
    }

    public double getBshares() {
        return bShares;
    }

    public double getHshares() {
        return hShares;
    }

    public double getEmploeerShares() {
        return emploeerShares;
    }

    public double getSumAssets() {
        return sumAssets;
    }

    public double getFlowAsserts() {
        return flowAsserts;
    }

    public double getFixedAsserts() {
        return fixedAsserts;
    }

    public double getInvisibleAsserts() {
        return invisibleAsserts;
    }

    public double getShareholderCount() {
        return shareholderCount;
    }

    public double getFlowDebt() {
        return flowDebt;
    }

    public double getLongDebt() {
        return longDebt;
    }

    public double getFund() {
        return fund;
    }

    public double getNetAssert() {
        return netAssert;
    }

    public double getMainIncome() {
        return mainIncome;
    }

    public double getMainProfit() {
        return mainProfit;
    }

    public double getShouldInBill() {
        return shouldInBill;
    }

    public double getBusinessProfit() {
        return businessProfit;
    }

    public double getInvestmentProfit() {
        return investmentProfit;
    }

    public double getBusinessCash() {
        return businessCash;
    }

    public double getSumCash() {
        return sumCash;
    }

    public double getSaveGoods() {
        return saveGoods;
    }

    public double getSumProfit() {
        return sumProfit;
    }

    public double getAfterTaxProfit() {
        return afterTaxProfit;
    }

    public double getNetProfit() {
        return netProfit;
    }

    public double getNotDivideProfit() {
        return notDivideProfit;
    }

    public String getReserved1() {
        return reserved1;
    }

    public String getReserved2() {
        return reserved2;
    }


    public void read(String[] quoteDataRow) throws ParseException {
        if (quoteDataRow != null && quoteDataRow.length == 37) {
          this.marcketCode=Integer.valueOf(quoteDataRow[0]);
            this.stockCode=quoteDataRow[1];
            this.circulShares=Double.valueOf(quoteDataRow[2]);
            this.province=quoteDataRow[3];
            this.business=quoteDataRow[4];
            this.updateString=quoteDataRow[5];
            this.marcketString=quoteDataRow[6];
            this.sumShares=Double.valueOf(quoteDataRow[7]);
            this.countryShares=Double.valueOf(quoteDataRow[8]);
            this.sponsorlegalhumanShares=Double.valueOf(quoteDataRow[9]);
            this.legalPersonShares=Double.valueOf(quoteDataRow[10]);
            this.bShares=Double.valueOf(quoteDataRow[11]);
            this.hShares=Double.valueOf(quoteDataRow[12]);
            this.emploeerShares=Double.valueOf(quoteDataRow[13]);
            this.sumAssets=Double.valueOf(quoteDataRow[14]);
            this.flowAsserts=Double.valueOf(quoteDataRow[15]);
            this.fixedAsserts=Double.valueOf(quoteDataRow[16]);
            this.invisibleAsserts=Double.valueOf(quoteDataRow[17]);
            this.shareholderCount=Double.valueOf(quoteDataRow[18]);
            this.flowDebt=Double.valueOf(quoteDataRow[19]);
            this.longDebt=Double.valueOf(quoteDataRow[20]);
            this.fund=Double.valueOf(quoteDataRow[21]);
            this.netAssert=Double.valueOf(quoteDataRow[22]);
            this.mainIncome=Double.valueOf(quoteDataRow[23]);
            this.mainProfit=Double.valueOf(quoteDataRow[24]);
            this.shouldInBill=Double.valueOf(quoteDataRow[25]);
            this.businessProfit=Double.valueOf(quoteDataRow[26]);
            this.investmentProfit=Double.valueOf(quoteDataRow[27]);
            this.businessCash=Double.valueOf(quoteDataRow[28]);
            this.sumCash=Double.valueOf(quoteDataRow[29]);
            this.saveGoods=Double.valueOf(quoteDataRow[30]);
            this.sumProfit=Double.valueOf(quoteDataRow[31]);
            this.afterTaxProfit=Double.valueOf(quoteDataRow[32]);
            this.netProfit=Double.valueOf(quoteDataRow[33]);
            this.notDivideProfit=Double.valueOf(quoteDataRow[34]);
            this.reserved1=quoteDataRow[35];
            this.reserved2=quoteDataRow[36];











        }
    }
}

package com.farmershao.stock.trade.dto;

/**
 * 配置文件中账户信息
 *
 * @author: Zhao Da
 * @since: 2018/4/25 17:15
 */
public class AccountDto {

    /** 交易服务器主机IP */
    private String tradeServerIp;
    /** 交易服务器端口号 */
    private String tradePort;
    /** 券商id */
    private String stockJobberId;
    /** 营业部id */
    private String stockExchangeId;
    /** 账户类型 */
    private String accountType;
    /** 登录账号 */
    private String accountNo;
    /** 资金账号 */
    private String bankrollAccount;
    /** 登录密码 */
    private String accountPassword;
    /** 通信密码 */
    private String communicationPassword;

    public String getTradeServerIp() {
        return tradeServerIp;
    }

    public void setTradeServerIp(String tradeServerIp) {
        this.tradeServerIp = tradeServerIp;
    }

    public String getTradePort() {
        return tradePort;
    }

    public void setTradePort(String tradePort) {
        this.tradePort = tradePort;
    }

    public String getStockJobberId() {
        return stockJobberId;
    }

    public void setStockJobberId(String stockJobberId) {
        this.stockJobberId = stockJobberId;
    }

    public String getStockExchangeId() {
        return stockExchangeId;
    }

    public void setStockExchangeId(String stockExchangeId) {
        this.stockExchangeId = stockExchangeId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankrollAccount() {
        return bankrollAccount;
    }

    public void setBankrollAccount(String bankrollAccount) {
        this.bankrollAccount = bankrollAccount;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getCommunicationPassword() {
        return communicationPassword;
    }

    public void setCommunicationPassword(String communicationPassword) {
        this.communicationPassword = communicationPassword;
    }
}

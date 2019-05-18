package com.farmershao.stock.trade.model.domain;

import java.util.Date;


public class AccountDO {
    private Long id;
    private String accountNo;

    /**
     * 交易账号，一般与登录帐号相同
     */
    private String tradeAccountNo;

    /**
     * 券商编码，采用通达信的枚举
     */
    private String brokerCode;

    /**
     * 账号类型
     */
    private String accountType;

    /**
     * 交易密码
     */
    private String tradePassword;

    /**
     * 通讯密码
     */
    private String communicationPassword;

    /**
     * 营业部编码
     */
    private String salesDepartmentId;

    /**
     * 记录创建时间
     */
    private Date gmtCreate;

    /**
     * 记录修改时间
     */
    private Date gmtModified;

    /**
     * 服务器，ip:端口，多个逗号隔开
     */
    private String servers;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取账号
     *
     * @return account_no - 账号
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * 设置账号
     *
     * @param accountNo 账号
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * 获取交易账号，一般与登录帐号相同
     *
     * @return trade_account_no - 交易账号，一般与登录帐号相同
     */
    public String getTradeAccountNo() {
        return tradeAccountNo;
    }

    /**
     * 设置交易账号，一般与登录帐号相同
     *
     * @param tradeAccountNo 交易账号，一般与登录帐号相同
     */
    public void setTradeAccountNo(String tradeAccountNo) {
        this.tradeAccountNo = tradeAccountNo;
    }

    /**
     * 获取券商编码，采用通达信的枚举
     *
     * @return broker_code - 券商编码，采用通达信的枚举
     */
    public String getBrokerCode() {
        return brokerCode;
    }

    /**
     * 设置券商编码，采用通达信的枚举
     *
     * @param brokerCode 券商编码，采用通达信的枚举
     */
    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    /**
     * 获取账号类型
     *
     * @return account_type - 账号类型
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * 设置账号类型
     *
     * @param accountType 账号类型
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * 获取交易密码
     *
     * @return trade_password - 交易密码
     */
    public String getTradePassword() {
        return tradePassword;
    }

    /**
     * 设置交易密码
     *
     * @param tradePassword 交易密码
     */
    public void setTradePassword(String tradePassword) {
        this.tradePassword = tradePassword;
    }

    /**
     * 获取通讯密码
     *
     * @return communication_password - 通讯密码
     */
    public String getCommunicationPassword() {
        return communicationPassword;
    }

    /**
     * 设置通讯密码
     *
     * @param communicationPassword 通讯密码
     */
    public void setCommunicationPassword(String communicationPassword) {
        this.communicationPassword = communicationPassword;
    }

    /**
     * 获取营业部编码
     *
     * @return sales_department_id - 营业部编码
     */
    public String getSalesDepartmentId() {
        return salesDepartmentId;
    }

    /**
     * 设置营业部编码
     *
     * @param salesDepartmentId 营业部编码
     */
    public void setSalesDepartmentId(String salesDepartmentId) {
        this.salesDepartmentId = salesDepartmentId;
    }

    /**
     * 获取记录创建时间
     *
     * @return gmt_create - 记录创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置记录创建时间
     *
     * @param gmtCreate 记录创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取记录修改时间
     *
     * @return gmt_modified - 记录修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置记录修改时间
     *
     * @param gmtModified 记录修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 获取服务器，ip:端口，多个逗号隔开
     *
     * @return servers - 服务器，ip:端口，多个逗号隔开
     */
    public String getServers() {
        return servers;
    }

    /**
     * 设置服务器，ip:端口，多个逗号隔开
     *
     * @param servers 服务器，ip:端口，多个逗号隔开
     */
    public void setServers(String servers) {
        this.servers = servers;
    }
}
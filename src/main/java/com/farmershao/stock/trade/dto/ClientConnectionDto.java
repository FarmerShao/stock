package com.farmershao.stock.trade.dto;

/**
 * 交易客户端连接
 *
 * @author Shao Yu
 * @since 2018/4/25 10:45
 **/
public class ClientConnectionDto {

    private AccountDto acoount;

    private int clientId;

    private int connId;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getConnId() {
        return connId;
    }

    public void setConnId(int connId) {
        this.connId = connId;
    }

    public AccountDto getAcoount() {
        return acoount;
    }

    public void setAcoount(AccountDto acoount) {
        this.acoount = acoount;
    }
}

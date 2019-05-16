package com.farmershao.stock.trade.dto;

public class ClientConnection {

    private String acoount;

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

    public String getAcoount() {
        return acoount;
    }

    public void setAcoount(String acoount) {
        this.acoount = acoount;
    }



}

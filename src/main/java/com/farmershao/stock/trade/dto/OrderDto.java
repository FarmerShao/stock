package com.farmershao.stock.trade.dto;

import com.farmershao.stock.trade.constant.OrderCate;
import com.farmershao.stock.trade.constant.PriceCate;

/**
 * 委托单实体
 */
public class OrderDto {

    //submit properties
    // 委托种类
    private OrderCate orderCate;
    // 报价方式
    private PriceCate priceCate;
    // 股东代码；交易上海股票填上海的股东代码，交易深圳的股票填入深圳的股东代码
    private String gddm;
    // 证券代码
    private String stockCode;
    // 委托价格
    private float price;
    // 委托数量
    private int quantity;
    // 券商账号
    private String accountNo;

    //Result properties
    //委托编号
    private String orderNo;

    public OrderDto(OrderCate orderCate,
                    PriceCate priceCate,
                    String gddm,
                    String stockCode,
                    float price,
                    int quantity,
                    String accountNo) {
        this.orderCate = orderCate;
        this.priceCate = priceCate;
        this.gddm = gddm;
        this.stockCode = stockCode;
        this.price = price;
        this.quantity = quantity;
        this.accountNo = accountNo;
    }

    public OrderDto() {
    }


    public OrderDto(String orderNo) {
        this.orderNo = orderNo;
    }

    public OrderDto(String orderNo, String stockCode) {
        this.orderNo = orderNo;
        this.stockCode = stockCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setGddm(String gddm) {
        this.gddm = gddm;
    }

    public String getGddm() {
        return gddm;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public OrderCate getOrderCate() {
        return orderCate;
    }

    public void setOrderCate(OrderCate orderCate) {
        this.orderCate = orderCate;
    }

    public PriceCate getPriceCate() {
        return priceCate;
    }

    public void setPriceCate(PriceCate priceCate) {
        this.priceCate = priceCate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("orderCate:" + orderCate).append(",");
        sb.append("priceCate:" + priceCate).append(",");
        sb.append("gddm:" + gddm);
        sb.append("stockCode:" + stockCode).append(",");
        sb.append("price:" + price).append(",");
        sb.append("quantity:" + quantity).append(",");
        sb.append("orderNo:" + orderNo).append(",");
        sb.append("}");
        return sb.toString();
    }
}

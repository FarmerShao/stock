package com.farmershao.stock.trade.model.suport;

/**
 * 挂单错误类
 *
 * Created by kongkp on 2017/1/9.
 */
public class OrderError extends Order implements Error {
    private static final long serialVersionUID = 1L;

    private String errorMessage;

    public OrderError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public OrderError(String errorMessage, Order order) {
        if (order != null) {
            this.setOrderCate(order.getOrderCate());
            this.setPriceCate(this.getPriceCate());
            this.setStockCode(order.getStockCode());
            this.setPrice(order.getPrice());
            this.setQuantity(order.getQuantity());
            this.setOrderNo(order.getOrderNo());
            this.setMessage(order.getMessage());
            this.setExtMessage(order.getExtMessage());
        }
        this.errorMessage = errorMessage;
    }


    @Override
    public String getErrorMsg() {
        return errorMessage;
    }

}

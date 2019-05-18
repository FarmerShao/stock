package com.farmershao.stock.trade.model.suport;

import java.io.Serializable;

import com.farmershao.stock.trade.enums.OrderCate;
import com.farmershao.stock.trade.enums.PriceCate;


/**
 *
 * Created by kongkp on 2017/1/9.
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	// submit properties
	private OrderCate orderCate;
	private PriceCate priceCate;
	private String stockCode;
	private float price;
	private int quantity;

	// Result properties
	private String orderNo; // 委托编号
	private String message;
	private String extMessage;

	public Order(OrderCate orderCate, PriceCate priceCate, String stockCode, float price, int quantity) {
		this.orderCate = orderCate;
		this.priceCate = priceCate;
		this.stockCode = stockCode;
		this.price = price;
		this.quantity = quantity;
	}

	public Order() {
	}

	public Order(String orderNo) {
		this.orderNo = orderNo;
	}

	public Order(String orderNo, String stockCode) {
		this.orderNo = orderNo;
		this.stockCode = stockCode;
	}

	public String getExtMessage() {
		return extMessage;
	}

	public void setExtMessage(String extMessage) {
		this.extMessage = extMessage;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	@Override
	public String toString() {
		return "Order [orderCate=" + orderCate + ", priceCate=" + priceCate + ", stockCode=" + stockCode + ", price="
				+ price + ", quantity=" + quantity + ", orderNo=" + orderNo + ", message=" + message + ", extMessage="
				+ extMessage + "]";
	}

}

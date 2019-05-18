package com.farmershao.stock.trade.dto;


/**
 * 策略详情
 * @author chenjz
 *
 */
public class StrategyDto {
    
	/** 策略ID **/
	private int order_id;
	
	/** 股票代码 **/
	private String stock_code;
	
	/** 股票名称 **/
	private String stock_name;
	
    /** 委托类型（买入/卖出/持仓）    **/
	private String Strategy_type;

    /** 交易状态   **/
	private String buss_status;
	
	/**   持股数量    **/
	private int own_count;
	
	/** 策略盈亏    **/
	private double profit;
	
	/** 买入价   **/
	private double buy_price;
	/** 卖出价   **/
	private double asprice;
	
	/**  卖出类型 (止损价卖出、委托价卖出)   **/
	private String sell_type;
	
	/** 止损价  **/
	private double slline_price;
	
	/**  保证金  **/
	private double margin;
	
	/** 已追加保证金  **/
	private double margin_all;
	
	/** 买入时间  **/
	private String buy_time;
	
	/**  综合费用   **/
	private double complex_fee;
	
	/**  延期费    **/
	private double daily_fee;
	
	/**  延期天数  **/
	private int days;
	
	/** 委托价格  **/
	private double obprice;
	
	/** 委托数量  **/
	private int count;
	
	/** 策略本金  **/
	private double capital;
	
	/** 杠杆倍数   **/
	private int lever;
	
	/** 盈利分成比例  **/
	private double psrate; 
	
	/** 成交状态（部分成交/全部成交）  **/
	private String deal_status;
	
    /** 委托时间  **/
	private String ctime;

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getStock_code() {
		return stock_code;
	}

	public void setStock_code(String stock_code) {
		this.stock_code = stock_code;
	}

	public String getStock_name() {
		return stock_name;
	}

	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}

	public String getStrategy_type() {
		return Strategy_type;
	}

	public void setStrategy_type(String strategy_type) {
		Strategy_type = strategy_type;
	}

	public String getBuss_status() {
		return buss_status;
	}

	public void setBuss_status(String buss_status) {
		this.buss_status = buss_status;
	}

	public int getOwn_count() {
		return own_count;
	}

	public void setOwn_count(int own_count) {
		this.own_count = own_count;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getBuy_price() {
		return buy_price;
	}

	public void setBuy_price(double buy_price) {
		this.buy_price = buy_price;
	}

	public double getAsprice() {
		return asprice;
	}

	public void setAsprice(double asprice) {
		this.asprice = asprice;
	}

	public String getSell_type() {
		return sell_type;
	}

	public void setSell_type(String sell_type) {
		this.sell_type = sell_type;
	}

	public double getSlline_price() {
		return slline_price;
	}

	public void setSlline_price(double slline_price) {
		this.slline_price = slline_price;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public double getMargin_all() {
		return margin_all;
	}

	public void setMargin_all(double margin_all) {
		this.margin_all = margin_all;
	}

	public String getBuy_time() {
		return buy_time;
	}

	public void setBuy_time(String buy_time) {
		this.buy_time = buy_time;
	}

	public double getComplex_fee() {
		return complex_fee;
	}

	public void setComplex_fee(double complex_fee) {
		this.complex_fee = complex_fee;
	}

	public double getDaily_fee() {
		return daily_fee;
	}

	public void setDaily_fee(double daily_fee) {
		this.daily_fee = daily_fee;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public double getObprice() {
		return obprice;
	}

	public void setObprice(double obprice) {
		this.obprice = obprice;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

	public int getLever() {
		return lever;
	}

	public void setLever(int lever) {
		this.lever = lever;
	}

	public double getPsrate() {
		return psrate;
	}

	public void setPsrate(double psrate) {
		this.psrate = psrate;
	}

	public String getDeal_status() {
		return deal_status;
	}

	public void setDeal_status(String deal_status) {
		this.deal_status = deal_status;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	
	


}

package com.farmershao.stock.trade.tradex;

import com.farmershao.stock.trade.enums.*;
import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.model.suport.*;
import com.farmershao.stock.trade.util.StockUtils;
import com.google.common.base.Preconditions;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 通达信客户端接口类，通过此类进行股票交易操作
 * <p>
 * Created by kongkp on 2017/1/5.
 */
public class TdxClient {
	private static Logger LOGGER = LoggerFactory.getLogger(TdxClient.class);

	private static final int RETRY = 2;

	private int clientId = -1;
	private TdxLibrary tdxLibrary;

	private volatile int hostIndex = 0;
	private List<String> hosts = new ArrayList<>();
	private List<Short> ports = new ArrayList<>();
	private String clientVersion;
	private short yybId;
	private String accountNo;
	private String jyAccount;
	private String jyPassword;
	private String txPassword;
	private String brokerCode;
	private String accountType;

	TdxClient(TdxLibrary tdxLibrary, String qsServer, short yybId, String clientVersion, String accountNo,
			String jyAccount, String jyPassword, String txPassword, String brokerCode, String accountType)
			throws TradeException {
		this.tdxLibrary = tdxLibrary;
		this.yybId = yybId;
		this.clientVersion = clientVersion;
		this.accountNo = accountNo;
		this.jyAccount = jyAccount;
		this.jyPassword = jyPassword;
		this.txPassword = txPassword;
		this.brokerCode = brokerCode;
		this.accountType = accountType;
		initQsServer(qsServer);
	}

	private void initQsServer(String qsServer) throws TradeException {
		String[] servers = StringUtils.split(qsServer, ",;| ");
		for (int i = 0, len = servers.length; i < len; i++) {
			String[] hostPort = StringUtils.split(servers[i], ':');
			if (hostPort.length == 2) {
				hosts.add(StringUtils.trim(hostPort[0]));
				ports.add(Short.valueOf(StringUtils.trim(hostPort[1])));
			} else {
				System.out.println("券商服务器(" + servers[i] + ")" + "配置错误,正确格式为: host:port[,host:port]");
			}
		}

		if (hosts.size() == 0) {
			throw new TradeException("券商服务器没配置或配置错误");
		}
	}

	public void logon() throws TradeException {
		for (int i = 0, len = hosts.size(); i < len; i++) {
			String host = hosts.get(hostIndex);
			short port = ports.get(hostIndex);
			byte[] errInfo = new byte[256];
			int clientId = tdxLibrary.Logon(Integer.valueOf(brokerCode), host, port, clientVersion, yybId,
					Byte.valueOf(accountType), accountNo, jyAccount, jyPassword, "", errInfo);
			if (clientId < 0) {
				String error = Native.toString(errInfo, "GBK");
				LOGGER.error("登录交易服务器({}:{})失败,原因:{}", host, port, error);
				nextServer();
			} else {
				LOGGER.info("登录交易服务器{}:{}成功:", host, port);
				this.clientId = clientId;
				return;
			}
		}
		throw new TradeException("所有交易服务器都无法成功登录.");
	}

	public void logoff() {
		try {
			if (clientId >= 0) {
				tdxLibrary.Logoff(clientId);
			}
		} finally {
			clientId = -1;
		}
	}

	public boolean isLogon() {
		return clientId != -1;
	}

	private void relogon() throws TradeException {
		this.logoff();
		this.logon();
	}

	int checkError(String error) {
		if (StringUtils.isNotBlank(error)) {
			if ((StringUtils.indexOf(error, "连接交易服务器失败") > -1
					|| StringUtils.indexOf(error, "ErrType=7,ErrCode=10014,ErrInfo=") > -1) && clientId != -1) {
				try {
					this.relogon();
					return 2;
				} catch (TradeException e) {
					e.printStackTrace(System.err);
				}
			}
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 查询各种交易数据
	 */
	public DataTable queryData(DataCate dataCate) throws TradeException {
		Preconditions.checkState(clientId >= 0, "请先登录(调用logon方法)再执行本操作.");

		byte[] rltData = new byte[1024 * 1024];
		byte[] errData = new byte[256];
		String error = "";
		for (int i = 0; i < RETRY; i++) {
			tdxLibrary.QueryData(clientId, dataCate.getId(), rltData, errData);
			String result = Native.toString(rltData, "GBK");
			error = Native.toString(errData, "GBK");
			int errType = checkError(error);
			if (errType == 0) {
				return new DataTable(dataCate.getName(), result);
			}
			if (errType == 1) {
				break;
			}
		}
		throw new TradeException(error);
	}

	/**
	 * 提交买卖交割单子
	 *
	 * @throws TradeException
	 */
	public Order sendOrder(Order order) throws TradeException {
		Preconditions.checkArgument(order != null, "order参数不能为null.");
		Preconditions.checkArgument(order.getStockCode() != null, "order.stockCode参数不能为null.");
		Preconditions.checkArgument(order.getOrderCate() != null, "order.orderCate参数不能为null.");
		Preconditions.checkArgument(order.getPriceCate() != null, "order.orderCate参数不能为null.");
		Preconditions.checkArgument(order.getQuantity() >= 0, "order.quantity不能小于0.");
		Preconditions.checkArgument(priceCateMatchStockCode(order), "价格类型不适用于该股票.");
		Preconditions.checkState(clientId >= 0, "请先登录(调用logon方法)再执行本操作.");

		int orderCateId = order.getOrderCate().getId();
		int priceCateId = order.getPriceCate().getId();
		String stockCode = order.getStockCode();
		float price = order.getPrice();
		int quantity = order.getQuantity();

		byte[] rltData = new byte[1024 * 1024];
		byte[] errData = new byte[256];
		String gddm = getGddm(stockCode);
		if (StringUtils.isBlank(gddm)) {
			throw new TradeException("当前用户没有开通改股票所在市场的帐号");
		}

		String error = "";
		for (int i = 0; i < RETRY; i++) {
			tdxLibrary.SendOrder(clientId, orderCateId, priceCateId, gddm, stockCode, price, quantity, rltData,
					errData);
			String result = Native.toString(rltData, "GBK");
			error = Native.toString(errData, "GBK");
			int errType = checkError(error);
			if (errType == 0) {
				DataTable rltDt = new DataTable(stockCode, result);
				order.setOrderNo(rltDt.get(0, 0));
				return order;
			}
			if (errType == 1) {
				break;
			}
		}
		throw new TradeException(error);
	}

	private boolean priceCateMatchStockCode(Order order) {
		return priceCateMatchStockCode(order.getPriceCate(), order.getStockCode());
	}

	private boolean priceCateMatchStockCode(PriceCate priceCate, String stockCode) {
		String pCate = priceCate.getName();
		String markget = StockUtils.getMarket(stockCode);
		if (markget.startsWith("SZ") && pCate.startsWith("SH_")) {
			return false;
		}
		if (markget.startsWith("SH") && pCate.startsWith("SZ_")) {
			return false;
		}
		return true;
	}

	/**
	 * 撤销单子
	 *
	 * @param order
	 *            不能为null，并且必须含有属性: orderNo和stockCode
	 * @return
	 * @throws TradeException
	 */
	public String cancelOrder(Order order) throws TradeException {
		Preconditions.checkArgument(order != null, "order参数不能为null.");
		Preconditions.checkArgument(StringUtils.isNotBlank(order.getOrderNo()), "委托的编号(order.orderNo)不能为null.");
		Preconditions.checkArgument(StringUtils.isNotBlank(order.getStockCode()), "证券代码(order.stockCode)不能为null.");
		Preconditions.checkState(clientId >= 0, "请先登录(调用logon方法)再执行本操作.");

		String exchangeId = getExchangeId(order.getStockCode());
		byte[] rltData = new byte[1024 * 1024];
		byte[] errData = new byte[256];

		String error = "";
		for (int i = 0; i < RETRY; i++) {
			tdxLibrary.CancelOrder(clientId, exchangeId, order.getOrderNo(), rltData, errData);
			String result = Native.toString(rltData, "GBK");
			error = Native.toString(errData, "GBK");
			int errType = checkError(error);
			if (errType == 0) {
				return result;
			}
			if (errType == 1) {
				break;
			}
		}
		throw new TradeException(error);
	}

	public Quote getQuote(String stockCode) throws TradeException {

		Preconditions.checkState(clientId >= 0, "请先登录(调用logon方法)再执行本操作.");
		byte[] rltData = new byte[1024 * 1024];
		byte[] errData = new byte[256];

		String error = "";
		for (int i = 0; i < RETRY; i++) {
			tdxLibrary.GetQuote(clientId, stockCode, rltData, errData);
			String result = Native.toString(rltData, "GBK");
			error = Native.toString(errData, "GBK");
			int errType = checkError(error);
			if (errType == 0) {
				Quote quote = new Quote();
				DataTable dt = new DataTable("QUOTE-" + stockCode, result);
				if (dt.rows() == 1) {
					quote.read(dt.getRow(0));
				}
				return quote;
			}
			if (errType == 1) {
				break;
			}
		}
		throw new TradeException(error);
	}

	// TODO debug
	public String repay(int amount) throws TradeException {
		Preconditions.checkState(clientId >= 0, "请先登录(调用logon方法)再执行本操作.");
		byte[] rltData = new byte[1024 * 1024];
		byte[] errData = new byte[256];

		String error = "";
		for (int i = 0; i < RETRY; i++) {
			tdxLibrary.Repay(clientId, String.valueOf(amount), rltData, errData);
			String result = Native.toString(rltData, "GBK");
			error = Native.toString(errData, "GBK");
			int errType = checkError(error);
			if (errType == 0) {
				return result;
			}
			if (errType == 1) {
				break;
			}
		}
		throw new TradeException(error);
	}

	private DataTable gddm = null;

	// TODO 改进，支持其他市场
	synchronized String getGddm(String stockCode) throws TradeException {
		if (gddm == null) {
			gddm = this.queryData(DataCate.GDDM);
		}
		String market = StockUtils.getMarket(stockCode);
		final String accountType;
		switch (market) {
		case "SH.A":
			accountType = "1";
			break;
		case "SZ.A":
			accountType = "0";
			break;
		case "SZ.ZX":
			accountType = "0";
			break;
		case "SZ.CY":
			accountType = "0";
			break;
		default:
			accountType = "";
		}

		final StringBuilder sb = new StringBuilder();
		gddm.browse(row -> {
			if (accountType.equals(row[2])) {
				sb.append(row[0]);
				return false;
			}
			return true;
		});
		return sb.toString();
	}

	public DataTable queryHistoryData(final HistDataCate dataType, Date startDate, Date endDate) throws TradeException {
		Preconditions.checkState(clientId >= 0, "请先登录(调用logon方法)再执行本操作.");

		byte[] rltData = new byte[1024 * 1024];
		byte[] errData = new byte[256];
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String sDate = df.format(startDate);
		String eDate = df.format(endDate);

		String error = "";
		for (int i = 0; i < RETRY; i++) {
			tdxLibrary.QueryHistoryData(clientId, dataType.getId(), sDate, eDate, rltData, errData);
			String result = Native.toString(rltData, "GBK");
			error = Native.toString(errData, "GBK");
			int errType = checkError(error);
			if (errType == 0) {
				return new DataTable(dataType.getName(), result);
			}
			if (errType == 1) {
				break;
			}
		}
		throw new TradeException(error);
	}

	public List<DataTable> queryDatas(final List<DataCate> dataCates) throws TradeException {
		Preconditions.checkArgument(dataCates != null, "dataTypes参数不能为null.");
		Preconditions.checkState(clientId >= 0, "请先登录(调用logon方法)再执行本操作.");

		int count = dataCates.size();
		Pointer[] rltData = makeMemorys(count, 1024 * 1024);
		Pointer[] errData = makeMemorys(count, 256);

		tdxLibrary.QueryDatas(clientId, enumToIntArr(dataCates), count, rltData, errData);
		return transfer(rltData, errData, (int i, String result, String resultType) -> {
			String dataType = dataCates.get(i).getName();
			if ("error".equals(resultType)) {
				return new DataTableError(dataType + "-ERROR", result);
			} else {
				return new DataTable(dataType, result);
			}
		});
	}

	public List<Order> sendOrders(Order... orders) throws TradeException {
		Preconditions.checkArgument(orders != null, "orders参数不能为null");
		return sendOrders(Arrays.asList(orders));
	}

	public List<Order> sendOrders(final List<Order> orders) throws TradeException {
		Preconditions.checkArgument(orders != null, "orders参数不能为null");
		Preconditions.checkState(clientId >= 0, "请先登录(调用logon方法)再执行本操作.");

		if (orders.size() > 0) {
			List<Integer> validIndex = validateSendOrders(orders);
			List<Order> validOrders = new ArrayList<>();
			validIndex.forEach(i -> validOrders.add(orders.get(i)));

			int count = validOrders.size();
			Pointer[] rltData = makeMemorys(count, 1024 * 1024);
			Pointer[] errData = makeMemorys(count, 256);
			tdxLibrary.SendOrders(clientId, getOrderTypeIds(validOrders), getPriceTypeIds(validOrders),
					getGddms(validOrders), getStockCodes(validOrders), getPrices(validOrders),
					getQuantities(validOrders), count, rltData, errData);

			transfer(rltData, errData, (int i, String result, String resultType) -> {
				Integer index = validIndex.get(i);
				Order order = orders.get(index);
				if ("error".equals(resultType)) {
					Order orderError = new OrderError(result, order);
					orders.set(index, orderError);
					return orderError;
				} else {
					DataTable resultTable = new DataTable(result);
					order.setOrderNo(resultTable.get(0, 0));
					return order;
				}
			});

			return orders;
		} else {
			return orders;
		}
	}

	private List<Integer> validateSendOrders(List<Order> orders) throws TradeException {
		List<Integer> validIndex = new ArrayList<>();
		for (int i = 0, len = orders.size(); i < len; i++) {
			Order order = orders.get(i);
			String stockCode = order.getStockCode();
			if (StringUtils.isBlank(getGddm(stockCode))) {
				orders.set(i, new OrderError("当前用户没有开通改股票所在市场的帐号", order));
				continue;
			}
			if (!priceCateMatchStockCode(order)) {
				orders.set(i, new OrderError("价格类型不适用于该股票", order));
				continue;
			}
			validIndex.add(i);
		}
		return validIndex;
	}

	public List<Boolean> cancelOrders(Order... orders) throws TradeException {
		Preconditions.checkArgument(orders != null, "orders参数不能为null");
		return cancelOrders(Arrays.asList(orders));
	}

	public List<Boolean> cancelOrders(final List<Order> orders) throws TradeException {
		Preconditions.checkArgument(orders != null, "orders参数不能为null");
		Preconditions.checkArgument(orders.size() > 0, "orders个数必须大于0");
		Preconditions.checkState(clientId >= 0, "请先登录(调用logon方法)再执行本操作.");

		int count = orders.size();
		String[] stockCodes = getStockCodes(orders);
		String[] exchangeIds = getExchangeIds(stockCodes);
		String[] orderNos = getOrderNos(orders);
		Pointer[] rltData = makeMemorys(count, 1024 * 1024);
		Pointer[] errData = makeMemorys(count, 256);

		tdxLibrary.CancelOrders(clientId, exchangeIds, orderNos, count, rltData, errData);
		return transfer(rltData, errData, (int i, String result, String resultType) -> {
			if ("error".equals(resultType)) {
				return false;
			} else {
				return true;
			}
		});
	}

	public List<Quote> getQuotes(final String... stockCodes) throws TradeException {
		Preconditions.checkArgument(stockCodes != null, "stockCodes参数不能为null");
		return getQuotes(Arrays.asList(stockCodes));
	}

	public List<Quote> getQuotes(final List<String> stockCodes) throws TradeException {
		Preconditions.checkArgument(stockCodes != null, "stockCodes参数不能为null");
		Preconditions.checkArgument(stockCodes.size() > 0, "stockCodes个数必须大于0");
		Preconditions.checkState(clientId >= 0, "请先登录(调用logon方法)再执行本操作.");

		int count = stockCodes.size();
		String[] stocks = stockCodes.toArray(new String[count]);
		Pointer[] rltData = makeMemorys(count, 1024 * 1024);
		Pointer[] errData = makeMemorys(count, 256);

		tdxLibrary.GetQuotes(clientId, stocks, count, rltData, errData);
		return transfer(rltData, errData,
				(int i, String result, String resultType) -> getQuote(stocks[i], result, resultType));
	}

	static Quote getQuote(String stockCode, String quoteContent, String resultOrError) {
		if ("result".equals(resultOrError)) {
			DataTable dt = new DataTable("QUOTE-" + stockCode, quoteContent);
			if (dt.rows() > 0) {
				Quote quote = new Quote();
				quote.read(dt.getRow(0));
				return quote;
			} else {
				return new QuoteError("返回结果没有内容");
			}
		} else {
			return new QuoteError(quoteContent);
		}
	}

	static Pointer[] makeMemorys(int count, int site) {
		Pointer[] rltData = new Pointer[count];
		for (int i = 0; i < count; i++) {
			Memory mem = new Memory(site);
			rltData[i] = mem;
		}
		return rltData;
	}

	static <T> List<T> transfer(List<TdxClient> clients, Pointer[] rltData, Pointer[] errData,
			ResultTransfer<T> transfer) {
		List<T> results = new ArrayList<>();
		for (int i = 0, len = rltData.length; i < rltData.length; i++) {
			Memory rltMem = (Memory) rltData[i];
			Memory errMem = (Memory) errData[i];
			byte[] rlt = rltMem.getByteArray(0L, (int) rltMem.size());
			byte[] err = errMem.getByteArray(0L, (int) errMem.size());

			String error = Native.toString(err, "GBK");
			TdxClient client = clients.size() == 1 ? clients.get(0) : clients.get(i);
			if (client.checkError(error) != 0) {
				T e = transfer.transfer(i, error, "error");
				results.add(e);
			} else {
				String result = Native.toString(rlt, "GBK");
				T r = transfer.transfer(i, result, "result");
				results.add(r);
			}
		}
		return results;
	}

	<T> List<T> transfer(Pointer[] rltData, Pointer[] errData, ResultTransfer<T> transfer) {
		List<TdxClient> clients = new ArrayList<>(1);
		clients.add(this);
		return transfer(clients, rltData, errData, transfer);
	}

	@FunctionalInterface
	interface ResultTransfer<T> {
		T transfer(int i, String data, String type);
	}

	private void nextServer() {
		hostIndex += 1;
		hostIndex = hostIndex % hosts.size();
	}

	private String[] getGddms(List<Order> orders) throws TradeException {
		String[] gddms = new String[orders.size()];
		for (int i = 0, len = gddms.length; i < len; i++) {
			gddms[i] = getGddm(orders.get(i).getStockCode());
		}
		return gddms;
	}

	private static int[] getOrderTypeIds(List<Order> orders) {
		int[] orderIds = new int[orders.size()];
		for (int i = 0, len = orderIds.length; i < len; i++) {
			orderIds[i] = orders.get(i).getOrderCate().getId();
		}
		return orderIds;
	}

	private static int[] getPriceTypeIds(List<Order> orders) {
		int[] priceTypeIds = new int[orders.size()];
		for (int i = 0, len = priceTypeIds.length; i < len; i++) {
			priceTypeIds[i] = orders.get(i).getPriceCate().getId();
		}
		return priceTypeIds;
	}

	private static String[] getStockCodes(List<Order> orders) throws TradeException {
		String[] stockCodes = new String[orders.size()];
		for (int i = 0, len = stockCodes.length; i < len; i++) {
			stockCodes[i] = orders.get(i).getStockCode();
		}
		return stockCodes;
	}

	private static float[] getPrices(List<Order> orders) {
		float[] prices = new float[orders.size()];
		for (int i = 0, len = prices.length; i < len; i++) {
			prices[i] = orders.get(i).getPrice();
		}
		return prices;
	}

	private static int[] getQuantities(List<Order> orders) {
		int[] quantities = new int[orders.size()];
		for (int i = 0, len = quantities.length; i < len; i++) {
			quantities[i] = orders.get(i).getQuantity();
		}
		return quantities;
	}

	private static String[] getOrderNos(List<Order> orders) {
		String[] orderNos = new String[orders.size()];
		for (int i = 0, len = orderNos.length; i < len; i++) {
			orderNos[i] = orders.get(i).getOrderNo();
		}
		return orderNos;
	}

	private static String[] getExchangeIds(String[] stockCodes) {
		String[] exchangeIds = new String[stockCodes.length];
		for (int i = 0, len = exchangeIds.length; i < len; i++) {
			exchangeIds[i] = getExchangeId(stockCodes[i]);
		}
		return exchangeIds;
	}

	private static String getExchangeId(String stockCode) {
		String market = StockUtils.getMarket(stockCode);
		if (market.startsWith("SH.")) {
			return String.valueOf(ExchangeId.SH.getId());
		} else if (market.startsWith("SZ.")) {
			return String.valueOf(ExchangeId.SZ.getId());
		}
		return "";
	}

	static <T extends IdAndName> int[] enumToIntArr(List<T> list) {
		if (list != null) {
			int[] intArr = new int[list.size()];
			for (int i = 0, len = list.size(); i < len; i++) {
				intArr[i] = list.get(i).getId();
			}
			return intArr;
		} else {
			return new int[0];
		}
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getClientId() {
		return clientId;
	}

	public TdxLibrary getTdxLibrary() {
		return tdxLibrary;
	}

	public void setTdxLibrary(TdxLibrary tdxLibrary) {
		this.tdxLibrary = tdxLibrary;
	}

}

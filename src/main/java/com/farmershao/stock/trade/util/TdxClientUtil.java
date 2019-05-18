package com.farmershao.stock.trade.util;

import com.farmershao.stock.trade.enums.DataCate;
import com.farmershao.stock.trade.enums.ExchangeId;
import com.farmershao.stock.trade.enums.PriceCate;
import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.model.suport.DataTable;
import com.farmershao.stock.trade.model.suport.Order;
import com.farmershao.stock.trade.tradex.TdxClient;
import com.farmershao.stock.trade.tradex.TdxLibrary;
import com.google.common.base.Preconditions;
import com.sun.jna.Native;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端通讯接口
 * <p>
 * Created by kongkp on 2017/1/7.
 */
public class TdxClientUtil extends TdxClientPollManager {
	private static final Logger logger = LoggerFactory.getLogger(TdxClientUtil.class);
	private static final Map<Long, DataTable> GDDM_MAP = new ConcurrentHashMap<>();

	public static Order sendOrder(Order order) throws TradeException {
		return sendOrder(order, 1L);
	}

	/**
	 * 提交买卖交割单子
	 *
	 * @throws TradeException
	 */
	public static Order sendOrder(Order order, Long accountId) throws TradeException {
		Preconditions.checkArgument(order != null, "order参数不能为null.");
		Preconditions.checkArgument(order.getStockCode() != null, "order.stockCode参数不能为null.");
		Preconditions.checkArgument(order.getOrderCate() != null, "order.orderCate参数不能为null.");
		Preconditions.checkArgument(order.getPriceCate() != null, "order.orderCate参数不能为null.");
		Preconditions.checkArgument(order.getQuantity() >= 0, "order.quantity不能小于0.");
		Preconditions.checkArgument(priceCateMatchStockCode(order), "价格类型不适用于该股票.");

		int orderCateId = order.getOrderCate().getId();
		int priceCateId = order.getPriceCate().getId();
		String stockCode = order.getStockCode();
		float price = order.getPrice();
		int quantity = order.getQuantity();

		byte[] rltData = new byte[1024 * 1024];
		byte[] errData = new byte[256];
		String gddm = getGddm(stockCode, accountId);
		if (StringUtils.isBlank(gddm)) {
			throw new TradeException("当前用户没有开通改股票所在市场的帐号");
		}

		for (int i = 0; i < LOOP_TIMES; i++) {
			TdxClient tdxClient = null;
			try {
				// 这里不用考虑连接有没有用
				tdxClient = getTdxClient(accountId);
				TdxLibrary tdxLibrary = tdxClient.getTdxLibrary();
				logger.info("账户：{}，股票:{},交易状态:{}，开始交易：{}", accountId, order.getStockCode(),
						order.getOrderCate().getName(), order);
				logger.info("账户：{}，股票:{}，具体交易参数：{},{},{},{},{},{},{}", accountId, order.getStockCode(),
						tdxClient.getClientId(), orderCateId, priceCateId, gddm, stockCode, price, quantity);
				tdxLibrary.SendOrder(tdxClient.getClientId(), orderCateId, priceCateId, gddm, stockCode, price,
						quantity, rltData, errData);
				String result = Native.toString(rltData, "GBK");
				String error = Native.toString(errData, "GBK");
				error = PubUtil.repalcWarp2Blank(error);
				logger.info("账户：{}，股票:{},交易状态:{}，交易正常结果：{}，错误结果:{}", accountId, order.getStockCode(),
						order.getOrderCate().getName(), result, error);
				if (StringUtils.isBlank(error)) {
					DataTable rltDt = new DataTable(stockCode, result);
					order.setOrderNo(rltDt.get(0, 0));
					return order;
				} else {
					logger.error("第{}次调用交易服务器是失败！原因是:{}", i + 1, error);
					if (StringUtils.indexOf(error, "客户号不存在或客户号和证券账户不匹配") > -1
							|| StringUtils.indexOf(error, "证券代码表记录不存在") > -1) {
						logger.error("stockCode:{},gddm:{},", stockCode, gddm);
						destroyTdxClient(tdxClient, accountId);
					} else {
						// 普通报错直接返回
						order.setMessage(error);
						return order;
					}
					// 最后一次
					if (i == LOOP_TIMES - 1) {
						throw new TradeException("调用交易服务器失败！原因是:" + error);
					}
				}

			} catch (TradeException tradeException) {
				logger.error(tradeException.getMessage(), tradeException);
				throw tradeException;
			} catch (Exception e) {
				logger.error("调用交易服务器失败！", e);
				throw new TradeException("调用交易服务器失败！");
			} finally {
				try {
					returnTdxClient(tdxClient, accountId);
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	public static String cancelOrder(Order order) throws TradeException {
		return cancelOrder(order, 1L);
	}

	/**
	 * 撤销单子
	 *
	 * @param order
	 *            不能为null，并且必须含有属性: orderNo和stockCode
	 * @return
	 * @throws TradeException
	 */
	public static String cancelOrder(Order order, Long accountId) throws TradeException {
		Preconditions.checkArgument(order != null, "order参数不能为null.");
		Preconditions.checkArgument(StringUtils.isNotBlank(order.getOrderNo()), "委托的编号(order.orderNo)不能为null.");
		Preconditions.checkArgument(StringUtils.isNotBlank(order.getStockCode()), "证券代码(order.stockCode)不能为null.");

		String exchangeId = getExchangeId(order.getStockCode());
		byte[] rltData = new byte[1024 * 1024];
		byte[] errData = new byte[256];

		for (int i = 0; i < LOOP_TIMES; i++) {
			TdxClient tdxClient = null;
			try {
				// 这里不用考虑连接有没有用
				tdxClient = getTdxClient(accountId);
				TdxLibrary tdxLibrary = tdxClient.getTdxLibrary();
				logger.info("账户：{}，股票:{}，开始撤单：{}", accountId, order.getStockCode(), order);
				logger.info("账户：{}，股票:{}，具体撤单参数：{},{},{}", accountId, order.getStockCode(), tdxClient.getClientId(),
						exchangeId, order.getOrderNo());
				tdxLibrary.CancelOrder(tdxClient.getClientId(), exchangeId, order.getOrderNo(), rltData, errData);
				String result = Native.toString(rltData, "GBK");
				String error = Native.toString(errData, "GBK");
				error = PubUtil.repalcWarp2Blank(error);
				logger.info("账户：{}，股票:{}，撤单正常结果：{}，错误结果:{}", accountId, order.getStockCode(), result, error);
				if (StringUtils.isBlank(error)) {
					return result;
				} else {
					if (StringUtils.containsAny(error, "委托状态错误不能撤单", "该笔委托已经全部成交或全部撤单")) {
						throw new TradeException(error);
					} else {
						logger.error("第{}次调用撤单服务器是失败！原因是:{}", i + 1, error);
						destroyTdxClient(tdxClient, accountId);
						// 最后一次
						if (i == LOOP_TIMES - 1) {
							throw new TradeException("调用撤单服务器失败！原因是:" + error);
						}
					}
				}
			} catch (TradeException tradeException) {
				logger.error(tradeException.getMessage(), tradeException);
				throw tradeException;
			} catch (Exception e) {
				logger.error("调用交易服务器失败！", e);
				throw new TradeException("调用交易服务器失败！");
			} finally {
				try {
					returnTdxClient(tdxClient, accountId);
				} catch (Exception e) {
				}
			}
		}

		return null;
	}

	public static DataTable queryData(DataCate dataCate) throws TradeException {
		return queryData(dataCate, 1L);
	}

	/**
	 * 查询各种交易数据
	 */
	public static DataTable queryData(DataCate dataCate, Long accountId) throws TradeException {

		byte[] rltData = new byte[1024 * 1024];
		byte[] errData = new byte[256];

		for (int i = 0; i < LOOP_TIMES; i++) {
			TdxClient tdxClient = null;
			try {
				// 这里不用考虑连接有没有用
				tdxClient = getTdxClient(accountId);
				TdxLibrary tdxLibrary = tdxClient.getTdxLibrary();
				tdxLibrary.QueryData(tdxClient.getClientId(), dataCate.getId(), rltData, errData);
				String result = Native.toString(rltData, "GBK");
				String error = Native.toString(errData, "GBK");
				error = PubUtil.repalcWarp2Blank(error);

				if (StringUtils.isBlank(error)) {
					return new DataTable(dataCate.getName(), result);
				} else {
					logger.error("第{}次调用交易服务器是失败！原因是:{}", i + 1, error);
					if (StringUtils.indexOf(error, "连接服务器超时") > -1) {
						destroyTdxClient(tdxClient, accountId);
					}
					// 最后一次
					if (i == LOOP_TIMES - 1) {
						throw new TradeException("调用交易服务器失败！原因是:" + error);
					}
				}
			} catch (TradeException tradeException) {
				logger.error(tradeException.getMessage(), tradeException);
				throw tradeException;
			} catch (Exception e) {
				logger.error("调用交易服务器失败！", e);
				throw new TradeException("调用交易服务器失败！");
			} finally {
				try {
					returnTdxClient(tdxClient, accountId);
				} catch (Exception e) {
				}
			}
		}
		return null;

	}

	public static void logoff(Long accountId) {
		TdxClient tdxClient = null;
		try {
			tdxClient = getTdxClient(accountId);
			TdxLibrary tdxLibrary = tdxClient.getTdxLibrary();
			tdxLibrary.Logoff(tdxClient.getClientId());
		} catch (TradeException tradeException) {
			logger.error(tradeException.getMessage(), tradeException);
			throw tradeException;
		} catch (Exception e) {
			logger.error("调用交易服务器失败！", e);
			throw new TradeException("调用交易服务器失败！");
		} finally {
			try {
				returnTdxClient(tdxClient, accountId);
			} catch (Exception e) {
			}
		}

	}

	private static boolean priceCateMatchStockCode(Order order) {
		return priceCateMatchStockCode(order.getPriceCate(), order.getStockCode());
	}

	private static boolean priceCateMatchStockCode(PriceCate priceCate, String stockCode) {
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

	// TODO 改进，支持其他市场
	private static synchronized String getGddm(String stockCode, Long accountId) throws TradeException {
		if (!GDDM_MAP.containsKey(accountId)) {
			GDDM_MAP.put(accountId, queryData(DataCate.GDDM, accountId));
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
		GDDM_MAP.get(accountId).browse(row -> {
			if (accountType.equals(row[2])) {
				sb.append(row[0]);
				return false;
			}
			return true;
		});
		return sb.toString();
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

}

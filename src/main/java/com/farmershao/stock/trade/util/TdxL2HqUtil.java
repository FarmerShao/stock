package com.farmershao.stock.trade.util;

import com.farmershao.stock.trade.enums.ExchangeId;
import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.model.suport.DataTable;
import com.farmershao.stock.trade.tradex.TdxL2HqClient;
import com.google.common.base.Preconditions;
import com.sun.jna.Native;
import com.sun.jna.ptr.ShortByReference;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * L2行情查詢接口
 * <p>
 * Created by kongkp on 2017/1/7.
 */
public class TdxL2HqUtil extends TdxL2HqPollManager {
	private static final Logger logger = LoggerFactory.getLogger(TdxL2HqUtil.class);

	/**
	 * 方法介绍: 获取深圳逐笔委托某个范围内的数据</br>
	 * 注意事项：无</br>
	 * 创建日期: 2017年2月26日 下午8:33:19</br>
	 *
	 * -----------------------------------</br>
	 * 修改原因:</br>
	 * 修改日期:</br>
	 * 修改人：</br>
	 * -----------------------------------</br>
	 *
	 * @author： 罗成</br>
	 * 
	 * @param stockCode
	 * @param start
	 * @param count
	 * @return
	 * @throws TradeException
	 */
	public DataTable getTransactionData(String stockCode, int start, int count) throws TradeException {
		Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
		ExchangeId market = StockUtils.getExchangeId(stockCode);
		Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

		byte[] errData = new byte[256];
		byte[] rltData = new byte[1024 * 1024];
		ShortByReference countRef = new ShortByReference((short) count);

		for (int i = 0; i < LOOP_TIMES; i++) {
			TdxL2HqClient tdxL2HqClient = null;
			try {
				// 这里不用考虑连接有没有用
				tdxL2HqClient = getTdxL2HqClient();
				tdxL2HqClient.getTdxLibrary().TdxL2Hq_GetTransactionData(tdxL2HqClient.getConnId(), market.getByteId(),
						stockCode, (short) start, countRef, rltData, errData);
				DataTable dataTable = dealResult(errData, rltData,
						stockCode + "分笔数据(" + start + "~" + (start + count) + ")", i, tdxL2HqClient);
				if (dataTable != null) {
					return dataTable;
				}

			} catch (Exception e) {
				logger.error("调用L2行情异常！", e);
				throw new TradeException("调用L2行情异常！");
			} finally {
				try {
					returnTdxL2HqClient(tdxL2HqClient);
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	/**
	 * 方法介绍: 获取深圳逐笔委托某个范围内的数据</br>
	 * 注意事项：无</br>
	 * 创建日期: 2017年2月26日 下午8:33:19</br>
	 *
	 * -----------------------------------</br>
	 * 修改原因:</br>
	 * 修改日期:</br>
	 * 修改人：</br>
	 * -----------------------------------</br>
	 *
	 * @author： 罗成</br>
	 * 
	 * @param market
	 * @param stockCode
	 * @param start
	 * @param count
	 * @return
	 * @throws TradeException
	 */
	public static DataTable getDetailOrderData(ExchangeId market, String stockCode, int start, int count)
			throws TradeException {
		Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
		Preconditions.checkState(start >= 0, "start不能小小于0");
		Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

		byte[] errData = new byte[256];
		byte[] rltData = new byte[1024 * 1024];
		ShortByReference countRef = new ShortByReference((short) count);

		for (int i = 0; i < LOOP_TIMES; i++) {
			TdxL2HqClient tdxL2HqClient = null;
			try {
				// 这里不用考虑连接有没有用
				tdxL2HqClient = getTdxL2HqClient();
				tdxL2HqClient.getTdxLibrary().TdxL2Hq_GetDetailOrderData(tdxL2HqClient.getConnId(), market.getByteId(),
						stockCode, (short) start, countRef, rltData, errData);
				DataTable dataTable = dealResult(errData, rltData,
						stockCode + "分笔数据(" + start + "~" + (start + count) + ")", i, tdxL2HqClient);
				if (dataTable != null) {
					return dataTable;
				}
			} catch (Exception e) {
				logger.error("调用L2行情异常！", e);
				throw new TradeException("调用L2行情异常！");
			} finally {
				try {
					returnTdxL2HqClient(tdxL2HqClient);
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	/**
	 * 方法介绍: 获取深圳逐笔委托某个范围内的数据</br>
	 * 注意事项：无</br>
	 * 创建日期: 2017年2月26日 下午8:33:19</br>
	 *
	 * -----------------------------------</br>
	 * 修改原因:</br>
	 * 修改日期:</br>
	 * 修改人：</br>
	 * -----------------------------------</br>
	 *
	 * @author： 罗成</br>
	 * 
	 * @param market
	 * @param stockCode
	 * @param start
	 * @param count
	 * @return
	 * @throws TradeException
	 */
	public static DataTable getBuySellQueue(ExchangeId market, String stockCode, int start, int count)
			throws TradeException {
		Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
		Preconditions.checkState(start >= 0, "start不能小小于0");
		Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

		byte[] errData = new byte[256];
		byte[] rltData = new byte[1024 * 1024];

		for (int i = 0; i < LOOP_TIMES; i++) {
			TdxL2HqClient tdxL2HqClient = null;
			try {
				// 这里不用考虑连接有没有用
				tdxL2HqClient = getTdxL2HqClient();
				tdxL2HqClient.getTdxLibrary().TdxL2Hq_GetBuySellQueue(tdxL2HqClient.getConnId(), market.getByteId(),
						stockCode, rltData, errData);
				DataTable dataTable = dealResult(errData, rltData,
						stockCode + "逐笔成交(" + start + "~" + (start + count) + ")", i, tdxL2HqClient);
				if (dataTable != null) {
					return dataTable;
				}
			} catch (Exception e) {
				logger.error("调用L2行情异常！", e);
				throw new TradeException("调用L2行情异常！");
			} finally {
				try {
					returnTdxL2HqClient(tdxL2HqClient);
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	/**
	 * 方法介绍: 获取买卖队列数据</br>
	 * 注意事项：无</br>
	 * 创建日期: 2017年2月26日 下午8:33:19</br>
	 *
	 * -----------------------------------</br>
	 * 修改原因:</br>
	 * 修改日期:</br>
	 * 修改人：</br>
	 * -----------------------------------</br>
	 *
	 * @author： 罗成</br>
	 * 
	 * @param market
	 * @param stockCode
	 * @param start
	 * @param count
	 * @return
	 * @throws TradeException
	 */
	public static DataTable GetDetailTransactionData(ExchangeId market, String stockCode, int start, int count)
			throws TradeException {
		Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
		Preconditions.checkState(start >= 0, "start不能小小于0");
		Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

		byte[] errData = new byte[256];
		byte[] rltData = new byte[1024 * 1024];
		ShortByReference countRef = new ShortByReference((short) count);

		for (int i = 0; i < LOOP_TIMES; i++) {
			TdxL2HqClient tdxL2HqClient = null;
			try {
				// 这里不用考虑连接有没有用
				tdxL2HqClient = getTdxL2HqClient();
				tdxL2HqClient.getTdxLibrary().TdxL2Hq_GetDetailTransactionData(tdxL2HqClient.getConnId(),
						market.getByteId(), stockCode, start, countRef, rltData, errData);
				DataTable dataTable = dealResult(errData, rltData,
						stockCode + "买卖队列(" + start + "~" + (start + count) + ")", i, tdxL2HqClient);
				if (dataTable != null) {
					return dataTable;
				}
			} catch (Exception e) {
				logger.error("调用L2行情异常！", e);
				throw new TradeException("调用L2行情异常！");
			} finally {
				try {
					returnTdxL2HqClient(tdxL2HqClient);
				} catch (Exception e) {
				}
			}
		}
		return null;

	}

	/**
	 * 批量获取多个证券的五档报价数据
	 *
	 * @param stockCodes
	 *            要查询的证券代码(最大50，不同券商可能不一样,具体数目请自行咨询券商或测试)
	 * @return 执行后, 返回返回的Quotes数据，如果stockCodes数量太多，那么返回的数量将不会有那么多
	 * @throws TradeException
	 */
	public static DataTable getSecurityQuotes10(List<String> stockCodes) throws TradeException {
		Preconditions.checkState(stockCodes != null, "stockCodes参数不能为null.");
		Preconditions.checkState(stockCodes.size() > 0, "stockCodes个数必须大于0.");
		return getSecurityQuotes10(stockCodes.toArray(new String[stockCodes.size()]));
	}

	public static DataTable getSecurityQuotes10(String... stockCodes) throws TradeException {
		Preconditions.checkState(stockCodes != null, "stockCodes参数不能为null.");
		Preconditions.checkState(stockCodes.length > 0, "stockCodes个数必须大于0.");

		int count = stockCodes.length;
		byte[] markets = getExchangeIds(stockCodes);
		ShortByReference countRef = new ShortByReference((short) count);
		byte[] errData = new byte[256];
		byte[] rltData = new byte[1024 * 1024];

		for (int i = 0; i < LOOP_TIMES; i++) {
			TdxL2HqClient tdxL2HqClient = null;
			try {
				// 这里不用考虑连接有没有用
				tdxL2HqClient = getTdxL2HqClient();
				tdxL2HqClient.getTdxLibrary().TdxL2Hq_GetSecurityQuotes10(tdxL2HqClient.getConnId(), markets,
						stockCodes, countRef, rltData, errData);
				DataTable dataTable = dealResult(errData, rltData, "SecurityQuotes", i, tdxL2HqClient);
				if (dataTable != null) {
					return dataTable;
				}
			} catch (Exception e) {
				logger.error("调用L2行情异常！", e);
				throw new TradeException("调用L2行情异常！");
			} finally {
				try {
					returnTdxL2HqClient(tdxL2HqClient);
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	private static DataTable dealResult(byte[] errData, byte[] rltData, String queryName, int index,
			TdxL2HqClient tdxL2HqClient) throws Exception {
		String error = Native.toString(errData, "GBK");
		String result = Native.toString(rltData, "GBK");
		if (StringUtils.isBlank(error)) {
			// 成功
			return new DataTable(queryName, result);
		} else {
			logger.error("第{}次调用L2行情是失败！原因是:{}", index + 1, error);
			if (StringUtils.indexOf(error, "请重连服务器") > -1 || StringUtils.indexOf(error, "高级行情用户验证失败") > -1
					|| StringUtils.indexOf(error, "其它地方登录行情") > -1 || StringUtils.indexOf(error, "无效行情连接") > -1) {
				destroyTdxL2HqClient(tdxL2HqClient);
			}
			// 最后一次
			if (index == LOOP_TIMES - 1) {
				throw new TradeException("调用L2行请失败！原因是:" + error);
			}
		}
		return null;
	}

	private static byte[] getExchangeIds(String[] stockCodes) {
		byte[] exchangeIds = new byte[stockCodes.length];
		for (int i = 0, len = exchangeIds.length; i < len; i++) {
			exchangeIds[i] = getExchangeId(stockCodes[i]);
		}
		return exchangeIds;
	}

	private static byte getExchangeId(String stockCode) {
		String market = StockUtils.getMarket(stockCode);
		if (market.startsWith("SH.")) {
			return ExchangeId.SH.getByteId();
		} else if (market.startsWith("SZ.")) {
			return ExchangeId.SZ.getByteId();
		}
		return -1;
	}
}

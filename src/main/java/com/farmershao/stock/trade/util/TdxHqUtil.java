package com.farmershao.stock.trade.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.farmershao.stock.trade.enums.ExchangeId;
import com.farmershao.stock.trade.enums.KCate;
import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.model.suport.DataTable;
import com.farmershao.stock.trade.model.suport.F10Cates;
import com.farmershao.stock.trade.tradex.TdxHqClient;
import com.farmershao.stock.trade.tradex.TdxLibrary;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.sun.jna.Native;
import com.sun.jna.ptr.ShortByReference;

/**
 * 行情查詢接口
 * <p>
 * Created by kongkp on 2017/1/7.
 */
public class TdxHqUtil extends TdxHqPollManager {
	private static final Logger logger = LoggerFactory.getLogger(TdxHqUtil.class);

	// 所有连接的list
	private static final List<String[]> CONNECT_LIST = new ArrayList<>();
	// 已经连接的connId到IP的路由
	private static final Map<Integer, String> USED_CONNECT_MAP = new HashMap<>();
	// 所有已经连接的IP
	private static final Set<String> USED_CONNECT_SET = new HashSet<>();
	// 需要重连错误信息枚举
	private static final String[] NEED_RECONNECT_ERROR = { "连接服务器超时", "请重连服务器", "解压失败", "接收数据包不完整", "无效行情连接" };

	static {
		// 初始化线程池
		initPoolConnect();
	}

	private static void initPoolConnect() {
		String server = Conf.get("tdx.hq.server");
		String[] ss = StringUtils.split(server, ",;| ");
		for (String s : ss) {
			String[] hostPort = StringUtils.split(s, ':');
			CONNECT_LIST.add(hostPort);
		}
	}

	/**
	 * 获取财务信息
	 *
	 * @param stockCode
	 *            证券代码
	 * @return 财务数据
	 * @throws TradeException
	 */
	public static DataTable getFinanceInfo(String stockCode) throws TradeException {
		Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
		ExchangeId market = StockUtils.getExchangeId(stockCode);
		Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

		byte[] errData = new byte[256];
		byte[] rltData = new byte[1024 * 1024];

		for (int i = 0; i < LOOP_TIMES; i++) {
			TdxHqClient tdxHqClient = null;
			try {
				// 这里不用考虑连接有没有用
				tdxHqClient = getTdxHqClient();
				TdxLibrary tdxLibrary = tdxHqClient.getTdxLibrary();
				tdxLibrary.TdxHq_GetFinanceInfo(tdxHqClient.getConnId(), market.getByteId(), stockCode, rltData,
						errData);

				String result = Native.toString(rltData, "GBK");
				String error = Native.toString(errData, "GBK");
				error = PubUtil.repalcWarp2Blank(error);

				if (StringUtils.isBlank(error)) {
					return new DataTable("(" + stockCode + ")财务信息", result);
				} else {
					logger.error("第{}次调用l1行情服务器是失败！原因是:{}", i + 1, error);
					if (StringUtils.containsAny(error, NEED_RECONNECT_ERROR)) {
						destroyTdxHqClient(tdxHqClient);
					}
					// 最后一次
					if (i == LOOP_TIMES - 1) {
						throw new TradeException("调用l1行情服务器失败！原因是:" + error);
					}
				}
			} catch (TradeException tradeException) {
				logger.error(tradeException.getMessage(), tradeException);
				throw tradeException;
			} catch (Exception e) {
				logger.error("调用l1行情服务器失败！", e);
				throw new TradeException("调用l1行情器失败！");
			} finally {
				try {
					returnTdxHqClient(tdxHqClient);
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	/**
	 * 获取市场内所有证券的数量
	 *
	 * @param market
	 *            市场ID
	 * @return 证券数量
	 * @throws TradeException
	 */
	public static int getSecurityCount(ExchangeId market) throws TradeException {
		Preconditions.checkState(market != null, "market参数不能为null.");

		byte[] errData = new byte[256];
		byte[] rltData = new byte[1024 * 1024];

		for (int i = 0; i < LOOP_TIMES; i++) {
			TdxHqClient tdxHqClient = null;
			try {
				// 这里不用考虑连接有没有用
				tdxHqClient = getTdxHqClient();
				TdxLibrary tdxLibrary = tdxHqClient.getTdxLibrary();
				ShortByReference count = new ShortByReference((short) 0);

				tdxLibrary.TdxHq_GetSecurityCount(tdxHqClient.getConnId(), market.getByteId(), count, errData);

				String result = Native.toString(rltData, "GBK");
				String error = Native.toString(errData, "GBK");
				error = PubUtil.repalcWarp2Blank(error);

				if (StringUtils.isBlank(error)) {
					return (int) count.getValue();
				} else {
					logger.error("第{}次调用l1行情服务器是失败！原因是:{}", i + 1, error);
					if (StringUtils.containsAny(error, NEED_RECONNECT_ERROR)) {
						destroyTdxHqClient(tdxHqClient);
					}
					// 最后一次
					if (i == LOOP_TIMES - 1) {
						throw new TradeException("调用l1行情服务器失败！原因是:" + error);
					}
				}
			} catch (TradeException tradeException) {
				logger.error(tradeException.getMessage(), tradeException);
				throw tradeException;
			} catch (Exception e) {
				logger.error("调用l1行情服务器失败！", e);
				throw new TradeException("调用l1行情器失败！");
			} finally {
				try {
					returnTdxHqClient(tdxHqClient);
				} catch (Exception e) {
				}
			}
		}
		return 0;

	}

	/**
	 * 获取市场内某个范围内的1000支股票的股票代码
	 *
	 *
	 * @param start
	 *            范围开始位置,第一个股票是0, 第二个是1, 依此类推,位置信息依据getSecurityCount返回的证券总数确定
	 * @return 股票列表
	 */
	public static DataTable getSecurityList(ExchangeId market, int start, int count) throws TradeException {
		// 这里注意 count 输入没用 不管怎么样都是1000条，暂时不管
		Preconditions.checkState(market != null, "market参数不能为null.");
		Preconditions.checkState(start >= 0, "start不能小小于0");

		byte[] errData = new byte[256];
		byte[] rltData = new byte[1024 * 1024];

		for (int i = 0; i < LOOP_TIMES; i++) {
			TdxHqClient tdxHqClient = null;
			try {
				// 这里不用考虑连接有没有用
				tdxHqClient = getTdxHqClient();
				TdxLibrary tdxLibrary = tdxHqClient.getTdxLibrary();
				ShortByReference countRef = new ShortByReference((short) count);
				tdxLibrary.TdxHq_GetSecurityList(tdxHqClient.getConnId(), market.getByteId(), (short) start, countRef,
						rltData, errData);
				String result = Native.toString(rltData, "GBK");
				String error = Native.toString(errData, "GBK");
				error = PubUtil.repalcWarp2Blank(error);

				if (StringUtils.isBlank(error)) {
					return new DataTable(
							market.getName() + "(" + start + "~" + (start + countRef.getValue()) + ")" + "股票列表",
							result);
				} else {
					logger.error("第{}次调用l1行情服务器是失败！原因是:{}", i + 1, error);
					if (StringUtils.containsAny(error, NEED_RECONNECT_ERROR)) {
						destroyTdxHqClient(tdxHqClient);
					}
					// 最后一次
					if (i == LOOP_TIMES - 1) {
						throw new TradeException("调用l1行情服务器失败！原因是:" + error);
					}
				}
			} catch (TradeException tradeException) {
				logger.error(tradeException.getMessage(), tradeException);
				throw tradeException;
			} catch (Exception e) {
				logger.error("调用l1行情服务器失败！", e);
				throw new TradeException("调用l1行情器失败！");
			} finally {
				try {
					returnTdxHqClient(tdxHqClient);
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	/**
	 * 获取F10资料的某一分类的内容
	 *
	 * @param stockCode
	 *            股票代碼
	 * @param cate
	 *            取值为：最新提示、公司概况、财务分析、股东研究、股本结构、 资本运作、业内点评、行业分析、公司大事、港澳特色、经营分析、
	 *            主力追踪、分红扩股、高层治理、龙虎榜单、关联个股
	 * @return F10资料的指定分类的内容
	 * @throws TradeException
	 */
	public static String getCompanyInfoContent(String stockCode, String cate) throws TradeException {
		F10Cates f10Cates = getCompanyInfoCategory(stockCode);
		F10Cates.Index index = f10Cates.getIndex(StringUtils.trim(cate));
		if (index == null) {
			throw new TradeException("没有找到类别(" + cate + ")的信息");
		}
		return getCompanyInfoContent(stockCode, index.getFile(), index.getStart(), index.getLength());
	}

	// TODO 后期用真实缓存替代
	private static Map<String, F10Cates> f10CatesMap = new ConcurrentHashMap<>();

	/**
	 * 获取F10资料的分类
	 *
	 * @param stockCode
	 *            股票代码
	 * @return F10资料数据
	 * @throws TradeException
	 */
	public static F10Cates getCompanyInfoCategory(String stockCode) throws TradeException {
		Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
		ExchangeId market = StockUtils.getExchangeId(stockCode);
		Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

		F10Cates cached = f10CatesMap.get(stockCode);
		if (cached == null) {
			byte[] errData = new byte[256];
			byte[] rltData = new byte[1024 * 1024];

			for (int i = 0; i < LOOP_TIMES; i++) {
				TdxHqClient tdxHqClient = null;
				try {
					// 这里不用考虑连接有没有用
					tdxHqClient = getTdxHqClient();
					TdxLibrary tdxLibrary = tdxHqClient.getTdxLibrary();
					tdxLibrary.TdxHq_GetCompanyInfoCategory(tdxHqClient.getConnId(), market.getByteId(), stockCode,
							rltData, errData);

					String result = Native.toString(rltData, "GBK");
					String error = Native.toString(errData, "GBK");
					error = PubUtil.repalcWarp2Blank(error);

					if (StringUtils.isBlank(error)) {
						return new F10Cates(stockCode, result);
					} else {
						logger.error("第{}次调用l1行情服务器是失败！原因是:{}", i + 1, error);
						if (StringUtils.containsAny(error, NEED_RECONNECT_ERROR)) {
							destroyTdxHqClient(tdxHqClient);
						}
						// 最后一次
						if (i == LOOP_TIMES - 1) {
							throw new TradeException("调用l1行情服务器失败！原因是:" + error);
						}
					}
				} catch (TradeException tradeException) {
					logger.error(tradeException.getMessage(), tradeException);
					throw tradeException;
				} catch (Exception e) {
					logger.error("调用l1行情服务器失败！", e);
					throw new TradeException("调用l1行情器失败！");
				} finally {
					try {
						returnTdxHqClient(tdxHqClient);
					} catch (Exception e) {
					}
				}
			}
			f10CatesMap.put(stockCode, cached);
		}
		return cached;
	}

	/**
	 * 获取F10资料的某一分类的内容
	 *
	 * @param stockCode
	 *            证券代码
	 * @param filename
	 *            文件名
	 * @param start
	 *            内容在文件里起始位置
	 * @param length
	 *            内容长度
	 * @return F10某一分类资料内容
	 * @throws TradeException
	 */
	private static String getCompanyInfoContent(String stockCode, String filename, int start, int length)
			throws TradeException {
		Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
		ExchangeId market = StockUtils.getExchangeId(stockCode);
		Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

		byte[] errData = new byte[256];
		byte[] rltData = new byte[1024 * 1024];

		for (int i = 0; i < LOOP_TIMES; i++) {
			TdxHqClient tdxHqClient = null;
			try {
				// 这里不用考虑连接有没有用
				tdxHqClient = getTdxHqClient();
				TdxLibrary tdxLibrary = tdxHqClient.getTdxLibrary();
				tdxLibrary.TdxHq_GetCompanyInfoContent(tdxHqClient.getConnId(), market.getByteId(), stockCode, filename,
						start, length, rltData, errData);
				String result = Native.toString(rltData, "GBK");
				String error = Native.toString(errData, "GBK");
				error = PubUtil.repalcWarp2Blank(error);

				if (StringUtils.isBlank(error)) {
					return result;
				} else {
					logger.error("第{}次调用l1行情服务器是失败！原因是:{}", i + 1, error);
					if (StringUtils.containsAny(error, NEED_RECONNECT_ERROR)) {
						destroyTdxHqClient(tdxHqClient);
					}
					// 最后一次
					if (i == LOOP_TIMES - 1) {
						throw new TradeException("调用l1行情服务器失败！原因是:" + error);
					}
				}
			} catch (TradeException tradeException) {
				logger.error(tradeException.getMessage(), tradeException);
				throw tradeException;
			} catch (Exception e) {
				logger.error("调用l1行情服务器失败！", e);
				throw new TradeException("调用l1行情器失败！");
			} finally {
				try {
					returnTdxHqClient(tdxHqClient);
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
	public static DataTable getSecurityQuotes(List<String> stockCodes) throws TradeException {
		Preconditions.checkState(stockCodes != null, "stockCodes参数不能为null.");
		Preconditions.checkState(stockCodes.size() > 0, "stockCodes个数必须大于0.");
		return getSecurityQuotes(stockCodes.toArray(new String[stockCodes.size()]));
	}

	public static DataTable getSecurityQuotes(String... stockCodes) throws TradeException {
		Preconditions.checkState(stockCodes != null, "stockCodes参数不能为null.");
		Preconditions.checkState(stockCodes.length > 0, "stockCodes个数必须大于0.");

		int count = stockCodes.length;
		byte[] markets = getExchangeIds(stockCodes);
		ShortByReference countRef = new ShortByReference((short) count);
		byte[] errData = new byte[256];
		byte[] rltData = new byte[1024 * 1024];

		for (int i = 0; i < LOOP_TIMES; i++) {
			TdxHqClient tdxHqClient = null;
			try {
				// 这里不用考虑连接有没有用
				tdxHqClient = getTdxHqClient();
				TdxLibrary tdxLibrary = tdxHqClient.getTdxLibrary();
				tdxLibrary.TdxHq_GetSecurityQuotes(tdxHqClient.getConnId(), markets, stockCodes, countRef, rltData,
						errData);

				String result = Native.toString(rltData, "GBK");
				String error = Native.toString(errData, "GBK");
				error = PubUtil.repalcWarp2Blank(error);

				if (StringUtils.isBlank(error)) {
					return new DataTable("SecurityQuotes", result);
				} else {
					logger.error("第{}次调用l1行情服务器是失败！原因是:{}", i + 1, error);
					if (StringUtils.containsAny(error, NEED_RECONNECT_ERROR)) {
						destroyTdxHqClient(tdxHqClient);
					}
					// 最后一次
					if (i == LOOP_TIMES - 1) {
						throw new TradeException("调用l1行情服务器失败！原因是:" + error);
					}
				}
			} catch (TradeException tradeException) {
				logger.error(tradeException.getMessage(), tradeException);
				throw tradeException;
			} catch (Exception e) {
				logger.error("调用l1行情服务器失败！", e);
				throw new TradeException("调用l1行情器失败！");
			} finally {
				try {
					returnTdxHqClient(tdxHqClient);
				} catch (Exception e) {
				}
			}
		}

		return null;
	}

	/**
	 * 获取证券指定范围的的K线数据
	 *
	 * @param cCate
	 *            K线类别
	 * @param stockCode
	 *            证券代码
	 * @param start
	 *            范围的开始位置,最后一条K线位置是0, 前一条是1, 依此类推
	 * @param count
	 *            范围的大小, 实际返回的K线数目由DataTable返回的行数确定
	 * @return K 线数据
	 * @throws TradeException
	 */
	public synchronized static DataTable getSecurityBars(KCate cCate, String stockCode, int start, int count)
			throws TradeException {
		// 暂时单线程
		Preconditions.checkState(cCate != null, "cCate参数不能为null.");
		Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
		Preconditions.checkState(start >= 0, "start不能小小于0");
		ExchangeId market = StockUtils.getExchangeId(stockCode);
		Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

		byte[] errData = new byte[256];
		byte[] rltData = new byte[1024 * 1024];

		for (int i = 0; i < LOOP_TIMES; i++) {
			TdxHqClient tdxHqClient = null;
			try {
				// 这里不用考虑连接有没有用
				tdxHqClient = getTdxHqClient();
				TdxLibrary tdxLibrary = tdxHqClient.getTdxLibrary();
				ShortByReference countRef = new ShortByReference((short) count);
				tdxLibrary.TdxHq_GetSecurityBars(tdxHqClient.getConnId(), cCate.getByteId(), market.getByteId(),
						stockCode, (short) start, countRef, rltData, errData);

				String result = Native.toString(rltData, "GBK");
				String error = Native.toString(errData, "GBK");
				error = PubUtil.repalcWarp2Blank(error);

				if (StringUtils.isBlank(error)) {
					return new DataTable(
							stockCode + " " + cCate.getName() + "(" + start + "~" + (start + countRef.getValue()) + ")",
							result);
				} else {
					logger.error("第{}次调用l1行情服务器是失败！原因是:{}", i + 1, error);
					if (StringUtils.containsAny(error, NEED_RECONNECT_ERROR)) {
						destroyTdxHqClient(tdxHqClient);
					}
					// 最后一次
					if (i == LOOP_TIMES - 1) {
						throw new TradeException("调用l1行情服务器失败！原因是:" + error);
					}
				}
			} catch (TradeException tradeException) {
				logger.error(tradeException.getMessage(), tradeException);
				throw tradeException;
			} catch (Exception e) {
				logger.error("调用l1行情服务器失败！", e);
				throw new TradeException("调用l1行情器失败！");
			} finally {
				try {
					returnTdxHqClient(tdxHqClient);
				} catch (Exception e) {
				}
			}
		}

		return null;
	}

	/**
	 * 方法介绍: 同时只能一个服务器申请连接 后续如果遇到瓶颈 这里可以修改</br>
	 * 注意事项：无</br>
	 * 创建日期: 2017年3月27日 下午8:43:47</br>
	 *
	 * -----------------------------------</br>
	 * 修改原因:</br>
	 * 修改日期:</br>
	 * 修改人：</br>
	 * -----------------------------------</br>
	 *
	 * @author： 罗成</br>
	 * 
	 * @return
	 */
	public static synchronized TdxHqClient connect() {
		byte[] rltData = new byte[256];
		byte[] errData = new byte[256];
		TdxHqClient tdxHqClient = TdxHqClient.single();

		// int lastIndex = 0;
		for (String[] host : CONNECT_LIST) {
			String hostStr = host[0];
			if (!USED_CONNECT_SET.contains(hostStr)) {
				int connIdinner = tdxHqClient.getTdxLibrary().TdxHq_Connect(hostStr, PubUtil.toShort(host[1]), rltData,
						errData);
				if (connIdinner > 0) {
					tdxHqClient.setConnId(connIdinner);
					USED_CONNECT_SET.add(hostStr);
					USED_CONNECT_MAP.put(connIdinner, hostStr);
					logger.info("连接行情服务器连接:" + hostStr + ":" + host[1] + ")成功!");
					break;
				} else {
					// 第一次连接永远报错，默认任何服务器先进来连接两次再说
					connIdinner = tdxHqClient.getTdxLibrary().TdxHq_Connect(hostStr, PubUtil.toShort(host[1]), rltData,
							errData);
					if (connIdinner > 0) {
						tdxHqClient.setConnId(connIdinner);
						USED_CONNECT_SET.add(hostStr);
						USED_CONNECT_MAP.put(connIdinner, hostStr);
						logger.info("连接行情服务器连接:" + hostStr + ":" + host[1] + "成功!");
						break;
					} else {
						logger.warn("连接行情服务器连接:" + hostStr + ":" + host[1] + "失败!");
					}
				}
			}
		}

		// // 重新排序 最近用过的在最后
		// LinkedList<String[]> tempList = new LinkedList<>();
		// for (int i = 0; i <= lastIndex; i++) {
		// tempList.addLast(CONNECT_LIST.get(i));
		// }
		// for (int i = CONNECT_LIST.size() - 1; i > lastIndex; i--) {
		// tempList.addFirst(CONNECT_LIST.get(i));
		// }
		// CONNECT_LIST.clear();
		// CONNECT_LIST.addAll(tempList);

		if (tdxHqClient.getConnId() == 0) {
			logger.info("连接所有行情服务器失败！");
		}
		return tdxHqClient;
	}

	/**
	 * 方法介绍: 断开连接</br>
	 * 注意事项：无</br>
	 * 创建日期: 2017年3月27日 下午9:11:04</br>
	 *
	 * -----------------------------------</br>
	 * 修改原因:</br>
	 * 修改日期:</br>
	 * 修改人：</br>
	 * -----------------------------------</br>
	 *
	 * @author： 罗成</br>
	 * 
	 * @param tdxHqClient
	 */
	public static void disconnect(TdxHqClient tdxHqClient) {
		String hostStr = USED_CONNECT_MAP.get(tdxHqClient.getConnId());
		USED_CONNECT_SET.remove(hostStr);
		USED_CONNECT_MAP.remove(tdxHqClient.getConnId());
		tdxHqClient.getTdxLibrary().TdxHq_Disconnect(tdxHqClient.getConnId());
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

package com.farmershao.stock.trade.tradex;

import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.model.domain.AccountDO;
import com.farmershao.stock.trade.model.domain.StaticParameter;
import com.farmershao.stock.trade.util.Conf;
import com.farmershao.stock.trade.util.DllLoader;
import com.google.common.base.Preconditions;
import com.sun.jna.Native;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通达信交易接口入口类
 * <p>
 * Created by kongkp on 2017/1/5.
 */
public class TdxTrade {
	private static Logger LOGGER = LoggerFactory.getLogger(TdxTrade.class);

	// public static final TdxLibrary lib = (TdxLibrary)
	// Native.loadLibrary("TradeX", TdxLibrary.class);
	private static boolean open = false;

	private static class TdxTradeHolder {
		private static final TdxTrade single = new TdxTrade();
	}

	private static class TdxLibraryHolder {
		private static final TdxLibrary single = (TdxLibrary) Native.loadLibrary("TradeX2-M", TdxLibrary.class);
	}

	public TdxLibrary lib() {
		return TdxLibraryHolder.single;
	}

	public boolean start() {
		if (!open) {
			byte[] errInfo = new byte[256];
			int loginId = TdxLibraryHolder.single.OpenTdx((short) 14, Conf.get("tdx.clientVersion"), (byte) 12,
					(byte) 0, errInfo);
			if (loginId < 0) {
				LOGGER.error("打开通达信实例错误！原因是:{}", Native.toString(errInfo, "GBK"));
				return false;
			}
			open = true;
		}
		return true;
	}

	public synchronized void stop() {
		open = false;
		TdxLibraryHolder.single.CloseTdx();
	}

	private TdxTrade() {
		DllLoader.preload();
	}

	public static TdxTrade single() {
		return TdxTradeHolder.single;
	}

	/**
	 * 返回交易API，注：每次返回的是新的TdxClient实例，所以每次进行独立logon和logoff，它会创建自己和服务器之间的连接。
	 *
	 * @param accountKeyPrefix
	 *            account keys prefix of conf.properties
	 * @return the TdxClient for dong trading by tdx server
	 */
	public TdxClient getClient(String accountKeyPrefix) throws TradeException {
		Preconditions.checkState(open, "请先开启通达信交易(调用TdxTrade.start()方法)再执行本操作.");

		String qsServer = Conf.get(accountKeyPrefix + ".qsServer");
		String yybId = Conf.get(accountKeyPrefix + ".yybId");
		String accountNo = Conf.get(accountKeyPrefix + ".accountNo");
		String jyAccount = Conf.get(accountKeyPrefix + ".jyAccount");
		String jyPassword = Conf.get(accountKeyPrefix + ".jyPassword");
		String txPassword = Conf.get(accountKeyPrefix + ".txPassword");
		if (StringUtils.isBlank(qsServer) || !StringUtils.isNumeric(yybId) || StringUtils.isBlank(accountNo)
				|| StringUtils.isBlank(jyAccount) || StringUtils.isBlank(jyPassword)) {
			throw new IllegalArgumentException("请在conf.properties配置<" + accountKeyPrefix + ">对应的帐号属性");
		}

		return getClient(qsServer, Short.valueOf(yybId), accountNo, jyAccount, jyPassword, txPassword, null, null);
	}

	public TdxClient getClient(Long accountId) throws TradeException {
		Preconditions.checkState(open, "请先开启通达信交易(调用TdxTrade.start()方法)再执行本操作.");

		AccountDO accountDO = StaticParameter.ACCOUNTDO_MAP.get(accountId);
		if (accountDO == null) {
			throw new IllegalArgumentException("账号:" + accountId + "数据库配置有误！");
		}
		String qsServer = accountDO.getServers();
		String yybId = accountDO.getSalesDepartmentId();
		String accountNo = accountDO.getAccountNo();
		String jyAccount = accountDO.getTradeAccountNo();
		String jyPassword = accountDO.getTradePassword();
		String txPassword = accountDO.getCommunicationPassword();
		String brokerCode = accountDO.getBrokerCode();
		String accountType = accountDO.getAccountType();

		if (StringUtils.isBlank(qsServer) || !StringUtils.isNumeric(yybId) || StringUtils.isBlank(accountNo)
				|| StringUtils.isBlank(jyAccount) || StringUtils.isBlank(jyPassword) || StringUtils.isBlank(brokerCode)
				|| StringUtils.isBlank(accountType)) {
			throw new IllegalArgumentException("账号:" + accountId + "数据库配置有误！");
		}

		return getClient(qsServer, Short.valueOf(yybId), accountNo, jyAccount, jyPassword, txPassword, brokerCode,
				accountType);
	}

	/**
	 * 返回交易API，注：每次返回的是新的TdxClient实例，所以每次进行独立logon和logoff，它会创建自己和服务器之间的连接。
	 *
	 * @param qsServer
	 * @param yybId
	 * @param accountNo
	 * @param jyAccount
	 * @param jyPassword
	 * @param txPassword
	 * @return
	 * @throws TradeException
	 */
	public TdxClient getClient(String qsServer, short yybId, String accountNo, String jyAccount, String jyPassword,
			String txPassword, String brokerCode, String accountType) throws TradeException {
		String clientVersion = Conf.get("tdx.clientVersion");
		if (StringUtils.isBlank(clientVersion)) {
			throw new IllegalStateException("conf.properties必须配置tdx.clientVersion参数.");
		}
		return new TdxClient(lib(), qsServer, yybId, clientVersion, accountNo, jyAccount, jyPassword, txPassword,
				brokerCode, accountType);
	}

	/**
	 * 暂没实现
	 *
	 * @return
	 */
	public TdxClientFed getClientFed() {
		Preconditions.checkState(open, "请先开启通达信交易(调用TdxTrade.start()方法)再执行本操作.");
		return new TdxClientFed(lib());
	}

}

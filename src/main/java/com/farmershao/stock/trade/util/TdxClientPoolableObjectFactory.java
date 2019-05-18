package com.farmershao.stock.trade.util;

import com.farmershao.stock.trade.tradex.TdxClient;
import com.farmershao.stock.trade.tradex.TdxTrade;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能介绍: 支持jodd线程池</br>
 * 创建日期: 2016年12月29日 下午4:01:30</br>
 *
 * @author： 罗成</br>
 */
public class TdxClientPoolableObjectFactory implements PooledObjectFactory<TdxClient> {
	private static Logger logger = LoggerFactory.getLogger(TdxClientPoolableObjectFactory.class);
	private Long accountId;

	public TdxClientPoolableObjectFactory(Long accountId) {
		this.accountId = accountId;
	}

	@Override
	public PooledObject<TdxClient> makeObject() throws Exception {
		TdxTrade tdxTrade = TdxTrade.single();
		if (!tdxTrade.start()) {
			logger.error("打开失败！");
			throw new Exception("打开通达信客户端失败！");
		}
		TdxClient tdxClient = tdxTrade.getClient(accountId);
		tdxClient.logon();
		PooledObject<TdxClient> pooledObject = new DefaultPooledObject<>(tdxClient);
		logger.info("账号:{},连接通达信服务器成功!", accountId);
		return pooledObject;

	}

	@Override
	public void destroyObject(PooledObject<TdxClient> p) throws Exception {
		// 这里先关闭连接
		TdxClient tdxClient = p.getObject();
		if (tdxClient != null) {
			try {
				tdxClient.logoff();
			} catch (Exception e) {
			}
		}
		// 再销毁对象
		p.invalidate();
		logger.info("账号:{},关闭通达信服务器!", accountId);
	}

	@Override
	public boolean validateObject(PooledObject<TdxClient> p) {
		return true;
	}

	@Override
	public void activateObject(PooledObject<TdxClient> p) throws Exception {
	}

	@Override
	public void passivateObject(PooledObject<TdxClient> p) throws Exception {
	}

}

package com.farmershao.stock.trade.util;

import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.tradex.TdxHqClient;
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
public class TdxHqPoolableObjectFactory implements PooledObjectFactory<TdxHqClient> {
	private static Logger logger = LoggerFactory.getLogger(TdxHqPoolableObjectFactory.class);

	@Override
	public PooledObject<TdxHqClient> makeObject() throws Exception {
		TdxHqClient tdxHqClient = TdxHqUtil.connect();
		if (tdxHqClient.getConnId() == 0) {
			throw new TradeException("创建连接池失败！");
		}
		PooledObject<TdxHqClient> pooledObject = new DefaultPooledObject<>(tdxHqClient);
		logger.info("连接l1行情成功");
		return pooledObject;

	}

	@Override
	public void destroyObject(PooledObject<TdxHqClient> p) throws Exception {
		// 这里先关闭连接
		TdxHqClient tdxHqClient = p.getObject();
		if (tdxHqClient != null) {
			try {
				TdxHqUtil.disconnect(tdxHqClient);
			} catch (Exception e) {
			}
		}
		// 再销毁对象
		p.invalidate();
		logger.info("关闭l1行情");
	}

	@Override
	public boolean validateObject(PooledObject<TdxHqClient> p) {
		return true;
	}

	@Override
	public void activateObject(PooledObject<TdxHqClient> p) throws Exception {
	}

	@Override
	public void passivateObject(PooledObject<TdxHqClient> p) throws Exception {
	}

}

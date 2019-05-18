package com.farmershao.stock.trade.util;

import com.farmershao.stock.trade.tradex.TdxL2HqClient;
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
public class TdxL2HqPoolableObjectFactory implements PooledObjectFactory<TdxL2HqClient> {
	private static Logger logger = LoggerFactory.getLogger(TdxL2HqPoolableObjectFactory.class);

	@Override
	public PooledObject<TdxL2HqClient> makeObject() throws Exception {
		TdxL2HqClient tdxL2HqClient = TdxL2HqClient.single();
		tdxL2HqClient.connect();
		PooledObject<TdxL2HqClient> pooledObject = new DefaultPooledObject<>(tdxL2HqClient);
		logger.info("连接l2行情成功");
		return pooledObject;

	}

	@Override
	public void destroyObject(PooledObject<TdxL2HqClient> p) throws Exception {
		// 这里先关闭连接
		TdxL2HqClient tdxL2HqClient = p.getObject();
		if (tdxL2HqClient != null) {
			try {
				tdxL2HqClient.disconnect();
			} catch (Exception e) {
			}
		}
		// 再销毁对象
		p.invalidate();
		logger.info("关闭l2行情");
	}

	@Override
	public boolean validateObject(PooledObject<TdxL2HqClient> p) {
		return true;
	}

	@Override
	public void activateObject(PooledObject<TdxL2HqClient> p) throws Exception {
	}

	@Override
	public void passivateObject(PooledObject<TdxL2HqClient> p) throws Exception {
	}

}

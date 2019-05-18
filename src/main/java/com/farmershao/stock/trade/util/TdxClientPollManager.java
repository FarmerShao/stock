package com.farmershao.stock.trade.util;

import com.farmershao.stock.trade.tradex.TdxClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.HashMap;
import java.util.Map;


public class TdxClientPollManager {

    // 循环次数
    protected static final int LOOP_TIMES = 3;
    // 支持多个账号
    protected volatile static Map<Long, GenericObjectPool<TdxClient>> TDX_CLIENT_POOL_MAP = new HashMap<>();

    /**
     * 方法介绍: 获取HttpConnection对象，如果有则给，没有则创建了再给</br>
     * 注意事项：无</br>
     * 创建日期: 2016年12月29日 下午6:39:50</br>
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
     * @throws Exception
     */
    protected static TdxClient getTdxClient(Long accountId) throws Exception {
        TdxClient tdxClient = null;
        if (TDX_CLIENT_POOL_MAP.containsKey(accountId)) {
            tdxClient = TDX_CLIENT_POOL_MAP.get(accountId).borrowObject();
        } else {
            // 这里直接用对象锁即可，访问的网站数量是有限的，如果后续访问网站数量剧增，考虑用key去锁
            synchronized (TdxClientPollManager.class) {
                if (TDX_CLIENT_POOL_MAP.containsKey(accountId)) {
                    tdxClient = TDX_CLIENT_POOL_MAP.get(accountId).borrowObject();
                } else {
                    TdxClientPoolableObjectFactory factory = new TdxClientPoolableObjectFactory(accountId);
                    GenericObjectPoolConfig config = new GenericObjectPoolConfig();
                    // 暂时默认最多1个 只能里连接一个 否则报错
                    config.setMaxTotal(1);
                    // 获取不到用于等待
                    config.setMaxWaitMillis(-1);
                    // 这里代表 如果超过5分钟就移除对象
                    config.setMinEvictableIdleTimeMillis(300000);
                    // 这里代表多久去检测一次移除对象,简单的说 这里最长就是60S+300S 一个对象空闲一定会被移除
                    config.setTimeBetweenEvictionRunsMillis(60000);
                    GenericObjectPool<TdxClient> genericObjectPool = new GenericObjectPool<>(factory, config);
                    TDX_CLIENT_POOL_MAP.put(accountId, genericObjectPool);
                    tdxClient = TDX_CLIENT_POOL_MAP.get(accountId).borrowObject();
                }
            }
        }
        return tdxClient;

    }

    /**
     * 方法介绍: 将HttpConnection还给线程池</br>
     * 注意事项：无</br>
     * 创建日期: 2016年12月29日 下午6:36:33</br>
     *
     * -----------------------------------</br>
     * 修改原因:</br>
     * 修改日期:</br>
     * 修改人：</br>
     * -----------------------------------</br>
     *
     * @author： 罗成</br>
     * 
     * @throws Exception
     */
    protected static void returnTdxClient(TdxClient tdxClient, Long accountId) throws Exception {
        if (TDX_CLIENT_POOL_MAP.containsKey(accountId)) {
            if (tdxClient != null) {
                TDX_CLIENT_POOL_MAP.get(accountId).returnObject(tdxClient);
            }
        }
    }

    /**
     * 方法介绍: 将HttpConnection销毁</br>
     * 注意事项：无</br>
     * 创建日期: 2016年12月29日 下午6:36:33</br>
     *
     * -----------------------------------</br>
     * 修改原因:</br>
     * 修改日期:</br>
     * 修改人：</br>
     * -----------------------------------</br>
     *
     * @author： 罗成</br>
     * 
     * @throws Exception
     */
    protected static void destroyTdxClient(TdxClient tdxClient, Long accountId) throws Exception {
        if (TDX_CLIENT_POOL_MAP.containsKey(accountId)) {
            if (tdxClient != null) {
                TDX_CLIENT_POOL_MAP.get(accountId).returnObject(tdxClient);
            }
        }
    }

}

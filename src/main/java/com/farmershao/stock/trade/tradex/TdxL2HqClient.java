package com.farmershao.stock.trade.tradex;

import com.farmershao.stock.trade.enums.ExchangeId;
import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.util.Conf;
import com.farmershao.stock.trade.util.PubUtil;
import com.farmershao.stock.trade.util.TdxClientPollManager;
import com.sun.jna.Native;
import com.sun.jna.ptr.ShortByReference;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * L2行情查詢接口
 * <p>
 * Created by kongkp on 2017/1/7.
 */
public class TdxL2HqClient extends TdxClientPollManager {
    private static final Logger logger = LoggerFactory.getLogger(TdxL2HqClient.class);

    private TdxLibrary tdxLibrary;
    private int connId = 0;

    TdxL2HqClient() {
        this.tdxLibrary = TdxTrade.single().lib();
    }

    public static TdxL2HqClient single() {
        return TdxL2HqClientHolder.single;
    }

    /**
     * 方法介绍: 打开通达信</br>
     * 注意事项：无</br>
     * 创建日期: 2017年3月19日 上午11:56:20</br>
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
     * @throws TradeException
     */
    public void connect() throws TradeException {
        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        ShortByReference countRef = new ShortByReference((short) 1);
        tdxLibrary.TdxL2Hq_GetSecurityQuotes10(connId, new byte[] {ExchangeId.SH.getByteId()}, new String[] {"601857"},
                        countRef, rltData, errData);
        String message = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        logger.info("测试L2是否需要重连：message:{},error{}", message, error);
        if (StringUtils.isBlank(error) && StringUtils.isNotBlank(message)) {
            logger.info("L2行情可以正常获取，无需重新连接！");
            return;
        }

        String serversStr = Conf.get("tdx.l2.hq.server");
        String username = Conf.get("tdx.l2.hq.username");
        String password = Conf.get("tdx.l2.hq.password");

        String[] servers = serversStr.split(",");
        for (int i = 0; i < servers.length; i++) {
            String server = servers[i];
            logger.info("开始尝试连接l2行情服务器:" + server);
            String[] hostAndport = StringUtils.split(server, ':');
            String host = hostAndport[0];
            String port = hostAndport[1];
            connId = tdxLibrary.TdxL2Hq_Connect(host, PubUtil.toShort(port), username, password, rltData, errData);
            // 连接成功 则不连接其他服务器
            if (connId >= 0) {
                logger.info("开始尝试连接l2行情服务器:" + server + "成功！行情连接是:" + connId);
                break;
            } else {
                error = Native.toString(errData, "GBK");
                logger.error("开始尝试连接l2行情服务器:{}失败！原因是:{}", server, error);
            }
        }

        if (connId < 0) {
            logger.info("连接所有L2行情服务器失败！！！！！！！！！");
        }
    }

    public void disconnect() {
        tdxLibrary.TdxL2Hq_Disconnect(connId);
    }

    public TdxLibrary getTdxLibrary() {
        return tdxLibrary;
    }

    public void setTdxLibrary(TdxLibrary tdxLibrary) {
        this.tdxLibrary = tdxLibrary;
    }

    public int getConnId() {
        return connId;
    }

    public void setConnId(int connId) {
        this.connId = connId;
    }

    private static class TdxL2HqClientHolder {
        private static final TdxL2HqClient single = new TdxL2HqClient();
    }

}

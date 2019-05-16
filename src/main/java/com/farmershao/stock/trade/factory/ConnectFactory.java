
package com.farmershao.stock.trade.factory;


import com.farmershao.stock.trade.api.TradeTdxLibrary;
import com.farmershao.stock.trade.exception.TradeException;
import com.sun.jna.Native;
import com.sun.jna.ptr.ShortByReference;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description 连接工厂类 主要用来获取连接
 * @Author 陈俊舟
 */
public class ConnectFactory {


    public static TradeTdxLibrary tdxLibrary = (TradeTdxLibrary) Native.loadLibrary("TradeX-M", TradeTdxLibrary.class);
    ;;


    /**
     * @return
     * @throws TradeException
     * @Param qsServer 行情服务器 IP:PORT
     * @Description 返回交易连接客户端代码
     */
    public synchronized Integer getTradeHqConnection(String qsServer) throws TradeException {
        String[] hosts = qsServer.split(":");
        String ip = hosts[0];
        int port = Integer.valueOf(hosts[1]);
        byte[] rltData = new byte[1024];
        byte[] errData = new byte[256];
        Integer nConnID = tdxLibrary.TdxHq_Connect(ip, (short) port, rltData, errData);
        String error = Native.toString(errData, "GBK");
        if (nConnID < 0) {
            // 失败时返回负值
            throw new TradeException(error);
        }
        return nConnID;
    }

    public synchronized  void disConnectHq(int connection) {
        try {
            tdxLibrary.TdxHq_Disconnect(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param poolNum  连接池数量
     * @param qsServer IP:PORT
     * @return
     * @throws TradeException
     */
    public synchronized List<Integer> getConnection(int poolNum, String qsServer) throws TradeException {
        List<Integer> hqList = new CopyOnWriteArrayList<>();
        //交易连接
        for (int i = 0; i < poolNum; i++) {
            try {
                //普通行情连接 时长在五分钟左右
                Integer connID = getTradeHqConnection(qsServer);
                hqList.add(connID);
            } catch (Exception e) {
                continue;
            }
        }
        return hqList;
    }


    /**
     * @param conn
     * @return
     * @Description 判断行情连接是否存在
     */
    public boolean isHqConnect(int conn) {
        boolean result = true;
        try {
            ShortByReference count = new ShortByReference((short) 0);
            byte[] errData = new byte[256];
            tdxLibrary.TdxHq_GetSecurityCount(conn, (byte) 0, count, errData);
            String error = Native.toString(errData, "GBK");
            if (StringUtils.isNotBlank(error)) {
                System.out.println(error);
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @Description 定时查询普通行情，保证扩展行情连接
     */
    public void continueHqConnect(int conn) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ShortByReference count = new ShortByReference((short) 0);
                byte[] errData = new byte[256];
                tdxLibrary.TdxHq_GetSecurityCount(conn, (byte) 0, count, errData);
                String erroInfo = Native.toString(errData, "GBK");
                if (StringUtils.isNotBlank(erroInfo)) {
                    this.cancel();
                }
            }
        }, 0, 5 * 60 * 1000);
    }

}
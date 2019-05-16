package com.farmershao.stock.trade.pool;


import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.factory.ConnectFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description 建立一个连接池，根据参数不同，调用时返回不同情况的连接
 * 1：普通行情 2：level2行情 3：扩展行情、
 * @Author chenjz
 */
public class ConnectPool {
    //存储行情连接
    private static String Server;
    //空闲普通行情连接
    public static volatile List<Integer> freeHqConnect = new CopyOnWriteArrayList<>();

    //被占用普通行情连接
    public static volatile List<Integer> activeHqConnect = new CopyOnWriteArrayList<>();


    //普通行情连接空闲数量
    public static volatile int hqFreeNum = -1;

    public static ConnectFactory connectFactory;


    public static void getConnectFactory() {
        connectFactory = new ConnectFactory();
    }

    /**
     * @throws TradeException
     * @Description 初始化方法，获取所有连接，给连接池赋值
     */
    public synchronized static void init(int poolNum, String qsServer) throws TradeException {
        Server = qsServer;
        getConnectFactory();
        freeHqConnect = connectFactory.getConnection(poolNum, qsServer);
        hqFreeNum = freeHqConnect.size();
    }

    /**
     * @return
     * @throws TradeException
     * @Description 获取连接
     */
    public synchronized static int getConnection() throws TradeException {
        Integer conn = -1;
        //判断剩余空闲连接数量  为0则把占用连接重新给空闲连接
        if (hqFreeNum < 1) {
            if (!activeHqConnect.isEmpty()) {
                freeHqConnect.clear();
                freeHqConnect.addAll(activeHqConnect);
            }
            activeHqConnect.clear();
            hqFreeNum = freeHqConnect.size();
        }
        //如果剩余连接大于0，取出连接，并把这条空闲连接定义为占用连接
        try {
            conn = freeHqConnect.get(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            Integer connID = 1;
            freeHqConnect.add(0, connID);
            conn = freeHqConnect.get(0);
            System.out.println("发生数组越界" + hqFreeNum + "，给第0个位置赋值为1");
        }
        if(hqFreeNum==1){
            freeHqConnect.clear();
        }else{
            freeHqConnect.remove(conn);
        }

        try {
            activeHqConnect.add(conn);
            //判断连接是否能用，不能用重新连接
            if (!connectFactory.isHqConnect(conn)) {
//                Integer connID = connectFactory.getTradeHqConnection(Server);
                System.out.println("连接不能用：" + conn + "  ,关闭所有连接");
                if (freeHqConnect.isEmpty()) {
                    for (Integer connection : activeHqConnect) {
                        connectFactory.disConnectHq(connection);
                    }
                } else {
                    for (Integer connection : freeHqConnect) {
                        connectFactory.disConnectHq(connection);
                    }
                }
                freeHqConnect.clear();
                activeHqConnect.clear();
                ConnectPool.init(20, Server);
                System.out.println("重新初始化线程池");
                conn = ConnectPool.getConnection();
                System.out.println("新的连接:" + conn);
                freeHqConnect.add(conn);
                //获取新的连接后，调用定时方法
//                connectFactory.continueHqConnect(conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //空闲连接被调用，数量减一
        hqFreeNum--;
        return conn;
    }

    public static void release(int conn) {
        freeHqConnect.add(conn);
        activeHqConnect.add(conn);
        hqFreeNum++;
    }

}

package com.farmershao.stock.trade.api;


import com.farmershao.stock.trade.exception.TradeException;
import com.sun.jna.Native;
import org.apache.commons.lang3.StringUtils;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 初始化连接池
 */
public class HqConnPoolM implements Closeable {
    private static Semaphore semaphore = new Semaphore(200, true);
    private volatile ArrayBlockingQueue<Integer> free = new ArrayBlockingQueue<>(2000);
    private List<String> servers = new ArrayList<>();
    private TradeTdxLibrary tdxLibrary;

    private static AtomicInteger serverLoop = new AtomicInteger(0);

    HqConnPoolM(TradeTdxLibrary tdxLibrary, String qsServer) {
        this.tdxLibrary = tdxLibrary;
        initConfAndConnections(qsServer);
    }

    public <T> T call(Apply<Integer, T> function) throws TradeException {
        int connId = -1;
        try {
            semaphore.acquire();
            connId = this.acquire();
            while (connId >= 0) {
                try {
                    return function.apply(tdxLibrary, connId);
                } catch (TradeException te) {
                    String msg = te.getMessage();
                    System.out.println("Call HQ server error : " + msg + ", connId:" + connId);
                    this.close(connId);
                    connId = this.acquire();
                }
            }
            throw new TradeException("暂无可用连接，请求失败");
        } catch (InterruptedException e) {
            throw new IllegalStateException("出现不应该出现的异常!", e);
        } finally {
            if (connId >= 0) {
                this.reserve(connId);
            }
            semaphore.release();
        }
    }

    @FunctionalInterface
    public interface Apply<T, R> {
        R apply(TradeTdxLibrary tdxLibrary, T t) throws TradeException;
    }

    private int acquire() {
        if (closed) {
            return -1;
        } else {
            Integer connId = free.poll();
            if (connId == null) {
                connId = createConnection();
            }
            return connId;
        }
    }

    private int createConnection() {
        int serverCount = servers.size();
        int clientId = -1, tryCount = 0;
        //每个服务器尝试2次, 如果没有一个连接创建成功那么放弃创建连接
        while (clientId < 0 && tryCount <= serverCount * 2) {
            int idx = Math.abs(serverLoop.getAndIncrement() % serverCount);
            clientId = doConnect(servers.get(idx));
            tryCount++;
        }
        return clientId;
    }

    /**
     * 回收再利用
     *
     * @param connId
     */
    private void reserve(int connId) {
        if (connId >= 0) {
            if (closed || !free.offer(connId)) {
                close(connId);
            }
        }
    }

    private void close(int connId) {
        if (connId >= 0) {
            tdxLibrary.TdxHq_Disconnect(connId);
        }
    }

    private volatile boolean closed = false;

    @Override
    public void close() {
        closed = true;
        Integer connId = free.poll();
        while (connId != null) {
            this.close(connId);
            connId = free.poll();
        }
    }

    private void initConfAndConnections(String qsServer) {
        initializeConf(qsServer);
        if (servers.size() == 0) {
            System.err.println("hq.server没配置或配置错误");
        }
    }

    private void initializeConf(String qsServer) {
        String server = qsServer;
        String[] ss = StringUtils.split(server, ",;| ");
        for (String s : ss) {
            String[] hostPort = StringUtils.split(s, ":");
            if (hostPort.length >= 2) {
                String host = StringUtils.trim(hostPort[0]);
                String port = StringUtils.trim(hostPort[1]);
                String service = host + ":" + port;
                servers.add(service);
            } else {
                System.err.println("hq.server(" + s + ")" + "配置错误,正确格式为: host:port[,host:port]");
            }
        }
    }

    private int doConnect(String service) {
        byte[] rltData = new byte[256];
        byte[] errData = new byte[256];
        String[] hostPort = StringUtils.split(service, ":");
        String host = hostPort[0];
        short port = Short.valueOf(hostPort[1]);
        int connId = tdxLibrary.TdxHq_Connect(host, port, rltData, errData);
        if (connId >= 0) {
            System.out.println("连接行情服务器(" + host + ":" + port + ")成功:" + connId);
        } else {
            System.out.println("连接行情服务器(" + host + ":" + port + ")失败:" + connId + "(" + Native.toString(errData, "GBK") + ")");
        }
        return connId;
    }

}

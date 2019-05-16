package com.farmershao.stock.trade.pool;


import com.farmershao.stock.trade.dto.ClientConnectionDto;
import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.factory.TradeFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Description 交易连接
 * @Author chenjz
 */
public class TradePool {
    private static volatile List<ClientConnectionDto> freeOnlyConnect = new CopyOnWriteArrayList<>();

    private static volatile int freeOnlyNum = -1;

    private static volatile int itera = 0;

    /**
     * 空闲交易连接
     */
    public static volatile List<ClientConnectionDto> freeTradeConnect = new CopyOnWriteArrayList<>();

    /**
     * 空闲交易连接数量
     */
    public static volatile int freeNum = -1;
    public static TradeFactory factory;

    public static void getFactory(String account, String clientVersion, Integer poolSize) {
        factory = new TradeFactory(account, clientVersion, poolSize);
    }

    public static void init(String account, String clientVersion, Integer poolSize) throws TradeException {
        getFactory(account, clientVersion, poolSize);
        freeTradeConnect = factory.getTradeConnection();
        freeNum = freeTradeConnect.size();
        freeOnlyConnect = factory.getOnlyTradeConnection();
    }


    /**
     * 得到所有的账号连接
     * @return
     */
    public static List<ClientConnectionDto> getOnlyTradeConnection() {
        return freeOnlyConnect;
    }

    public static synchronized ClientConnectionDto getNextTradeConnection(String currentAccount, String nowAccount) throws TradeException {
        int index = 0;
        for (int i = 0; i < freeOnlyConnect.size(); i++) {
            if (nowAccount.equals(freeOnlyConnect.get(i).getAcoount().getAccountNo())) {
                index = i;
                break;
            }
        }
        index += 1;
        if (index >= freeOnlyConnect.size()) {
            index = 0;
        }
        ClientConnectionDto clientConnectionDto = freeOnlyConnect.get(index);
        if (currentAccount.equals(clientConnectionDto.getAcoount().getAccountNo())) {
            return null;
        }
        return clientConnectionDto;
    }


    /**
     * 得到交易连接
     * @param accountNo         股票账户
     * @return
     * @throws TradeException
     */
    public static synchronized ClientConnectionDto getTradeConnection(String accountNo) throws TradeException {
        ClientConnectionDto conn = null;
        if (accountNo == null) {
            if (itera == freeTradeConnect.size()) {
                itera = 0;
            }
            conn = freeTradeConnect.get(itera);
            itera++;
        } else {
            for (ClientConnectionDto clientConnectionDto : freeTradeConnect) {
                String account = clientConnectionDto.getAcoount().getAccountNo().split("\\|")[0];
                if (account.equals(accountNo)) {
                    conn = clientConnectionDto;
                    break;
                }
            }
        }
        if (!factory.isTradeConnect(conn)) {
            int clientID = factory.login(conn.getAcoount());
            conn.setClientId(clientID);
            factory.continueTradeConnect(conn);
        }

        return conn;
    }


}

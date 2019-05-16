package com.farmershao.stock.trade.factory;

import com.farmershao.stock.trade.api.TradeTdxLibrary;
import com.farmershao.stock.trade.constant.MarketEnum;
import com.farmershao.stock.trade.constant.eastmoney.EastMoneyCate;
import com.farmershao.stock.trade.dto.AccountDto;
import com.farmershao.stock.trade.dto.ClientConnectionDto;
import com.farmershao.stock.trade.dto.DataTableDto;
import com.farmershao.stock.trade.dto.WrapResultDto;
import com.farmershao.stock.trade.dto.eastmoney.trade.BankrollDto;
import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.utils.StockAccountFundCacheUtil;
import com.farmershao.stock.util.JsonUtil;
import com.farmershao.stock.util.SpringManager;
import com.sun.jna.Native;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jca.context.SpringContextResourceAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class TradeFactory {

    public static final TradeTdxLibrary tdxLibrary = (TradeTdxLibrary) Native.loadLibrary("TradeX-M", TradeTdxLibrary.class);

    private static String account;

    private static String clientVersion;

    private static Integer poolSize;

    public TradeFactory() {
    }

    public TradeFactory(String account, String clientVersion, Integer poolSize) {
        this.account = account;
        this.clientVersion = clientVersion;
        this.poolSize = poolSize;
    }

    /**
     * @return
     * @throws TradeException
     * @Description 得到所有的账号
     */
    public List<ClientConnectionDto> getTradeConnection() throws TradeException {
        List<AccountDto> accountList = JsonUtil.parseToArray(account, AccountDto.class);
        List<ClientConnectionDto> list = new ArrayList<>();
        for (AccountDto accountDto : accountList) {
            ClientConnectionDto connUtilModel = new ClientConnectionDto();
            int clientID = login(accountDto);
            connUtilModel.setAcoount(accountDto);
            connUtilModel.setClientId(clientID);
            String jsonStr = queryData(clientID);
            jsonStr = jsonStr.substring(1, jsonStr.lastIndexOf("]"));
            BankrollDto bankrollDto =JsonUtil.parse(jsonStr,BankrollDto.class);
            StockAccountFundCacheUtil accountFundCacheUtil = SpringManager.getBean(StockAccountFundCacheUtil.class);
            accountFundCacheUtil.initAccountMoney(accountDto.getAccountNo().split("\\|")[0], BigDecimal.valueOf(bankrollDto.getUsableCapital()));
            list.add(connUtilModel);
        }

        return list;
    }

    public List<ClientConnectionDto> getOnlyTradeConnection() throws TradeException {
        List<AccountDto> accountList = JsonUtil.parseToArray(account, AccountDto.class);
        List<ClientConnectionDto> list = new ArrayList<>();
        for (AccountDto accountDto : accountList) {
            ClientConnectionDto connUtilModel = new ClientConnectionDto();
            int clientID = login(accountDto);
            connUtilModel.setAcoount(accountDto);
            connUtilModel.setClientId(clientID);
            list.add(connUtilModel);
        }
        return list;
    }

    /**
     * @param qsid
     * @param host
     * @param port
     * @param cVersion
     * @param yybId
     * @param accountType
     * @param accountNo
     * @param tradeAccount
     * @param jyPassword
     * @param txPassword
     * @return
     * @throws TradeException
     * @Description 登录通达信
     */
    public int logonTdx(int qsid, String host, int port, String cVersion, int yybId, int accountType, String accountNo,
                        String tradeAccount, String jyPassword, String txPassword) throws TradeException {
        // 为错误信息说明分配256个字节的空间
        byte[] errData = new byte[256];
        // 调用登录交易账户接口
        int clientId = tdxLibrary.Logon(qsid, host, port, cVersion, (short) yybId, (short) accountType,
                accountNo, tradeAccount, jyPassword, txPassword, errData);
        String errInfo = Native.toString(errData, "GBK");

        if (StringUtils.isNotBlank(errInfo) || clientId == -1) {
            throw new TradeException(errInfo);
        }
        return clientId;
    }


    /**
     * @throws TradeException
     * @Description 打开通达信
     */
    public void openTdx() throws TradeException {
        // 为错误信息说明分配256个字节的空间
        byte[] errData = new byte[256];
        // 调用打开通达信实例接口，使用固定参数
        int cID = tdxLibrary.OpenTdx(14, "6.40", 12, 0, errData);
        // 没有出错时，errInfo为空字符串
        String errInfo = Native.toString(errData, "GBK");
        if (StringUtils.isNotBlank(errInfo) && cID == -1) {
            throw new TradeException(errInfo);
        }
    }


    /**
     * @param account
     * @return
     * @throws TradeException
     * @Description 登录
     */
    public int login(AccountDto account) throws TradeException {
        String ip = account.getTradeServerIp();
        String port = account.getTradePort();
        String qsid = account.getStockJobberId();
        String yybId = account.getStockExchangeId();
        String accountType = account.getAccountType();
        String accounts = account.getAccountNo();
        String[] accountArray = accounts.split("\\|");
        String accountNo = accountArray[0];
        String jyAccount = account.getBankrollAccount();
        String jyPassword = account.getAccountPassword();
        String txPassword = account.getCommunicationPassword();
        openTdx();
        int clientID = logonTdx(Integer.valueOf(qsid), ip, Integer.valueOf(port), clientVersion, Integer.valueOf(yybId), Integer.valueOf(accountType), accountNo, jyAccount, jyPassword, txPassword);
        return clientID;
    }

    /**
     * @param conn
     * @return
     * @Description 判断登录连接是否存在
     */
    public boolean isTradeConnect(ClientConnectionDto conn) {
        boolean result = true;
        byte[] queryResult = new byte[1024 * 1024];
        byte[] errData = new byte[256];
        tdxLibrary.QueryData(conn.getClientId(), EastMoneyCate.GDDM.getValue(), queryResult, errData);
        String error = Native.toString(errData, "GBK");
        if (StringUtils.isNotBlank(error)) {
            result = false;
        }
        return result;
    }


    /**
     * @Description 定时查询交易信息，保证交易连接
     */
    public void continueTradeConnect(ClientConnectionDto conn) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                byte[] queryResult = new byte[1024 * 1024];
                byte[] errData = new byte[256];
                tdxLibrary.QueryData(conn.getClientId(), EastMoneyCate.GDDM.getValue(), queryResult, errData);
                String error = Native.toString(errData, "GBK");
                if (StringUtils.isNotBlank(error)) {
                    this.cancel();
                }
            }
        }, 0, 5 * 60 * 1000);
    }


    public String queryData(int clientId) throws TradeException {
        byte[] rltData = new byte[1024 * 1024];
        byte[] errData = new byte[256];
        // 获取对应的证券商名称
        tdxLibrary.QueryData(clientId, EastMoneyCate.ZJ.getValue(), rltData, errData);
        String resultInfo = Native.toString(rltData, "GBK");
        String errorInfo = Native.toString(errData, "GBK");

        if (StringUtils.isNotBlank(errorInfo)) {
            throw new TradeException(errorInfo);
        }

        DataTableDto dataTable = new DataTableDto(resultInfo);

        MarketEnum marketEnum = MarketEnum.valueOf("eastrich");
        WrapResultDto wrapResultDto = (WrapResultDto) marketEnum.getObject();
        String result = wrapResultDto.queryData(dataTable, EastMoneyCate.ZJ);
        return result;
    }
}

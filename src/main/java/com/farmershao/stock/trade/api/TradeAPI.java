package com.farmershao.stock.trade.api;

import com.alibaba.fastjson.JSONArray;
import com.farmershao.stock.constant.CacheKeyEnum;
import com.farmershao.stock.trade.constant.*;
import com.farmershao.stock.trade.constant.eastmoney.EastMoneyCate;
import com.farmershao.stock.trade.dto.ClientConnectionDto;
import com.farmershao.stock.trade.dto.DataTableDto;
import com.farmershao.stock.trade.dto.OrderDto;
import com.farmershao.stock.trade.dto.WrapResultDto;
import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.pool.TradePool;
import com.farmershao.stock.trade.utils.DllLoader;
import com.farmershao.stock.trade.utils.StockAccountFundCacheUtil;
import com.farmershao.stock.trade.utils.StockUtils;
import com.farmershao.stock.util.RedisUtil;
import com.farmershao.stock.util.SpringManager;
import com.google.common.base.Preconditions;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.cache.CacheType;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TradeAPI
 *
 * @author Shao Yu
 * @since 2018/4/25 10:16
 **/
public class TradeAPI implements TradeService {


    private static TradeTdxLibrary tdxLibrary = null;

    public TradeAPI(String account, String clientVersion, Integer poolSize) {
        DllLoader.preload(0);
        tdxLibrary = (TradeTdxLibrary) Native.loadLibrary("TradeX-M", TradeTdxLibrary.class);
        if (TradePool.freeNum == -1) {
            try {
                TradePool.init(account, clientVersion, poolSize);
            } catch (TradeException e) {
                e.printStackTrace();
            }
        }
    }

 /*   public List<ClientConnectionDto> initTradePool(String account, String clientVersion, Integer poolSize) {
        List<ClientConnectionDto> list = null;
        if (TradePool.freeNum == -1) {
            try {
                list = TradePool.init(account, clientVersion, poolSize);
            } catch (TradeException e) {
                e.printStackTrace();
            }
        }
        return list;
    }*/


    /**
     * 打开通达信实例接口
     *
     * @return String 连接失败时返回失败信息，成功时为空字符串
     */
    private String openTdx() throws TradeException {
        // 为错误信息说明分配256个字节的空间
        byte[] errData = new byte[256];
        // 调用打开通达信实例接口，使用固定参数
        int cID = tdxLibrary.OpenTdx(14, "6.40", 12, 0, errData);
        // 没有出错时，errInfo为空字符串
        String errInfo = Native.toString(errData, "GBK");
        if (StringUtils.isBlank(errInfo) && cID != -1) {
            return "";
        } else {
            throw new TradeException(errInfo);
        }
    }

    /**
     * 关闭通达信实例接口
     */
    private void closeTdx() {
        tdxLibrary.CloseTdx();
    }

    /**
     * 交易账户登录接口
     *
     * @param qsid         券商标识
     * @param host         券商交易服务器IP
     * @param port         券商交易服务器端口
     * @param cVersion     设置通达信客户端的版本号
     * @param yybId        营业部代码
     * @param accountType  登录账户类型
     * @param accountNo    客户账户号（Client Account），即用户在券商客户端登陆时需要输入的账户号，可以是客户号、资金账户号、股东代码等，因登录类型不同而异
     * @param tradeAccount 登录券商通达信软件，查询股东列表，从股东列表内的资金帐号一列获取
     * @param jyPassword   交易密码，对应客户账户号
     * @param txPassword   通讯密码
     * @return 登录失败时，返回失败信息，成功时返回客户端ID
     * @throws IllegalStateException 没有打开通达信实例时，抛出此异常
     */
    private String logonTdx(int qsid, String host, int port, String cVersion, int yybId, int accountType, String accountNo,
                            String tradeAccount, String jyPassword, String txPassword) throws IllegalStateException, TradeException {
        // 为错误信息说明分配256个字节的空间
        byte[] errData = new byte[256];
        // 调用登录交易账户接口
        int clientId = tdxLibrary.Logon(qsid, host, port, cVersion, (short) yybId, (short) accountType,
                accountNo, tradeAccount, jyPassword, txPassword, errData);
        String errInfo = Native.toString(errData, "GBK");
        if (StringUtils.isBlank(errInfo) && clientId != -1) {
            return String.valueOf(clientId);
        } else {
            throw new TradeException(errInfo);
        }
    }

    /**
     * 判断交易接口是否连接并登录成功
     *
     * @return 已经登录成功则返回true，失败返回false
     */
    private boolean isLogon(int clientId) {
        return tdxLibrary.IsConnectOK(clientId);
    }

    /**
     * 查询交易数据
     *
     * @param dataCate 查询信息种类
     * @return 返回对应种类的交易数据集合json数组字符串
     * @throws IllegalStateException 没有登录交易账户时，抛出此异常
     * @throws TradeException        当返回“请重新登录交易服务器”时，直接调用登出和登录方法重新登录
     */
    public String queryData(NameValue dataCate, String accountNo) throws TradeException {
        byte[] rltData = new byte[1024 * 1024];
        byte[] errData = new byte[256];
        ClientConnectionDto connectionDto = TradePool.getTradeConnection(accountNo);
        int clientId = connectionDto.getClientId();
        // 获取对应的证券商名称
        String marketName = connectionDto.getAcoount().getAccountNo().split("\\|")[1];
        tdxLibrary.QueryData(clientId, (int) dataCate.getValue(), rltData, errData);
        String resultInfo = Native.toString(rltData, "GBK");
        String errorInfo = Native.toString(errData, "GBK");

        if (checkError(errorInfo)) {
            throw new TradeException(errorInfo);
        }

        DataTableDto dataTable = new DataTableDto(resultInfo);
        MarketEnum marketEnum = MarketEnum.valueOf(marketName);
        WrapResultDto wrapResultDto = (WrapResultDto) marketEnum.getObject();
        String result = wrapResultDto.queryData(dataTable, dataCate);
        return result;
    }

    @Override
    public String queryData(NameValue dataCate, ClientConnectionDto connectionDto) throws TradeException {
        byte[] rltData = new byte[1024 * 1024];
        byte[] errData = new byte[256];
        int clientId = connectionDto.getClientId();
        // 获取对应的证券商名称
        String marketName = connectionDto.getAcoount().getAccountNo().split("\\|")[1];
        tdxLibrary.QueryData(clientId, (int) dataCate.getValue(), rltData, errData);
        String resultInfo = Native.toString(rltData, "GBK");
        String errorInfo = Native.toString(errData, "GBK");

        if (checkError(errorInfo)) {
            throw new TradeException(errorInfo);
        }

        DataTableDto dataTable = new DataTableDto(resultInfo);

        MarketEnum marketEnum = MarketEnum.valueOf(marketName);
        WrapResultDto wrapResultDto = (WrapResultDto) marketEnum.getObject();
        String result = wrapResultDto.queryData(dataTable, dataCate);
        return result;
    }

    /**
     * TODO 注释了一部分功能代码
     * 委托下单接口
     *
     * @param order 委托单
     * @return 含有委托编号的查询数据
     * @throws TradeException 当返回“请重新登录交易服务器”时，直接调用登出和登录方法重新登录
     */
    @Override
    public OrderDto sendOrder(OrderDto order, String accountNo) throws TradeException {
        Preconditions.checkArgument(order != null, "order参数不能为null.");
        Preconditions.checkArgument(order.getStockCode() != null, "order.stockCode参数不能为null.");
        Preconditions.checkArgument(order.getOrderCate() != null, "order.orderCate参数不能为null.");
        Preconditions.checkArgument(order.getPriceCate() != null, "order.priceCate参数不能为null.");
        Preconditions.checkArgument(order.getQuantity() >= 0, "order.quantity不能小于0.");
        Preconditions.checkArgument(priceCateMatchStockCode(order), "价格类型不适用于该股票.");
        ClientConnectionDto connectionDto = TradePool.getTradeConnection(accountNo);
        String account = connectionDto.getAcoount().getAccountNo().split("\\|")[0];
        String newAccount = account;
        if (order.getOrderCate() == OrderCate.MR) {
            //判断资金余额是否足够，不够就找一个够得
            RedisUtil redisUtil = SpringManager.getBean(RedisUtil.class);
            Map<String, Object> map = redisUtil.getAllHash(CacheKeyEnum.ACCOUNT_MONEY.getKey());
            BigDecimal buyAmount = BigDecimal.valueOf(order.getQuantity()).multiply(new BigDecimal(Float.toString(order.getPrice())));
            //余额不足
            BigDecimal money = (BigDecimal) map.get(account);
            if (money.compareTo(buyAmount) < 0) {
                boolean result = false;
                List<String> keyList = new ArrayList<>(map.keySet());
                for (String string : keyList) {
                    if (((BigDecimal)map.get(string)).compareTo(buyAmount) >= 0) {
                        newAccount = string;
                        result = true;
                        break;
                    }
                }
                if (!result) {
                    throw new TradeException("资金账户可用数不足150906130");
                }
            }
            if (!account.equals(newAccount)) {
                connectionDto = TradePool.getTradeConnection(newAccount);
            }
        }
        // 获取对应的证券商名称
        String marketName = connectionDto.getAcoount().getAccountNo().split("\\|")[1];
        String json = queryData(EastMoneyCate.GDDM, newAccount);
        String marcket = order.getGddm();
        if (marketName.equals("eastrich")) {
            JSONArray jsonArray = JSONArray.parseArray(json);
            for (int i = 0; i < jsonArray.size(); i++) {
                if (order.getGddm().equals(jsonArray.getJSONObject(i).getString("accountType"))) {
                    order.setGddm(jsonArray.getJSONObject(i).getString("gddm"));
                    break;
                }
            }
        }
        int orderCateId = order.getOrderCate().getValue();
        int priceCateId = order.getPriceCate().getValue();
        String gddm = order.getGddm();
        String stockCode = order.getStockCode();
        float price = order.getPrice();
        int quantity = order.getQuantity();
        byte[] rltData = new byte[1024 * 1024];
        byte[] errData = new byte[256];
        if (StringUtils.isBlank(gddm)) {
            throw new TradeException("当前用户没有开通改股票所在市场的帐号");
        }

        int clientId = connectionDto.getClientId();

        tdxLibrary.SendOrder(clientId, orderCateId, priceCateId, gddm, stockCode, price, quantity, rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String errorInfo = Native.toString(errData, "GBK");
        if (checkError(errorInfo)) {
            throw new TradeException(errorInfo);
        }
        DataTableDto rltDt = new DataTableDto(stockCode, result);
        order.setOrderNo(rltDt.get(0, 0));
        order.setAccountNo(connectionDto.getAcoount().getAccountNo());
        return order;
    }

    /**
     * TODO
     * 撤销委托接口
     *
     * @param order 带有委托编号的委托单
     * @return 表格形式的查询数据
     * @throws TradeException 当返回“请重新登录交易服务器”时，直接调用登出和登录方法重新登录
     */
    @Override
    public boolean cancelOrder(OrderDto order, String accountNo) throws TradeException {
        Preconditions.checkArgument(order != null, "order参数不能为null.");
        Preconditions.checkArgument(StringUtils.isNotBlank(order.getOrderNo()), "委托的编号(order.orderNo)不能为null.");
        Preconditions.checkArgument(StringUtils.isNotBlank(order.getStockCode()), "证券代码(order.stockCode)不能为null.");

        // 获取交易所ID
        String exchangeId = getExchangeId(order.getStockCode());
        byte[] rltData = new byte[1024 * 1024];
        byte[] errData = new byte[256];
        ClientConnectionDto connectionDto = TradePool.getTradeConnection(accountNo);
        int clientId = connectionDto.getClientId();
        // 获取对应的证券商名称
        String marketName = connectionDto.getAcoount().getAccountNo().split("\\|")[1];
        tdxLibrary.CancelOrder(clientId, exchangeId, order.getOrderNo(), rltData, errData);
        String resultInfo = Native.toString(rltData, "GBK");
        String errorInfo = Native.toString(errData, "GBK");
        if (checkError(errorInfo)) {
            throw new TradeException(errorInfo);
        }
        DataTableDto dto = new DataTableDto(resultInfo);
        if (dto.rows() > 0 && "撤单申报成功".equals(dto.get(2, 0))) {
            return true;
        }
        return false;
    }

    /**
     * 获取证券的实时五档行情接口
     *
     * @param stockCode 证券代码
     * @return 实时五档行情json字符串
     * @throws TradeException 当返回“请重新登录交易服务器”时，直接调用登出和登录方法重新登录
     */
    @Override
    public String getQuote(String stockCode, String accountNo) throws TradeException {
        byte[] rltData = new byte[1024 * 1024];
        byte[] errData = new byte[256];

        ClientConnectionDto connectionDto = TradePool.getTradeConnection(accountNo);
        int clientId = connectionDto.getClientId();
        // 获取对应的证券商名称

        String marketName = connectionDto.getAcoount().getAccountNo().split("\\|")[1];
        tdxLibrary.GetQuote(clientId, stockCode, rltData, errData);
        String resultInfo = Native.toString(rltData, "GBK");
        String errorInfo = Native.toString(errData, "GBK");
        if (checkError(errorInfo)) {
            throw new TradeException(errorInfo);
        }
        DataTableDto dt = new DataTableDto("QUOTE-" + stockCode, resultInfo);
        MarketEnum marketEnum = MarketEnum.valueOf(marketName);
        WrapResultDto wrapResultDto = (WrapResultDto) marketEnum.getObject();
        String result = wrapResultDto.getQuote(dt);
        return result;
    }

    /**
     * 查询当前可交易股票数量
     *
     * @param order 委托信息
     * @return 可卖股票数量json数组字符串
     * @throws TradeException 当返回“请重新登录交易服务器”时，直接调用登出和登录方法重新登录
     */
    public String getTrandableQuantity(OrderDto order, String accountNo) throws TradeException {
        Preconditions.checkArgument(order != null, "order参数不能为null.");
        Preconditions.checkArgument(order.getOrderCate() != null, "order.orderCate参数不能为null.");
        Preconditions.checkArgument(order.getPriceCate() != null, "order.priceCate参数不能为null.");
        Preconditions.checkArgument(order.getGddm() != null, "order.gddm参数不能为null.");
        Preconditions.checkArgument(order.getStockCode() != null, "order.stockCode参数不能为null.");
        Preconditions.checkArgument(priceCateMatchStockCode(order), "报价方式不适用于该股票.");

        int orderCateId = order.getOrderCate().getValue();
        int priceCateId = order.getPriceCate().getValue();
        String gddm = order.getGddm();
        String zqdm = order.getStockCode();
        float price = order.getPrice();

        byte[] rltData = new byte[1024 * 1024];
        byte[] errData = new byte[256];
        ClientConnectionDto connectionDto = TradePool.getTradeConnection(accountNo);
        int clientId = connectionDto.getClientId();
        // 获取对应的证券商名称
        String marketName = connectionDto.getAcoount().getAccountNo().split("\\|")[1];
        // 得到可卖股票数量，失败为负数
        tdxLibrary.GetTradableQuantity(clientId, orderCateId, priceCateId, gddm, zqdm, price, rltData, errData);
        // 委托编号数据，出错为空字符串
        String resultInfo = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        DataTableDto dataTable = new DataTableDto(resultInfo);
        MarketEnum marketEnum = MarketEnum.valueOf(marketName);
        WrapResultDto wrapResultDto = (WrapResultDto) marketEnum.getObject();
        String result = wrapResultDto.getTrandableQuantity(dataTable);
        return result;
    }

    /**
     * TODO
     * 融资融券账户直接还款
     *
     * @param amount 还款金额
     * @return 表格形式查询数据
     * @throws TradeException 当返回“请重新登录交易服务器”时，直接调用登出和登录方法重新登录
     */
    @Override
    public String repay(int amount, String accountNo) throws TradeException {
        byte[] rltData = new byte[1024 * 1024];
        byte[] errData = new byte[256];
        ClientConnectionDto connectionDto = TradePool.getTradeConnection(accountNo);
        int clientId = connectionDto.getClientId();
        // 获取对应的证券商名称
        String marketName = connectionDto.getAcoount().getAccountNo().split("\\|")[1];
        tdxLibrary.Repay(clientId, String.valueOf(amount), rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        return result;
    }

    /**
     * 查询历史交易数据
     *
     * @param dataType  查询信息的种类
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 查询数据json数组字符串
     * @throws TradeException 当返回“请重新登录交易服务器”时，直接调用登出和登录方法重新登录
     */
    public String queryHistoryData(final NameValue dataType, Date startDate, Date endDate, String accountNo) throws TradeException {
        byte[] rltData = new byte[1024 * 1024];
        byte[] errData = new byte[256];
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String sDate = df.format(startDate);
        String eDate = df.format(endDate);
        ClientConnectionDto connectionDto = TradePool.getTradeConnection(accountNo);
        int clientId = connectionDto.getClientId();
        // 获取对应的证券商名称
        String marketName = connectionDto.getAcoount().getAccountNo().split("\\|")[1];
        tdxLibrary.QueryHistoryData(clientId, (int) dataType.getValue(), sDate, eDate, rltData, errData);
        String resultInfo = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        DataTableDto dataTable = new DataTableDto(resultInfo);
        System.out.println(dataTable);
        MarketEnum marketEnum = MarketEnum.valueOf(marketName);
        WrapResultDto wrapResultDto = (WrapResultDto) marketEnum.getObject();
        String result = wrapResultDto.queryHistoryData(dataTable, dataType);
        return result;
    }

    private boolean checkError(String errorInfo) throws TradeException {
        if (StringUtils.isNotBlank(errorInfo)) {
            if (errorInfo.contains("连接交易服务器失败")) {
                throw new TradeException("请重新登录交易服务器");
            }
            return true;
        }
        return false;
    }

    private static String getExchangeId(String stockCode) {
        String market = StockUtils.getMarket(stockCode);
        if (market.startsWith("SH.")) {
            return String.valueOf(ExchangeId.SH.getValue());
        } else if (market.startsWith("SZ.")) {
            return String.valueOf(ExchangeId.SZ.getValue());
        }
        return "";
    }

    private boolean priceCateMatchStockCode(OrderDto order) {
        return priceCateMatchStockCode(order.getPriceCate(), order.getStockCode());
    }

    private boolean priceCateMatchStockCode(PriceCate priceCate, String stockCode) {
        String pCate = priceCate.getName();
        String markget = StockUtils.getMarket(stockCode);
        if (markget.startsWith("SZ") && pCate.startsWith("SH_")) {
            return false;
        }
        if (markget.startsWith("SH") && pCate.startsWith("SZ_")) {
            return false;
        }
        return true;
    }

    static Pointer[] makeMemorys(int count, int site) {
        Pointer[] rltData = new Pointer[count];
        for (int i = 0; i < count; i++) {
            Memory mem = new Memory(site);
            rltData[i] = mem;
        }
        return rltData;
    }

    static <T> List<T> transfer(List<TradeAPI> clients, Pointer[] rltData, Pointer[] errData, ResultTransfer<T> transfer) throws TradeException {
        List<T> results = new ArrayList<>();
        for (int i = 0, len = rltData.length; i < rltData.length; i++) {
            Memory rltMem = (Memory) rltData[i];
            Memory errMem = (Memory) errData[i];
            byte[] rlt = rltMem.getByteArray(0L, (int) rltMem.size());
            byte[] err = errMem.getByteArray(0L, (int) errMem.size());

            String error = Native.toString(err, "GBK");
            TradeAPI client = clients.size() == 1 ? clients.get(0) : clients.get(i);
            if (client.checkError(error)) {
                T e = transfer.transfer(i, error, "error");
                results.add(e);
            } else {
                String result = Native.toString(rlt, "GBK");
                T r = transfer.transfer(i, result, "result");
                results.add(r);
            }
        }
        return results;
    }

    <T> List<T> transfer(Pointer[] rltData, Pointer[] errData, ResultTransfer<T> transfer) throws TradeException {
        List<TradeAPI> clients = new ArrayList<>(1);
        clients.add(this);
        return transfer(clients, rltData, errData, transfer);
    }

    @FunctionalInterface
    interface ResultTransfer<T> {
        T transfer(int i, String data, String type);
    }

    private static int[] getOrderTypeIds(List<OrderDto> orders) {
        int[] orderIds = new int[orders.size()];
        for (int i = 0, len = orderIds.length; i < len; i++) {
            orderIds[i] = orders.get(i).getOrderCate().getValue();
        }
        return orderIds;
    }

    private static int[] getPriceTypeIds(List<OrderDto> orders) {
        int[] priceTypeIds = new int[orders.size()];
        for (int i = 0, len = priceTypeIds.length; i < len; i++) {
            priceTypeIds[i] = orders.get(i).getPriceCate().getValue();
        }
        return priceTypeIds;
    }

    private String[] getGddms(List<OrderDto> orders) throws TradeException {
        String[] gddms = new String[orders.size()];
        for (int i = 0, len = gddms.length; i < len; i++) {
            gddms[i] = orders.get(i).getGddm();
        }
        return gddms;
    }

    private static String[] getStockCodes(List<OrderDto> orders) {
        String[] stockCodes = new String[orders.size()];
        for (int i = 0, len = stockCodes.length; i < len; i++) {
            stockCodes[i] = orders.get(i).getStockCode();
        }
        return stockCodes;
    }

    private static float[] getPrices(List<OrderDto> orders) {
        float[] prices = new float[orders.size()];
        for (int i = 0, len = prices.length; i < len; i++) {
            prices[i] = orders.get(i).getPrice();
        }
        return prices;
    }

    private static int[] getQuantities(List<OrderDto> orders) {
        int[] quantities = new int[orders.size()];
        for (int i = 0, len = quantities.length; i < len; i++) {
            quantities[i] = orders.get(i).getQuantity();
        }
        return quantities;
    }

    private static String[] getOrderNos(List<OrderDto> orders) {
        String[] orderNos = new String[orders.size()];
        for (int i = 0, len = orderNos.length; i < len; i++) {
            orderNos[i] = orders.get(i).getOrderNo();
        }
        return orderNos;
    }

    private static String[] getExchangeIds(String[] stockCodes) {
        String[] exchangeIds = new String[stockCodes.length];
        for (int i = 0, len = exchangeIds.length; i < len; i++) {
            exchangeIds[i] = getExchangeId(stockCodes[i]);
        }
        return exchangeIds;
    }

    static <T extends NameValue> int[] enumToIntArr(List<T> list) {
        if (list != null) {
            int[] intArr = new int[list.size()];
            for (int i = 0, len = list.size(); i < len; i++) {
                intArr[i] = (Integer) list.get(i).getValue();
            }
            return intArr;
        } else {
            return new int[0];
        }
    }

    private static int[] getClientIds(List<Integer> clients) {
        int[] clientIds = new int[clients.size()];
        for (int i = 0, len = clients.size(); i < len; i++) {
            clientIds[i] = clients.get(i);
        }
        return clientIds;
    }
}

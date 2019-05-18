package com.farmershao.stock.trade.tradex;

import com.farmershao.stock.trade.enums.ExchangeId;
import com.farmershao.stock.trade.enums.KCate;
import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.model.suport.DataTable;
import com.farmershao.stock.trade.model.suport.F10Cates;
import com.farmershao.stock.trade.model.suport.Quote;
import com.farmershao.stock.trade.util.StockUtils;
import com.google.common.base.Preconditions;
import com.sun.jna.Native;
import com.sun.jna.ptr.ShortByReference;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 行情查詢接口
 * <p>
 * Created by kongkp on 2017/1/7.
 */
public class TdxHqClient implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(TdxHqClient.class);

    private TdxLibrary tdxLibrary;
    private HqConnPool connPool;
    private int connId;
    private Map<String, F10Cates> f10CatesMap = new ConcurrentHashMap<>();

    private TdxHqClient() {
        this.tdxLibrary = TdxTrade.single().lib();
    }

    public static TdxHqClient single() {
        return TdxHqClientHolder.single;
    }

    private static byte[] getExchangeIds(String[] stockCodes) {
        byte[] exchangeIds = new byte[stockCodes.length];
        for (int i = 0, len = exchangeIds.length; i < len; i++) {
            exchangeIds[i] = getExchangeId(stockCodes[i]);
        }
        return exchangeIds;
    }

    private static byte getExchangeId(String stockCode) {
        String market = StockUtils.getMarket(stockCode);
        if (market.startsWith("SH.")) {
            return ExchangeId.SH.getByteId();
        } else if (market.startsWith("SZ.")) {
            return ExchangeId.SZ.getByteId();
        }
        return -1;
    }

    /**
     * 获取市场内所有证券的数量
     *
     * @param market 市场ID
     * @return 证券数量
     * @throws TradeException
     */
    public int getSecurityCount(ExchangeId market) throws TradeException {
        Preconditions.checkState(market != null, "market参数不能为null.");

        byte[] errData = new byte[256];
        return connPool.call(connId -> {
            ShortByReference count = new ShortByReference((short) 0);
            tdxLibrary.TdxHq_GetSecurityCount(connId, market.getByteId(), count, errData);
            String error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                return (int) count.getValue();
            }
            throw new TradeException(error);
        });
    }

    /**
     * 获取市场内某个范围内的1000支股票的股票代码
     *
     *
     * @param start 范围开始位置,第一个股票是0, 第二个是1, 依此类推,位置信息依据getSecurityCount返回的证券总数确定
     * @return 股票列表
     */
    public DataTable getSecurityList(ExchangeId market, int start, int count) throws TradeException {
        // 这里注意 count 输入没用 不管怎么样都是1000条，暂时不管
        Preconditions.checkState(market != null, "market参数不能为null.");
        Preconditions.checkState(start >= 0, "start不能小小于0");

        ShortByReference countRef = new ShortByReference((short) count);
        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        return connPool.call(connId -> {
            String error = "";
            tdxLibrary.TdxHq_GetSecurityList(connId, market.getByteId(), (short) start, countRef, rltData, errData);
            error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                String result = Native.toString(rltData, "GBK");
                return new DataTable(
                                market.getName() + "(" + start + "~" + (start + countRef.getValue()) + ")" + "股票列表",
                                result);
            }
            throw new TradeException(error);
        });
    }

    /**
     * 获取证券指定范围的的K线数据
     *
     * @param cCate K线类别
     * @param stockCode 证券代码
     * @param start 范围的开始位置,最后一条K线位置是0, 前一条是1, 依此类推
     * @param count 范围的大小, 实际返回的K线数目由DataTable返回的行数确定
     * @return K 线数据
     * @throws TradeException
     */
    public synchronized DataTable getSecurityBars(KCate cCate, String stockCode, int start, int count)
                    throws TradeException {
        // 暂时单线程
        Preconditions.checkState(cCate != null, "cCate参数不能为null.");
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        Preconditions.checkState(start >= 0, "start不能小小于0");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        return connPool.call(connId -> {
            ShortByReference countRef = new ShortByReference((short) count);
            tdxLibrary.TdxHq_GetSecurityBars(connId, cCate.getByteId(), market.getByteId(), stockCode, (short) start,
                            countRef, rltData, errData);
            String error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                String result = Native.toString(rltData, "GBK");
                return new DataTable(stockCode + " " + cCate.getName() + "(" + start + "~"
                                + (start + countRef.getValue()) + ")", result);
            }
            throw new TradeException(error);
        });
    }

    /**
     * 获取指数的指定范围内K线数据 TODO 返回的数据有问题需要處理
     *
     * @param cCate K线类型
     * @param stockCode 证券(指数)代码
     * @param start 范围的开始位置,最后一条K线位置是0, 前一条是1, 依此类推
     * @param count 范围的大小, 实际返回的K线数目由DataTable返回的行数确定
     * @return K 线数据
     * @throws TradeException
     */
    public DataTable getIndexBars(KCate cCate, String stockCode, int start, int count) throws TradeException {
        Preconditions.checkState(cCate != null, "cCate参数不能为null.");
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        Preconditions.checkState(start >= 0, "start不能小小于0");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        return connPool.call(connId -> {
            ShortByReference countRef = new ShortByReference((short) count);
            tdxLibrary.TdxHq_GetIndexBars(connId, cCate.getByteId(), market.getByteId(), stockCode, (short) start,
                            countRef, rltData, errData);
            String error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                String result = Native.toString(rltData, "GBK");
                return new DataTable(stockCode + " " + cCate.getName() + "(" + start + "~"
                                + (start + countRef.getValue()) + ")", result);
            }
            throw new TradeException(error);

        });
    }

    /**
     * 获取分时数据
     *
     * @param stockCode 股票代码
     * @return 分时图数据
     * @throws TradeException
     */
    public DataTable getMinuteTimeData(String stockCode) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        return connPool.call(connId -> {
            tdxLibrary.TdxHq_GetMinuteTimeData(connId, market.getByteId(), stockCode, rltData, errData);
            String error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                String result = Native.toString(rltData, "GBK");
                return new DataTable(stockCode + "分时数据", result);
            }
            throw new TradeException(error);
        });
    }

    /**
     * 获取历史分时数据
     *
     * @param stockCode 股票代码
     * @param date 日期, 比如2014年1月1日为整数20140101
     * @return 历史分时图数据
     * @throws TradeException
     */
    public DataTable getHistoryMinuteTimeData(String stockCode, int date) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        return connPool.call(connId -> {
            tdxLibrary.TdxHq_GetHistoryMinuteTimeData(connId, market.getByteId(), stockCode, date, rltData, errData);
            String error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                String result = Native.toString(rltData, "GBK");
                return new DataTable(stockCode + "历史分时数据(" + date + ")", result);
            }
            throw new TradeException(error);
        });
    }

    public DataTable getHistoryMinuteTimeData(String stockCode, Date date) throws TradeException {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        int dateInt = Integer.valueOf(df.format(date));
        return getHistoryMinuteTimeData(stockCode, dateInt);
    }

    /**
     * 获取分笔成交某个范围内的数据 //TODO 弄清楚接口含义（如何获取所有的分笔数据）
     *
     * @param stockCode 股票代码
     * @param start 范围开始位置,最后一条K线位置是0, 前一条是1, 依此类推
     * @param count 范围大小，表示用户要请求的K线数目
     * @return 分笔成交数据
     * @throws TradeException
     */
    public DataTable getTransactionData(String stockCode, int start, int count) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        return connPool.call(connId -> {
            ShortByReference countRef = new ShortByReference((short) count);
            tdxLibrary.TdxHq_GetTransactionData(connId, market.getByteId(), stockCode, (short) start, countRef, rltData,
                            errData);
            String error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                String result = Native.toString(rltData, "GBK");
                return new DataTable(stockCode + "分笔数据(" + start + "~" + (start + count) + ")", result);
            }
            throw new TradeException(error);
        });
    }

    public DataTable getHistoryTransactionData(String stockCode, int start, int count, Date date)
                    throws TradeException {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        int dateInt = Integer.valueOf(df.format(date));
        return getHistoryTransactionData(stockCode, start, count, dateInt);
    }

    public DataTable getHistoryTransactionData(String stockCode, int start, int count, int date) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        ShortByReference countRef = new ShortByReference((short) count);
        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];

        return connPool.call(connId -> {
            tdxLibrary.TdxHq_GetHistoryTransactionData(connId, market.getByteId(), stockCode, (short) start, countRef,
                            date, rltData, errData);
            String error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                String result = Native.toString(rltData, "GBK");
                return new DataTable(stockCode + "历史分笔数据(" + date + ":" + start + "~" + (start + count) + ")", result);
            }
            throw new TradeException(error);
        });

    }

    /**
     * 批量获取多个证券的五档报价数据
     *
     * @param stockCodes 要查询的证券代码(最大50，不同券商可能不一样,具体数目请自行咨询券商或测试)
     * @return 执行后, 返回返回的Quotes数据，如果stockCodes数量太多，那么返回的数量将不会有那么多
     * @throws TradeException
     */
    public DataTable getSecurityQuotes(List<String> stockCodes) throws TradeException {
        Preconditions.checkState(stockCodes != null, "stockCodes参数不能为null.");
        Preconditions.checkState(stockCodes.size() > 0, "stockCodes个数必须大于0.");
        return getSecurityQuotes(stockCodes.toArray(new String[stockCodes.size()]));
    }

    public Quote getSecurityQuote(String stockCode) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        DataTable dt = getSecurityQuotes(stockCode);
        Quote quote = new Quote();
        if (dt.rows() == 1) {
            quote.read(dt.getRow(0));
        }
        return quote;
    }

    public DataTable getSecurityQuotes(String... stockCodes) throws TradeException {
        Preconditions.checkState(stockCodes != null, "stockCodes参数不能为null.");
        Preconditions.checkState(stockCodes.length > 0, "stockCodes个数必须大于0.");

        int count = stockCodes.length;
        byte[] markets = getExchangeIds(stockCodes);
        ShortByReference countRef = new ShortByReference((short) count);
        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];

        return connPool.call(connId -> {
            tdxLibrary.TdxHq_GetSecurityQuotes(connId, markets, stockCodes, countRef, rltData, errData);
            String error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                String result = Native.toString(rltData, "GBK");
                return new DataTable("SecurityQuotes", result);
            }
            throw new TradeException(error);
        });
    }

    /**
     * 获取F10资料的分类
     *
     * @param stockCode 股票代码
     * @return F10资料数据
     * @throws TradeException
     */
    public F10Cates getCompanyInfoCategory(String stockCode) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        F10Cates cached = f10CatesMap.get(stockCode);
        if (cached == null) {
            byte[] errData = new byte[256];
            byte[] rltData = new byte[1024 * 1024];
            cached = connPool.call(connId -> {
                tdxLibrary.TdxHq_GetCompanyInfoCategory(connId, market.getByteId(), stockCode, rltData, errData);
                String error = Native.toString(errData, "GBK");
                if (StringUtils.isBlank(error)) {
                    String result = Native.toString(rltData, "GBK");
                    return new F10Cates(stockCode, result);
                }
                throw new TradeException(error);
            });
            f10CatesMap.put(stockCode, cached);
        }
        return cached;
    }

    /**
     * 获取F10资料的某一分类的内容
     *
     * @param stockCode 证券代码
     * @param filename 文件名
     * @param start 内容在文件里起始位置
     * @param length 内容长度
     * @return F10某一分类资料内容
     * @throws TradeException
     */
    private String getCompanyInfoContent(String stockCode, String filename, int start, int length)
                    throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        return connPool.call(connId -> {
            tdxLibrary.TdxHq_GetCompanyInfoContent(connId, market.getByteId(), stockCode, filename, start, length,
                            rltData, errData);
            String error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                return Native.toString(rltData, "GBK");
            }
            throw new TradeException(error);
        });
    }

    /**
     * 获取F10资料的某一分类的内容
     *
     * @param stockCode 股票代碼
     * @param cate 取值为：最新提示、公司概况、财务分析、股东研究、股本结构、 资本运作、业内点评、行业分析、公司大事、港澳特色、经营分析、 主力追踪、分红扩股、高层治理、龙虎榜单、关联个股
     * @return F10资料的指定分类的内容
     * @throws TradeException
     */
    public String getCompanyInfoContent(String stockCode, String cate) throws TradeException {
        F10Cates f10Cates = this.getCompanyInfoCategory(stockCode);
        F10Cates.Index index = f10Cates.getIndex(StringUtils.trim(cate));
        if (index == null) {
            throw new TradeException("没有找到类别(" + cate + ")的信息");
        }
        return getCompanyInfoContent(stockCode, index.getFile(), index.getStart(), index.getLength());
    }

    /**
     * 获取F10资料的所有分类的内容
     *
     * @param stockCode 股票代碼
     * @return F10资料内容，以分类Map内容返回
     * @throws TradeException
     */
    public Map<String, String> getCompanyInfoContents(String stockCode) throws TradeException {
        F10Cates f10Cates = this.getCompanyInfoCategory(stockCode);
        if (f10Cates == null) {
            throw new TradeException("没有找到(" + stockCode + ")公司的F10资料信息");
        }
        Map<String, String> contents = new HashMap<>();
        for (String cate : f10Cates.cates()) {
            try {
                String content = this.getCompanyInfoContent(stockCode, cate);
                contents.put(cate, content);
            } catch (TradeException te) {
                te.printStackTrace(System.err);
            }
        }
        return contents;
    }

    /**
     * 获取除权除息信息
     *
     * @param stockCode 证券代码
     * @return 除权除息信息
     * @throws TradeException
     */
    public DataTable getXDXRInfo(String stockCode) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        return connPool.call(connId -> {
            tdxLibrary.TdxHq_GetXDXRInfo(connId, market.getByteId(), stockCode, rltData, errData);
            String error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                String result = Native.toString(rltData, "GBK");
                return new DataTable(stockCode, result);
            }
            throw new TradeException(error);
        });
    }

    /**
     * 获取财务信息
     *
     * @param stockCode 证券代码
     * @return 财务数据
     * @throws TradeException
     */
    public DataTable getFinanceInfo(String stockCode) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        return connPool.call(connId -> {
            tdxLibrary.TdxHq_GetFinanceInfo(connId, market.getByteId(), stockCode, rltData, errData);
            String error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                String result = Native.toString(rltData, "GBK");
                return new DataTable("(" + stockCode + ")财务信息", result);
            }
            throw new TradeException(error);
        });
    }

    public void restart() {
        if (connPool.isClosed()) {
            connPool.restart();
        }
    }

    public int getConnId() {
        return connId;
    }

    public void setConnId(int connId) {
        this.connId = connId;
    }

    public void close() {
        connPool.close();
    }

    public TdxLibrary getTdxLibrary() {
        return tdxLibrary;
    }

    public void setTdxLibrary(TdxLibrary tdxLibrary) {
        this.tdxLibrary = tdxLibrary;
    }

    private static class TdxHqClientHolder {
        private static final TdxHqClient single = new TdxHqClient();
    }

}

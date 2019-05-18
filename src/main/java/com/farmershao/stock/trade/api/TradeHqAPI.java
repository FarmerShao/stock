package com.farmershao.stock.trade.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.farmershao.stock.trade.constant.ExchangeId;
import com.farmershao.stock.trade.constant.KCate;
import com.farmershao.stock.trade.dto.*;
import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.pool.ConnectPool;
import com.farmershao.stock.trade.utils.DllLoader;
import com.farmershao.stock.trade.utils.StockUtils;
import com.farmershao.stock.util.JsonUtil;
import com.google.common.base.Preconditions;
import com.sun.jna.Native;
import com.sun.jna.ptr.ShortByReference;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/***
 * 普通行情API
 */
public class TradeHqAPI implements TradeHqService{

    private HqConnPoolM connPool;

    // 对接接口库
    private  TradeTdxLibrary tdxLibrary = null;

    public TradeHqAPI(int poolNum, String qsServer) throws TradeException {
        DllLoader.preload(0);
        tdxLibrary = (TradeTdxLibrary) Native.loadLibrary("TradeX-M", TradeTdxLibrary.class);
        this.connPool = new HqConnPoolM(tdxLibrary, qsServer);
    }

    /**
     * 连接通达信行情服务器
     *
     * @param host 服务器IP
     * @param port 服务器端口
     * @return 连接成功时，返回结果信息HqConnectInfo,json
     * @throws TradeException 失败时抛出该异常，异常信息为失败原因
     */
    public String connect(String host, int port) throws TradeException {
        byte[] rltData = new byte[1024];
        byte[] errData = new byte[256];
        int nConnID = tdxLibrary.TdxHq_Connect(host, (short) port, rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (nConnID < 0) {
            // 失败时返回负值
            throw new TradeException(error);
        } else {
            DataTableDto dt = new DataTableDto(result);
            List<HqConnectInfo> lists = new ArrayList<HqConnectInfo>();
            HqConnectInfo hq = null;
            for (int i = 0, size = dt.rows(); i < size; i++) {
                hq = new HqConnectInfo();
                hq.read(dt.getRow(i));
                lists.add(hq);
            }
            return JsonUtil.toJson(lists);
        }
    }

    public void disConnect(int nConnID) {
        tdxLibrary.TdxHq_Disconnect(nConnID);
    }


    /**
     * 获取市场内所有证券的数量
     *
     * @param market 交易所（市场）
     * @return 证券数量
     * @throws TradeException
     */
    @Override
    public int getSecurityCount(ExchangeId market) throws TradeException {
        Preconditions.checkState(market != null, "market参数不能为null.");

        byte[] errData = new byte[256];

        return connPool.call((tdxLibrary, connId) -> {
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
     * 获取市场内所有证券的数量
     *
     * @return 证券数量
     * @throws TradeException
     */
    @Override
    public int getSecurityCount(byte id) throws TradeException {

        byte[] errData = new byte[256];

        return connPool.call((tdxLibrary, connId) -> {
            ShortByReference count = new ShortByReference((short) 0);
            tdxLibrary.TdxHq_GetSecurityCount(connId, id, count, errData);
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
     * @param market 交易所（市场）
     * @param start  范围开始位置,第一个股票是0, 第二个是1, 依此类推,位置信息依据getSecurityCount返回的证券总数确定
     * @param count  范围的大小，API 执行后,保存了实际返回的股票数目
     * @return 股票列表
     * @throws TradeException
     */
    @Override
    public List<SjsSharesListDto> getSecurityList(ExchangeId market, int start, int count) throws TradeException {
        Preconditions.checkState(market != null, "market参数不能为null.");
        Preconditions.checkState(start >= 0, "start不能小于0");

        ShortByReference countRef = new ShortByReference((short) count);
        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        DataTableDto dt =  connPool.call((tdxLibrary, connId) -> {
            String error = "";
            tdxLibrary.TdxHq_GetSecurityList(connId, market.getByteId(), (short) start, countRef, rltData, errData);
            error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                String result = Native.toString(rltData, "GBK");
                return new DataTableDto(market.getName() + "(" + start + "~" + (start + countRef.getValue()) + ")" + "股票列表", result);
            }
            throw new TradeException(error);
        });

        List<SjsSharesListDto> sharesListModelList = new ArrayList<SjsSharesListDto>();
        for (int i = 0; i < dt.rows(); i++) {
            if (!StockUtils.stockCodesFilter(dt.getRow(i)[0])) {
                continue;
            }
            SjsSharesListDto sharesListModel = new SjsSharesListDto();
            sharesListModel.read(dt.getRow(i));
            if (market.getValue() == 0) {
                sharesListModel.setPinyin("SZ");
            } else if (market.getValue() == 1) {
                sharesListModel.setPinyin("SH");
            }
            sharesListModelList.add(sharesListModel);
        }
        return sharesListModelList;
    }

    /**
     * 批量获取多个证券的五档报价数据
     *
     * @param stockCodes 要查询的证券代码(最大80，不同券商可能不一样,具体数目请自行咨询券商或测试)
     * @return 执行后, 返回返回的Quotes数据，如果stockCodes数量太多，那么返回的数量将不会有那么多
     * @throws TradeException
     */
    @Override
    public List<QuoteDto> getSecurityQuotes(List<String> stockCodes) throws TradeException {
        Preconditions.checkState(stockCodes != null, "stockCodes参数不能为null.");
        Preconditions.checkState(stockCodes.size() > 0, "stockCodes个数必须大于0.");

        int count = stockCodes.size();
        byte[] markets = getExchangeIds(stockCodes.toArray(new String[stockCodes.size()]));
        ShortByReference countRef = new ShortByReference((short) count);
        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];

        DataTableDto dt =  connPool.call((tdxLibrary, connId) -> {
            tdxLibrary.TdxHq_GetSecurityQuotes(connId, markets, stockCodes.toArray(new String[stockCodes.size()]), countRef, rltData, errData);
            String error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                String result = Native.toString(rltData, "GBK");
                return new DataTableDto("SecurityQuotes", result);
            }
            throw new TradeException(error);
        });
        List<QuoteDto> strockInfoDtoList = new ArrayList<QuoteDto>();
        for (int i = 0; i < dt.rows(); i++) {
            QuoteDto strockInfoDto = new QuoteDto();
            strockInfoDto.read(dt.getRow(i));
            if (new BigDecimal(strockInfoDto.getNowPrice()).doubleValue() > 0 && new BigDecimal(strockInfoDto.getSumCount()).doubleValue() > 0
                    && new BigDecimal(strockInfoDto.getSumMoney()).doubleValue() > 0) {
                strockInfoDto.setSuspension("N");
            } else {
                strockInfoDto.setSuspension("Y");
            }
            strockInfoDtoList.add(strockInfoDto);
        }
        return strockInfoDtoList;
    }

    /**
     * 数组大小最大为80
     * @param array 股票代码数组
     * @return
     * @throws Exception
     */
    @Override
    public List<QuoteDto> getSecurityQuotes(String[] array) throws Exception {
        int connId = ConnectPool.getConnection();
        int count = array.length;
        byte[] markets = getExchangeIds(array);

        ShortByReference countRef = new ShortByReference((short) count);
        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        tdxLibrary.TdxHq_GetSecurityQuotes(connId, markets, array, countRef, rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        DataTableDto dt = new DataTableDto("SecurityQuotes，证券数目：" + countRef.getValue(), result);
        List<QuoteDto> quoteDtos = new ArrayList<>();
        QuoteDto stockInfoModel = null;
        for (int i=0; i < dt.rows(); i++) {
            stockInfoModel = new QuoteDto();
            stockInfoModel.read(dt.getRow(i));
            if (new BigDecimal(stockInfoModel.getNowPrice()).doubleValue() > 0 && new BigDecimal(stockInfoModel.getSumCount()).doubleValue() > 0
                    && new BigDecimal(stockInfoModel.getSumMoney()).doubleValue() > 0) {
                stockInfoModel.setSuspension("N");
            } else {
                stockInfoModel.setSuspension("Y");
            }
            quoteDtos.add(stockInfoModel);
        }

        return quoteDtos;
    }

    /**
     * 批量获取多个证券的五档报价数据
     *
     * @param stockCodes 要查询的证券代码(最大80，不同券商可能不一样,具体数目请自行咨询券商或测试)
     * @param bourseIDs 0深圳，1上海
     * @return 执行后, 返回返回的Quotes数据，如果stockCodes数量太多，那么返回的数量将不会有那么多
     * @throws TradeException
     */
    @Override
    public String getSecurityQuotes(String[] stockCodes, int[] bourseIDs) throws TradeException {
        Preconditions.checkState(stockCodes != null, "stockCodes参数不能为null.");
        Preconditions.checkState(stockCodes.length > 0, "stockCodes个数必须大于0.");
        int connId = ConnectPool.getConnection();
        int count = stockCodes.length;

        byte[] markets = new byte[stockCodes.length];
        for (int i = 0; i < markets.length; i++) {
            markets[i] = (byte)bourseIDs[i];
        }

        ShortByReference countRef = new ShortByReference((short) count);
        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        tdxLibrary.TdxHq_GetSecurityQuotes(connId, markets, stockCodes, countRef, rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        DataTableDto dt = new DataTableDto("SecurityQuotes，证券数目：" + countRef.getValue(), result);

        List<QuoteDto> dtos = new ArrayList<>();
        for (int i = 0; i < dt.rows(); i++) {
            QuoteDto strockInfoModel = new QuoteDto();
            strockInfoModel.read(dt.getRow(i));
            if (new BigDecimal(strockInfoModel.getNowPrice()).doubleValue() > 0 && new BigDecimal(strockInfoModel.getSumCount()).doubleValue() > 0
                    && new BigDecimal(strockInfoModel.getSumMoney()).doubleValue() > 0) {
                strockInfoModel.setSuspension("N");
            } else {
                strockInfoModel.setSuspension("Y");
            }
            dtos.add(strockInfoModel);
        }
        return JSONObject.toJSON(dtos).toString();
    }

    /**
     * 批量获取多个证券的五档报价数据
     *
     * @param stockCodes 要查询的证券代码(最大80，不同券商可能不一样,具体数目请自行咨询券商或测试)
     * @param bourseID 0深圳，1上海
     * @return 执行后, 返回返回的Quotes数据，如果stockCodes数量太多，那么返回的数量将不会有那么多
     * @throws TradeException
     */
    @Override
    public Map<String, QuoteDto> getSecurityQuotes(List<String> stockCodes, int bourseID) throws TradeException {
        Preconditions.checkState(stockCodes != null, "stockCodes参数不能为null.");
        Preconditions.checkState(stockCodes.size() > 0, "stockCodes个数必须大于0.");

        int count = stockCodes.size();
        ShortByReference countRef = new ShortByReference((short) count);
        String[] stocks = (String[])stockCodes.toArray(new String[count]);
        byte[] markets = new byte[stocks.length];

        for(int i = 0; i < markets.length; ++i) {
            markets[i] = (byte)bourseID;
        }

        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];

        DataTableDto dt =  connPool.call((tdxLibrary, connId) -> {
            tdxLibrary.TdxHq_GetSecurityQuotes(connId, markets, stocks, countRef, rltData, errData);
            String error = Native.toString(errData, "GBK");
            if (StringUtils.isBlank(error)) {
                String result = Native.toString(rltData, "GBK");
                return new DataTableDto("SecurityQuotes", result);
            }
            throw new TradeException(error);
        });

        Map<String, QuoteDto> quoteMap = new HashMap<>();
        for (int i = 0; i < dt.rows(); i++) {
            QuoteDto strockInfoModel = new QuoteDto();
            strockInfoModel.read(dt.getRow(i));
            if (new BigDecimal(strockInfoModel.getNowPrice()).doubleValue() > 0 && new BigDecimal(strockInfoModel.getSumCount()).doubleValue() > 0
                    && new BigDecimal(strockInfoModel.getSumMoney()).doubleValue() > 0) {
                strockInfoModel.setSuspension("N");
            } else {
                strockInfoModel.setSuspension("Y");
            }
            quoteMap.put(dt.getRow(i)[1], strockInfoModel);
        }
        return quoteMap;
    }

    /**
     * 获取证券指定范围的的K线数据
     *
     * @param cCate     K线类别
     * @param stockCode 证券代码
     * @param start     范围的开始位置,最后一条K线位置是0, 前一条是1, 依此类推
     * @param count     范围的大小, 实际返回的K线数目由DataTable返回的行数确定
     * @return K 线数据
     * @throws TradeException
     */
    @Override
    public String getSecurityBars(int cCate, String stockCode, int start, int count) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        Preconditions.checkState(start >= 0, "start不能小小于0");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        int connId = ConnectPool.getConnection();
        ShortByReference countRef = new ShortByReference((short) count);
        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        tdxLibrary.TdxHq_GetSecurityBars(connId, (byte)cCate, market.getByteId(), stockCode, (short) start, countRef, rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        DataTableDto dt = new DataTableDto(result);
        List<KcateDataDto> kcateDataModelList = new ArrayList<KcateDataDto>();
        for (int i = 0; i < dt.rows(); i++) {
            KcateDataDto kcateDataModel = new KcateDataDto();
            kcateDataModel.read(dt.getRow(i));
            kcateDataModelList.add(kcateDataModel);
        }
        return JSONObject.toJSON(kcateDataModelList).toString();

    }

    /**
     * 获取证券指定范围的的K线数据
     *
     * @param cCate     K线类别
     * @param stockCode 证券代码
     * @param start     范围的开始位置,最后一条K线位置是0, 前一条是1, 依此类推
     * @param count     范围的大小, 实际返回的K线数目由DataTable返回的行数确定
     * @return K 线数据
     * @throws TradeException
     */
    @Override
    public String getSecurityBars(KCate cCate, String stockCode, int start, int count) throws TradeException {
        Preconditions.checkState(cCate != null, "cCate参数不能为null.");
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        Preconditions.checkState(start >= 0, "start不能小小于0");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        ShortByReference countRef = new ShortByReference((short) count);
        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        int nConnID = ConnectPool.getConnection();
        tdxLibrary.TdxHq_GetSecurityBars(nConnID, cCate.getByteId(), market.getByteId(), stockCode, (short) start, countRef, rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        DataTableDto dt = new DataTableDto(stockCode + " " + cCate.getName() + "(" + start + "~" + (start + countRef.getValue()) + ")", result);
        List<KcateDataDto> kcateDataDtoList = new ArrayList<KcateDataDto>();
        for (int i = 0; i < dt.rows(); i++) {
            KcateDataDto kcateDataDto = new KcateDataDto();
            kcateDataDto.read(dt.getRow(i));
            kcateDataDtoList.add(kcateDataDto);
        }
        return JSONObject.toJSON(kcateDataDtoList).toString();

    }

    /**
     * 获取指数的指定范围内K线数据
     *
     * @param cCate     K线类别
     * @param stockCode 证券代码
     * @param start     范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param count     范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目, 最大值800
     * @return
     * @throws TradeException
     */
    @Override
    public String getIndexBars(KCate cCate, String stockCode, int start, int count) throws TradeException {
        Preconditions.checkState(cCate != null, "cCate参数不能为null.");
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        Preconditions.checkState(start >= 0, "start不能小小于0");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        ShortByReference countRef = new ShortByReference((short) count);
        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        int nConnID = ConnectPool.getConnection();
        tdxLibrary.TdxHq_GetIndexBars(nConnID, cCate.getByteId(), market.getByteId(), stockCode, (short) start, countRef, rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        DataTableDto dt = new DataTableDto(stockCode + " " + cCate.getName() + "(" + start + "~" + (start + countRef.getValue()) + ")", result);
        List<TimeDataDto> timeDataDtoList = new ArrayList<TimeDataDto>();
        for (int i = 0; i < dt.rows(); i++) {
            TimeDataDto timeDataDto = new TimeDataDto();
            timeDataDto.read(dt.getRow(i));
            timeDataDtoList.add(timeDataDto);
        }
        return JSONObject.toJSON(timeDataDtoList).toString();
    }

    /**
     * 获取分时数据
     *
     * @param stockCode 证券代码
     * @return
     * @throws TradeException
     */
    @Override
    public String getMinuteTimeData(String stockCode) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        int nConnID = ConnectPool.getConnection();
        tdxLibrary.TdxHq_GetMinuteTimeData(nConnID, market.getByteId(), stockCode, rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        DataTableDto dt = new DataTableDto(stockCode + "分时数据", result);
        List<MinuteTimeDto> minuteTimeDtoList = new ArrayList<MinuteTimeDto>();
        for (int i = 0; i < dt.rows(); i++) {
            MinuteTimeDto minuteTimeDto = new MinuteTimeDto();
            minuteTimeDto.read(dt.getRow(i));
            minuteTimeDtoList.add(minuteTimeDto);
        }
        return JSONObject.toJSON(minuteTimeDtoList).toString();
    }

    /**
     * 获取历史分时数据
     *
     * @param stockCode 股票代码
     * @param date      日期, 比如2014年1月1日为整数20140101
     * @return
     * @throws TradeException
     */
    @Override
    public String getHistoryMinuteTimeData(String stockCode, int date) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        int nConnID = ConnectPool.getConnection();
        tdxLibrary.TdxHq_GetHistoryMinuteTimeData(nConnID, market.getByteId(), stockCode, date, rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        DataTableDto dt = new DataTableDto(stockCode + "历史分时数据(" + date + ")", result);
        List<MinuteTimeDto> minuteTimeDtoList = new ArrayList<MinuteTimeDto>();
        for (int i = 0; i < dt.rows(); i++) {
            MinuteTimeDto minuteTimeDto = new MinuteTimeDto();
            minuteTimeDto.read(dt.getRow(i));
            minuteTimeDtoList.add(minuteTimeDto);
        }
        return JSONObject.toJSON(minuteTimeDtoList).toString();
    }

    /**
     * 获取历史分时数据
     *
     * @param stockCode 证券代码
     * @param date      日期
     * @return
     * @throws TradeException
     */
    @Override
    public String getHistoryMinuteTimeData(String stockCode, Date date) throws TradeException {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        int dateInt = Integer.valueOf(df.format(date));
        return getHistoryMinuteTimeData(stockCode, dateInt);
    }

    /**
     * 获取某个范围的分笔成交数据
     *
     * @param stockCode 股票代码
     * @param start     范围开始位置,最后一条K线位置是0, 前一条是1, 依此类推
     * @param count     范围大小，表示用户要请求的K线数目
     * @return
     * @throws TradeException
     */
    @Override
    public String getTransactionData(String stockCode, int start, int count) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于哪个证券市场.");

        ShortByReference countRef = new ShortByReference((short) count);
        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        int nConnID = ConnectPool.getConnection();
        tdxLibrary.TdxHq_GetTransactionData(nConnID, market.getByteId(), stockCode, (short) start, countRef, rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        DataTableDto dt = new DataTableDto(stockCode + "分笔数据(" + start + "~" + (start + countRef.getValue()) + ")", result);
        List<TransactionDto> transactionDtoList = new ArrayList<TransactionDto>();
        for (int i = 0; i < dt.rows(); i++) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.read(dt.getRow(i));
            transactionDtoList.add(transactionDto);
        }
        return JSONObject.toJSON(transactionDtoList).toString();
    }

    /**
     * 获取某个范围的历史分笔成交数据
     * 券商行情服务器侧限制最大允许单次返回2000 条记录，如果需要更多数据则需要多次调用循环读取
     *
     * @param stockCode 证券代码
     * @param start     范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param count     范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目
     * @param date      日期
     * @return
     * @throws TradeException
     */
    @Override
    public String getHistoryTransactionData(String stockCode, int start, int count, Date date) throws TradeException {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        int dateInt = Integer.valueOf(df.format(date));
        return getHistoryTransactionData(stockCode, start, count, dateInt);
    }

    /**
     * 获取某个范围的历史分笔成交数据
     * 券商行情服务器侧限制最大允许单次返回2000 条记录，如果需要更多数据则需要多次调用循环读取
     *
     * @param stockCode 证券代码
     * @param start     范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param count     范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目
     * @param date      日期, 比如2017 年2 月1 日为整数20170201
     * @return
     * @throws TradeException
     */
    @Override
    public String getHistoryTransactionData(String stockCode, int start, int count, int date) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        ShortByReference countRef = new ShortByReference((short) count);
        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        int nConnID = ConnectPool.getConnection();
        tdxLibrary.TdxHq_GetHistoryTransactionData(nConnID, market.getByteId(), stockCode, (short) start, countRef, date, rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        DataTableDto dt = new DataTableDto(stockCode + "历史分笔数据(" + date + ":" + start + "~" + (start + countRef.getValue()) + ")", result);
        List<HistoryTransctionDto> historyTransctionDtoList = new ArrayList<HistoryTransctionDto>();
        for (int i = 0; i < dt.rows(); i++) {
            HistoryTransctionDto historyTransctionDto = new HistoryTransctionDto();
            historyTransctionDto.read(dt.getRow(i));
            historyTransctionDtoList.add(historyTransctionDto);
        }
        return JSONObject.toJSON(historyTransctionDtoList).toString();

    }

    //TODO replace with the real cache
    private Map<String, F10Cates> f10CatesMap = new HashMap<>();

    /**
     * 获取F10资料的类别
     *
     * @param stockCode 证券代码
     * @return F10资料数据
     * @throws TradeException
     */
    @Override
    public String getCompanyInfoCategory(String stockCode) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        F10Cates cached = f10CatesMap.get(stockCode);
        List<F10FileModel> list = new ArrayList<F10FileModel>();
        if (cached == null) {
            byte[] errData = new byte[256];
            byte[] rltData = new byte[1024 * 1024];
            int nConnID = ConnectPool.getConnection();
            tdxLibrary.TdxHq_GetCompanyInfoCategory(nConnID, market.getByteId(), stockCode, rltData, errData);
            String result = Native.toString(rltData, "GBK");
            String error = Native.toString(errData, "GBK");
            if (checkError(error)) {
                throw new TradeException(error);
            }
            cached = new F10Cates(stockCode, result);
            DataTableDto dt = new DataTableDto(result);

            for (int i = 0; i < dt.rows(); i++) {
                F10FileModel model = new F10FileModel();
                model.read(dt.getRow(i));
                list.add(model);
            }
            f10CatesMap.put(stockCode, cached);
        } else {
            DataTableDto dt = new DataTableDto(cached.toString());
            for (int i = 0; i < dt.rows(); i++) {
                F10FileModel f10FileModel = new F10FileModel();
                f10FileModel.read(dt.getRow(i));
                list.add(f10FileModel);
            }

        }

        return JSONObject.toJSON(list).toString();
    }

    /**
     * 获取某一类F10 资料的具体内容
     *
     * @param stockCode 证券代码
     * @param filename  类目的文件名, 由TdxHq_GetCompanyInfoCategory 返回信息中获取
     * @param start     类目的开始位置, 由TdxHq_GetCompanyInfoCategory 返回信息中获取
     * @param length    类目的长度, 由TdxHq_GetCompanyInfoCategory 返回信息中获取
     * @return
     * @throws TradeException
     */
    @Override
    public String getCompanyInfoContent(String stockCode, String filename, int start, int length) throws TradeException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        int nConnID = ConnectPool.getConnection();
        tdxLibrary.TdxHq_GetCompanyInfoContent(nConnID, market.getByteId(), stockCode, filename, start, length, rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        return result;
    }

    /**
     * 获取F10资料的某一分类的内容
     *
     * @param stockCode 股票代碼
     * @param cate      取值为：最新提示、公司概况、财务分析、股东研究、股本结构、
     *                  资本运作、业内点评、行业分析、公司大事、港澳特色、经营分析、
     *                  主力追踪、分红扩股、高层治理、龙虎榜单、关联个股
     * @return
     * @throws TradeException
     */
    @Override
    public String getCompanyInfoContent(String stockCode, String cate) throws TradeException {
        String json = this.getCompanyInfoCategory(stockCode);
        JSONArray jsonArray = JSONArray.parseArray(json);
        boolean result = false;
        F10FileModel model = new F10FileModel();
        for (int i = 0; i < jsonArray.size(); i++) {
            if (cate.equals(jsonArray.getJSONObject(i).get("classic"))) {
                result = true;
                model.setFile(jsonArray.getJSONObject(i).get("file").toString());
                model.setStart((int) jsonArray.getJSONObject(i).get("start"));
                model.setLength((int) jsonArray.getJSONObject(i).get("length"));
                break;
            }
        }
        if (result == false) {
            throw new TradeException("没有找到类别(" + cate + ")的信息");
        }
        return getCompanyInfoContent(stockCode, model.getFile(), model.getStart(), model.getLength());
    }


    /**
     * 获取F10资料的所有分类的内容
     *
     * @param stockCode 股票代碼
     * @return
     * @throws TradeException
     */
    @Override
    public String getCompanyInfoContents(String stockCode) throws TradeException {
        String json = this.getCompanyInfoCategory(stockCode);
        if (json == null || "".equals(json)) {
            throw new TradeException("没有找到类别(" + stockCode + ")的公司信息");
        }
        Map<String, String> contents = new HashMap<>();
        JSONArray jsonArray = JSONArray.parseArray(json);
        for (int i = 0; i < jsonArray.size(); i++) {
            String cate = jsonArray.getJSONObject(i).get("classic").toString();
            String content = this.getCompanyInfoContent(stockCode, cate);
            if (checkError(content)) {
                contents.put(cate, content);
            }
        }
        return JSONObject.toJSON(contents).toString();
    }

    /**
     * 获取除权除息信息
     *
     * @param stockCode 证券代码
     * @return
     * @throws TradeException
     */
    @Override
    public String getXDXRInfo(String stockCode) throws TradeException, ParseException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        int nConnID = ConnectPool.getConnection();
        tdxLibrary.TdxHq_GetXDXRInfo(nConnID, market.getByteId(), stockCode, rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        DataTableDto dt = new DataTableDto(stockCode, result);
        List<ExclusionExdividendDto> exdividendModelList = new ArrayList<ExclusionExdividendDto>();
        for (int i = 0; i < dt.rows(); i++) {
            ExclusionExdividendDto exclusionExdividendDto = new ExclusionExdividendDto();
            exclusionExdividendDto.read(dt.getRow(i));
            exdividendModelList.add(exclusionExdividendDto);
        }
        return JSONObject.toJSON(exdividendModelList).toString();
    }

    /**
     * 获取财务信息
     *
     * @param stockCode 证券代码
     * @return
     * @throws TradeException
     */
    @Override
    public String getFinanceInfo(String stockCode) throws TradeException, ParseException {
        Preconditions.checkState(stockCode != null, "stockCode参数不能为null.");
        ExchangeId market = StockUtils.getExchangeId(stockCode);
        Preconditions.checkState(market != null, "无法识别股票代码属于那个证券市场.");

        byte[] errData = new byte[256];
        byte[] rltData = new byte[1024 * 1024];
        int nConnID = ConnectPool.getConnection();
        tdxLibrary.TdxHq_GetFinanceInfo(nConnID, market.getByteId(), stockCode, rltData, errData);
        String result = Native.toString(rltData, "GBK");
        String error = Native.toString(errData, "GBK");
        if (checkError(error)) {
            throw new TradeException(error);
        }
        DataTableDto dt = new DataTableDto(stockCode, result);
        List<FinanceInfoDto> financeInfoDtoList = new ArrayList<FinanceInfoDto>();
        for (int i = 0; i < dt.rows(); i++) {
            FinanceInfoDto financeInfoDto = new FinanceInfoDto();
            financeInfoDto.read(dt.getRow(i));
            financeInfoDtoList.add(financeInfoDto);
        }
        return JSONObject.toJSON(financeInfoDtoList).toString();
    }

    private boolean checkError(String error) {
        if (StringUtils.isNotBlank(error)) {
            return true;
        } else {
            return false;
        }
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

    public void close() {
        connPool.close();
    }
}

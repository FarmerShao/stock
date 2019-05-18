package com.farmershao.stock.trade.api;


import com.farmershao.stock.trade.constant.ExchangeId;
import com.farmershao.stock.trade.constant.KCate;
import com.farmershao.stock.trade.dto.QuoteDto;
import com.farmershao.stock.trade.dto.SjsSharesListDto;
import com.farmershao.stock.trade.exception.TradeException;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***
 * 普通行情API
 */
public interface TradeHqService {


    /**
     * 获取市场内所有证券的数量
     *
     * @param market 交易所（市场）
     * @return 证券数量
     * @throws TradeException
     */
    int getSecurityCount(ExchangeId market) throws TradeException;
  

    /**
     * 获取市场内所有证券的数量
     *
     * @return 证券数量
     * @throws TradeException
     */
    public int getSecurityCount(byte id) throws TradeException ;

    /**
     * 获取市场内某个范围内的1000支股票的股票代码
     *
     * @param market 交易所（市场）
     * @param start  范围开始位置,第一个股票是0, 第二个是1, 依此类推,位置信息依据getSecurityCount返回的证券总数确定
     * @param count  范围的大小，API 执行后,保存了实际返回的股票数目
     * @return 股票列表
     * @throws TradeException
     */
    public List<SjsSharesListDto> getSecurityList(ExchangeId market, int start, int count) throws TradeException ;

    /**
     * 批量获取多个证券的五档报价数据
     *
     * @param stockCodes 要查询的证券代码(最大80，不同券商可能不一样,具体数目请自行咨询券商或测试)
     * @return 执行后, 返回返回的Quotes数据，如果stockCodes数量太多，那么返回的数量将不会有那么多
     * @throws TradeException
     */
    public List<QuoteDto> getSecurityQuotes(List<String> stockCodes) throws TradeException ;

    /**
     * 数组大小最大为80
     * @param array 股票代码数组
     * @return
     * @throws Exception
     */
    public List<QuoteDto> getSecurityQuotes(String[] array) throws Exception ;
    /**
     * 批量获取多个证券的五档报价数据
     *
     * @param stockCodes 要查询的证券代码(最大80，不同券商可能不一样,具体数目请自行咨询券商或测试)
     * @param bourseIDs 0深圳，1上海
     * @return 执行后, 返回返回的Quotes数据，如果stockCodes数量太多，那么返回的数量将不会有那么多
     * @throws TradeException
     */
    public String getSecurityQuotes(String[] stockCodes, int[] bourseIDs) throws TradeException, ParseException ;

    /**
     * 批量获取多个证券的五档报价数据
     *
     * @param stockCodes 要查询的证券代码(最大80，不同券商可能不一样,具体数目请自行咨询券商或测试)
     * @param bourseID 0深圳，1上海
     * @return 执行后, 返回返回的Quotes数据，如果stockCodes数量太多，那么返回的数量将不会有那么多
     * @throws TradeException
     */
    public Map<String, QuoteDto> getSecurityQuotes(List<String> stockCodes, int bourseID) throws TradeException, ParseException ;
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
    public String getSecurityBars(int cCate, String stockCode, int start, int count) throws TradeException ;
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
    public String getSecurityBars(KCate cCate, String stockCode, int start, int count) throws TradeException ;

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
    public String getIndexBars(KCate cCate, String stockCode, int start, int count) throws TradeException ;
    /**
     * 获取分时数据
     *
     * @param stockCode 证券代码
     * @return
     * @throws TradeException
     */
    public String getMinuteTimeData(String stockCode) throws TradeException ;
    /**
     * 获取历史分时数据
     *
     * @param stockCode 股票代码
     * @param date      日期, 比如2014年1月1日为整数20140101
     * @return
     * @throws TradeException
     */
    public String getHistoryMinuteTimeData(String stockCode, int date) throws TradeException ;
    /**
     * 获取历史分时数据
     *
     * @param stockCode 证券代码
     * @param date      日期
     * @return
     * @throws TradeException
     */
    public String getHistoryMinuteTimeData(String stockCode, Date date) throws TradeException ;

    /**
     * 获取某个范围的分笔成交数据
     *
     * @param stockCode 股票代码
     * @param start     范围开始位置,最后一条K线位置是0, 前一条是1, 依此类推
     * @param count     范围大小，表示用户要请求的K线数目
     * @return
     * @throws TradeException
     */
    public String getTransactionData(String stockCode, int start, int count) throws TradeException ;

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
    public String getHistoryTransactionData(String stockCode, int start, int count, Date date) throws TradeException ;

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
    public String getHistoryTransactionData(String stockCode, int start, int count, int date) throws TradeException ;


    /**
     * 获取F10资料的类别
     *
     * @param stockCode 证券代码
     * @return F10资料数据
     * @throws TradeException
     */
    public String getCompanyInfoCategory(String stockCode) throws TradeException ;

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
    public String getCompanyInfoContent(String stockCode, String filename, int start, int length) throws TradeException ;

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
    public String getCompanyInfoContent(String stockCode, String cate) throws TradeException ;

    /**
     * 获取F10资料的所有分类的内容
     *
     * @param stockCode 股票代碼
     * @return
     * @throws TradeException
     */
    public String getCompanyInfoContents(String stockCode) throws TradeException ;

    /**
     * 获取除权除息信息
     *
     * @param stockCode 证券代码
     * @return
     * @throws TradeException
     */
    public String getXDXRInfo(String stockCode) throws TradeException, ParseException ;

    /**
     * 获取财务信息
     *
     * @param stockCode 证券代码
     * @return
     * @throws TradeException
     */
    public String getFinanceInfo(String stockCode) throws TradeException, ParseException ;

   
}

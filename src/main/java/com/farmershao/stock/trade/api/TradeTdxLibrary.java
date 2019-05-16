package com.farmershao.stock.trade.api;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.ShortByReference;

/**
 * 通达信交易 API（多账户版）
 *
 * @author Shao Yu
 * @since 2018/4/25 9:50
 **/
public interface TradeTdxLibrary extends Library {

    /**
     * 打开通达信实例
     *
     * @param nClientType      券商客户端类型；使用固定参数为14
     * @param pszClientVersion 券商客户端的版本号；固定参数为“6.40”
     * @param nCliType         券商客户端指令接口类型；固定参数为12
     * @param nVipTermFlag     终端标志代码；固定参数为0
     * @param ErrInfo          此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间；没出错时为空字符串。
     * @return 客户端ID，负值 失败时返回-1
     */
    int OpenTdx(int nClientType, String pszClientVersion, int nCliType, int nVipTermFlag, byte[] ErrInfo);

    /**
     * 关闭通达信实例
     */
    void CloseTdx();

    /**
     * 交易账户登录
     *
     * @param nQsid           券商标识 从券商客户端的tcoem.xml 文件中获取券商代码
     * @param pszHost         券商交易服务器IP
     * @param nPort           券商交易服务器端口
     * @param pszVersion      设置通达信客户端的版本号
     * @param nYybId          营业部代码 从券商客户端etrade.xml 的[Branch]板块可以查询对应的营业部ID
     * @param nAccountType    登录账户类型 从券商客户端etrade.xml 的[LoginMode]板块可以查询对应的类型
     * @param pszAccountNo    客户账户号（Client Account），即用户在券商客户端登陆时需要输入的账户号，可以是客户号、资金账户号、股东代码等，因登录类型不同而异
     * @param pszTradeAccount 登录券商通达信软件，查询股东列表，从股东列表内的资金帐号一列获取
     * @param pszJyPassword   交易密码
     * @param pszTxPassword   通讯密码
     * @param ErrInfo         此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没错为空字符串
     * @return 成功则返回客户端ID，失败则返回-1
     */
    int Logon(int nQsid, String pszHost, int nPort, String pszVersion, short nYybId, short nAccountType, String pszAccountNo, String pszTradeAccount, String pszJyPassword, String pszTxPassword, byte[] ErrInfo);

    /**
     * 交易账户登出
     *
     * @param ClientID 客户端ID
     */
    void Logoff(int ClientID);

    /**
     * 判断交易连接是否正确
     *
     * @param nClientID 客户端ID
     * @return 1成功，0失败
     */
    boolean IsConnectOK(int nClientID);

    /**
     * 查询交易数据
     *
     * @param ClientID 客户端ID
     * @param Category 查询信息的种类
     * @param Result   此API 执行返回后，Result 内保存了返回的查询数据, 形式为表格数据，行数据之间通过\n 字符分割，列数据之间通过\t 分隔。一般要分配1024*1024 字节的空间
     * @param ErrInfo  此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return
     */
    int QueryData(int ClientID, int Category, byte[] Result, byte[] ErrInfo);

    /**
     * 委托下单
     *
     * @param ClientID  客户端ID
     * @param Category  委托的种类
     * @param PriceType 报价方式
     * @param Gddm      股东代码；交易上海股票填上海的股东代码，交易深圳的股票填入深圳的股东代码
     * @param Zqdm      证券代码
     * @param Price     委托价格
     * @param Quantity  委托数量
     * @param Result    此API 执行返回后，Result 内保存了返回的查询数据, 形式为表格数据，行数据之间通过\n 字符分割，列数据之间通过\t 分隔。一般要分配1024*1024 字节的空间
     * @param ErrInfo   此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return
     */
    int SendOrder(int ClientID, int Category, int PriceType, String Gddm, String Zqdm, float Price, int Quantity, byte[] Result, byte[] ErrInfo);

    /**
     * 撤销委托
     *
     * @param ClientID   客户端ID
     * @param ExchangeID 交易所ID 0 深圳(招商证券，普通账户深圳是2)；1 上海
     * @param pszOrderID 表示要撤的目标委托的编号
     * @param Result     此API 执行返回后，Result 内保存了返回的查询数据, 形式为表格数据，行数据之间通过\n 字符分割，列数据之间通过\t 分隔。一般要分配1024*1024 字节的空间,出错时为空字符串
     * @param ErrInfo    此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return
     */
    int CancelOrder(int ClientID, String ExchangeID, String pszOrderID, byte[] Result, byte[] ErrInfo);

    /**
     * 获取证券的实时五档行情
     *
     * @param nClientID 客户端ID
     * @param pszZqdm   证券代码
     * @param Result    此API 执行返回后，Result 内保存了返回的查询数据, 形式为表格数据，行数据之间通过\n 字符分割，列数据之间通过\t 分隔。一般要分配1024*1024 字节的空间,出错时为空字符串
     * @param ErrInfo   此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return
     */
    int GetQuote(int nClientID, String pszZqdm, byte[] Result, byte[] ErrInfo);

    /**
     * 查询当前可交易股票数量
     *
     * @param nClientID  客户端ID
     * @param nCategory  委托的种类
     * @param nPriceType 报价方式
     * @param pszGddm    股东代码
     * @param pszZqdm    证券代码
     * @param fPrice     委托价格
     * @param Result     此API 执行返回后，Result 内保存了返回的查询数据, 形式为表格数据，行数据之间通过\n 字符分割，列数据之间通过\t 分隔。一般要分配1024*1024 字节的空间,出错时为空字符串
     * @param ErrInfo    此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功时返回当前可卖股票数量，失败时返回负数
     */
    int GetTradableQuantity(int nClientID, int nCategory, int nPriceType, String pszGddm, String pszZqdm, float fPrice, byte[] Result, byte[] ErrInfo);

    /**
     * 融资融券账户直接还款
     *
     * @param ClientID 客户端ID
     * @param Amount   还款金额
     * @param Result   此API 执行返回后，Result 内保存了返回的查询数据, 形式为表格数据，行数据之间通过\n 字符分割，列数据之间通过\t 分隔。一般要分配1024*1024 字节的空间,出错时为空字符串
     * @param ErrInfo  此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return
     */
    int Repay(int ClientID, String Amount, byte[] Result, byte[] ErrInfo);

    /**
     * 查询历史交易数据
     *
     * @param ClientID  客户端ID
     * @param Category  查询信息的种类
     * @param StartDate 开始日期，格式为yyyyMMdd,比如2017 年2 月1 日为20170201
     * @param EndDate   结束日期，格式为yyyyMMdd,比如2017 年2 月1 日为20170201
     * @param Result    此API 执行返回后，Result 内保存了返回的查询数据, 形式为表格数据，行数据之间通过\n 字符分割，列数据之间通过\t 分隔。一般要分配1024*1024 字节的空间,出错时为空字符串
     * @param ErrInfo   此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return
     */
    int QueryHistoryData(int ClientID, int Category, String StartDate, String EndDate, byte[] Result, byte[] ErrInfo);

    /**
     * 单账户批量查询各类交易数据
     *
     * @param ClientID   客户端ID
     * @param Category   信息种类的数组，第i 个元素表示第i 个查询的信息种类
     * @param Count      查询的个数，即数组的长度
     * @param ResultOK   返回数据的数组, 第i 个元素表示第i 个委托的返回信息
     * @param ResultFail 返回错误信息的数组, 第i 个元素表示第i 个委托的返回信息
     * @param ErrInfo    错误信息的数组，返回全局性的错误信息
     * @return
     */
    int QueryDatas(int ClientID, int[] Category, int Count, Pointer[] ResultOK, Pointer[] ResultFail, Pointer[] ErrInfo);

    /**
     * 单账户批量委托交易证券
     *
     * @param ClientID  客户端ID
     * @param Category  委托种类的数组，第i 个元素表示第i 个委托的种类
     * @param PriceType 报价方式的数组，第i 个元素表示第i 个委托的报价方式
     * @param Gddm      股东代码数组，第i 个元素表示第i 个委托的股东代码；交易上海股票填上海的股东代码，交易深圳的股票填入深圳的股东代码
     * @param Zqdm      证券代码数组，第i 个元素表示第i 个委托的证券代码
     * @param Price     委托价格数组，第i 个元素表示第i 个委托的委托价格
     * @param Quantity  委托数量数组，第i 个元素表示第i 个委托的委托数量
     * @param Count     委托的个数，即数组的长度
     * @param Result    返回数据的数组,, 第i 个元素表示第i 个委托的返回信息. 此API 执行返回后，Result[i]含义同上
     * @param ErrInfo   错误信息的数组，第i 个元素表示第i 个委托的错误信息. 此API 执行返回后，ErrInfo[i]含义同上
     * @return
     */
    int SendOrders(int ClientID, int[] Category, int[] PriceType, String[] Gddm, String[] Zqdm, float[] Price, int[] Quantity, int Count, Pointer[] Result, Pointer[] ErrInfo);

    /**
     * 批量撤委托
     *
     * @param ClientID   客户端ID
     * @param ExchangeID 交易所ID 0 深圳(招商证券，普通账户深圳是2);要撤的目标委托的编号
     * @param pszOrderID 要撤的目标委托的编号
     * @param Count      要撤委托的个数，即数组的长度
     * @param Result     返回数据的数组, 第i 个元素表示第i 个委托的返回信息
     * @param ErrInfo    错误信息的数组，第i 个元素表示第i 个委托的错误信息
     * @return 失败时返回负数
     */
    int CancelOrders(int ClientID, String[] ExchangeID, String[] pszOrderID, int Count, Pointer[] Result, Pointer[] ErrInfo);

    /**
     * 单账户批量五档
     *
     * @param ClientID 客户端ID
     * @param Zqdm     证券代码
     * @param Count    待查询证券的数量，即证券代码数组的长度
     * @param Result   返回数据的数组, 第i 个元素表示第i 个委托的返回信息
     * @param ErrInfo  错误信息的数组，第i 个元素表示第i 个委托的错误信息
     * @return 失败时返回负数
     */
    int GetQuotes(int ClientID, String[] Zqdm, int Count, Pointer[] Result, Pointer[] ErrInfo);

    /**
     * 批量不同账户各类交易
     *
     * @param ClientID 客户端ID
     * @param Category 信息种类的数组
     * @param Count    查询的个数，即数组的长度
     * @param Result   返回数据的数组, 第i 个元素表示第i 个委托的返回信息
     * @param ErrInfo  错误信息的数组，第i 个元素表示第i 个委托的错误信息
     * @return 失败时返回负数
     */
    int QueryMultiAccountsDatas(int[] ClientID, int[] Category, int Count, Pointer[] Result, Pointer[] ErrInfo);

    /**
     * 向不同账户批量下委托单
     *
     * @param ClientID  客户端ID 数组,第i 个元素表示第i 个委托的客户端ID
     * @param Category  委托种类的数组，第i 个元素表示第i 个委托的种类
     * @param PriceType 报价方式的数组, 第i 个元素表示第i 个委托的报价方式
     * @param Gddm      股东代码数组，第i 个元素表示第i 个委托的股东代码
     * @param Zqdm      证券代码数组，第i 个元素表示第i 个委托的证券代码
     * @param Price     委托价格数组，第i 个元素表示第i 个委托的委托价格
     * @param Quantity  委托数量数组，第i 个元素表示第i 个委托的委托数量
     * @param Count     委托的个数，即数组的长度
     * @param Result    返回数据的数组, 第i 个元素表示第i 个委托的返回信息
     * @param ErrInfo   错误信息的数组，第i 个元素表示第i 个委托的错误信息
     * @return 失败时返回负数
     */
    int SendMultiAccountsOrders(int[] ClientID, int[] Category, int[] PriceType, String[] Gddm, String[] Zqdm, float[] Price, int[] Quantity, int Count, Pointer[] Result, Pointer[] ErrInfo);

    /**
     * 批量向不同账户撤单
     *
     * @param ClientID   客户端ID
     * @param ExchangeID 交易所ID 0 深圳(招商证券，普通账户深圳是2);1 上海
     * @param pszOrderID 要撤的目标委托的编号
     * @param Count      要撤委托的个数，即数组的长度
     * @param Result     返回数据的数组, 第i 个元素表示第i 个委托的返回信息
     * @param ErrInfo    错误信息的数组，第i 个元素表示第i 个委托的错误信息
     * @return 失败时返回负数
     */
    int CancelMultiAccountsOrders(int[] ClientID, String[] ExchangeID, String[] pszOrderID, int Count, Pointer[] Result, Pointer[] ErrInfo);

    /**
     * 批量向不同账户获取实时五档报价
     *
     * @param ClientID 客户端ID 数组,第i 个元素表示第i 个查询的客户端ID
     * @param Zqdm     证券代码
     * @param Count    待查询证券的数量，即证券代码数组的长度
     * @param Result   返回数据的数组, 第i 个元素表示第i 个委托的返回信息
     * @param ErrInfo  错误信息的数组，第i 个元素表示第i 个委托的错误信息
     * @return 失败时返回负数
     */
    int GetMultiAccountsQuotes(int[] ClientID, String[] Zqdm, int Count, Pointer[] Result, Pointer[] ErrInfo);

    /** 普通行情API
     * 1）应用程序先调用TdxHq_Connect 连接通达信行情服务器；
     * 2）然后才可以调用其他接口获取行情数据,应用程序应自行处理网络断线问题, 接口是线程安全的。
     */

    /**
     * 连接通达信行情服务器
     *
     * @param IP      服务器IP，可在券商软件登录界面中的通讯设置中查得
     * @param Port    服务器端口
     * @param Result  此API 执行返回后，Result 内保存了返回的证券代码信息,形式为表格数据.行数据之间通过\n 字符分割，列数据之间通过\t 分隔。一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 行情连接代码
     */
    int TdxHq_Connect(String IP, short Port, byte[] Result, byte[] ErrInfo);

    /**
     * 断开同服务器的连接
     *
     * @param nConnID 行情连接代码
     * @return 无
     */
    int TdxHq_Disconnect(int nConnID);

    /**
     * 获取指定市场内的证券数目
     *
     * @param nConnID 行情连接代码
     * @param Market  市场代码（0 深圳，1 上海）
     * @param pnCount 此API 执行返回后，保存了返回的证券数量
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明
     * @return 成功返回true, 失败返回false
     */
    boolean TdxHq_GetSecurityCount(int nConnID, byte Market, ShortByReference pnCount, byte[] ErrInfo);

    /**
     * 获取市场内某个范围内的1000 支股票的股票代码
     *
     * @param nConnID 行情连接代码
     * @param Market  市场代码（0 深圳，1 上海）
     * @param Start   范围开始位置,第一个股票是0, 第二个是1, 依此类推,位置信息依据TdxHq_GetSecurityCount 返回的证券总数确定
     * @param Count   范围的大小，API 执行后,保存了实际返回的股票数目
     * @param Result  此API 执行返回后，Result 内保存了返回的证券代码信息,形式为表格数据.行数据之间通过\n 字符分割，列数据之间通过\t 分隔。一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxHq_GetSecurityList(int nConnID, byte Market, short Start, ShortByReference Count, byte[] Result, byte[] ErrInfo);

    /**
     * 批量获取多个证券的五档报价数据
     *
     * @param nConnID 行情连接代码
     * @param nMarket 市场代码 0 深圳，1 上海
     * @param pszZqdm 证券代码
     * @param Count   API 执行前,表示用户要请求的证券数目,最大80；API 执行后,保存了实际返回的数目
     * @param Result  此API 执行返回后，Result 内保存了返回的证券代码信息,形式为表格数
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明
     * @return 成功返回true, 失败返回false
     */
    boolean TdxHq_GetSecurityQuotes(int nConnID, byte[] nMarket, String[] pszZqdm, ShortByReference Count, byte[] Result, byte[] ErrInfo);

    /**
     * 获取某个范围内的证券K 线数据
     *
     * @param nConnID  行情连接代码
     * @param Category K 线种类
     * @param Market   市场代码 0 深圳，1 上海
     * @param Zqdm     证券代码
     * @param Start    范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param Count    范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目, 最大值800
     * @param Result   此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据，出错时为空字符串
     * @param ErrInfo  此API 执行返回后，如果出错，保存了错误信息说明，没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxHq_GetSecurityBars(int nConnID, byte Category, byte Market, String Zqdm, short Start, ShortByReference Count, byte[] Result, byte[] ErrInfo);

    /**
     * 获取某个范围内的指数K 线数据
     *
     * @param nConnID  行情连接代码
     * @param Category K 线种类
     * @param Market   市场代码 0 深圳，1 上海
     * @param Zqdm     证券代码
     * @param Start    范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param Count    范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目, 最大值800
     * @param Result   此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据，出错时为空字符串
     * @param ErrInfo  此API 执行返回后，如果出错，保存了错误信息说明，没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxHq_GetIndexBars(int nConnID, byte Category, byte Market, String Zqdm, short Start, ShortByReference Count, byte[] Result, byte[] ErrInfo);

    /**
     * 获取分时行情数据
     *
     * @param nConnID 行情连接代码
     * @param Market  市场代码 0 深圳，1 上海
     * @param Zqdm    证券代码
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据，出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明，没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxHq_GetMinuteTimeData(int nConnID, byte Market, String Zqdm, byte[] Result, byte[] ErrInfo);

    /**
     * 获取历史分时行情数据
     *
     * @param nConnID 行情连接代码
     * @param Market  市场代码 0 深圳，1 上海
     * @param Zqdm    证券代码
     * @param date    日期, 比如2017 年2 月1 日为整数20170201
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据，出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明，没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxHq_GetHistoryMinuteTimeData(int nConnID, byte Market, String Zqdm, int date, byte[] Result, byte[] ErrInfo);

    /**
     * 获取某个范围的分笔成交数据
     *
     * @param nConnID 行情连接代码
     * @param Market  市场代码 0 深圳，1 上海
     * @param Zqdm    证券代码
     * @param Start   范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param Count   范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据，出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明，没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxHq_GetTransactionData(int nConnID, byte Market, String Zqdm, short Start, ShortByReference Count, byte[] Result, byte[] ErrInfo);

    /**
     * 获取某个范围的历史分笔成交数据
     * 券商行情服务器侧限制最大允许单次返回2000 条记录，如果需要更多数据则需要多次调用循环读取
     *
     * @param nConnID 行情连接代码
     * @param Market  市场代码 0 深圳，1 上海
     * @param Zqdm    证券代码
     * @param Start   范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param Count   范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目
     * @param date    日期, 比如2017 年2 月1 日为整数20170201
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据，出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明，没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxHq_GetHistoryTransactionData(int nConnID, byte Market, String Zqdm, short Start, ShortByReference Count, int date, byte[] Result, byte[] ErrInfo);

    /**
     * 获取F10 资料的类别
     *
     * @param nConnID 行情连接代码
     * @param Market  市场代码 0 深圳，1 上海
     * @param Zqdm    证券代码
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据，出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明，没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxHq_GetCompanyInfoCategory(int nConnID, byte Market, String Zqdm, byte[] Result, byte[] ErrInfo);

    /**
     * 获取某一类F10 资料的具体内容
     *
     * @param nConnID  行情连接代码
     * @param Market   市场代码 0 深圳，1 上海
     * @param Zqdm     证券代码
     * @param FileName 类目的文件名, 由TdxHq_GetCompanyInfoCategory 返回信息中获取
     * @param Start    类目的开始位置, 由TdxHq_GetCompanyInfoCategory 返回信息中获取
     * @param Length   类目的长度, 由TdxHq_GetCompanyInfoCategory 返回信息中获取
     * @param Result   此API 执行返回后，Result 内保存了返回的查询数据。出错时为空字符串
     * @param ErrInfo  此API 执行返回后，如果出错，保存了错误信息说明，没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxHq_GetCompanyInfoContent(int nConnID, byte Market, String Zqdm, String FileName, int Start, int Length, byte[] Result, byte[] ErrInfo);

    /**
     * 获取除权除息数据
     *
     * @param nConnID 行情连接代码
     * @param Market  市场代码 0 深圳，1 上海
     * @param Zqdm    证券代码
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据。出错时为空字符串。
     *                返回信息的保留字段含义：1 除权除息,2 送配股上市,3 非流通股上市,4 未知股本变动,5 股本变化,6 增发新股,7 股份回购
     *                8 增发新股上市,9 转配股上市,10 可转债上市,11 扩缩股,12 非流通股缩股,13 送认购权证,14 送认沽权证
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明，没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxHq_GetXDXRInfo(int nConnID, byte Market, String Zqdm, byte[] Result, byte[] ErrInfo);

    /**
     * 获取财务数据
     *
     * @param nConnID 行情连接代码
     * @param Market  市场代码 0 深圳，1 上海
     * @param Zqdm    证券代码
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据，出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明，没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxHq_GetFinanceInfo(int nConnID, byte Market, String Zqdm, byte[] Result, byte[] ErrInfo);

    /** Level 2 行情API*/

    /**
     * 连接券商行情服务器
     *
     * @param nConnID  行情连接代码
     * @param pszIP    服务器IP,可在演示版内查得
     * @param port     服务器端口
     * @param user     通达信Level 2 行情账户名，用户需向通达信购买
     * @param password 通达信Level 2 行情账户密码
     * @param Result   此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据，出错时为空字符串
     * @param ErrInfo  此API 执行返回后，如果出错，保存了错误信息说明，没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    int TdxL2Hq_Connect(int nConnID, String pszIP, IntByReference port, String user, String password, byte[] Result, byte[] ErrInfo);

    /**
     * 断开同服务器的连接
     *
     * @param nConnID 行情连接代码
     */
    void TdxL2Hq_Disconnect(int nConnID);

    /**
     * 获取指定市场内的所有证券数量
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码 0 深圳，1 上海
     * @param Count   此API 执行返回后，保存了返回的证券数量
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetSecurityCount(int nConnID, byte market, ShortByReference Count, byte[] ErrInfo);

    /**
     * 获取市场内某个范围内的1000 支股票的股票代码
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码 0 深圳，1 上海
     * @param start   范围开始位置,第一个股票是0, 第二个是1, 依此类推；位置信息依据TdxL2Hq_GetSecurityCount 返回的证券总数量确定
     * @param Count   范围大小，API 执行后,保存了实际返回的股票数目
     * @param Result  此API 执行返回后，Result 内保存了返回的证券代码信息,形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔。一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetSecurityList(int nConnID, byte market, int start, ShortByReference Count, byte[] Result, byte[] ErrInfo);

    /**
     * 批量获取多个证券的五档报价数据
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码 0 深圳，1 上海
     * @param zqdm    证券代码, nCount 个证券代码组成的数组
     * @param Count   API 执行前,表示用户要请求的证券数目,最大80（不同券商可能不同，具体请咨询券商或测试）
     * @param Result  此API 执行返回后，Result 内保存了返回的证券代码信息,形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔。一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetSecurityQuotes(int nConnID, byte[] market, String[] zqdm, ShortByReference Count, byte[] Result, byte[] ErrInfo);

    /**
     * 获取证券在某个范围内的K 线数据
     *
     * @param nConnID  行情连接代码
     * @param category K 线种类
     * @param market   市场代码 0 深圳，1 上海
     * @param zqdm     证券代码
     * @param start    范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param Count    范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目, 最大值800
     * @param Result   此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo  此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetSecurityBars(int nConnID, int category, byte market, String zqdm, int start, ShortByReference Count, byte[] Result, byte[] ErrInfo);

    /**
     * 获取指数在某个范围内的K 线数据
     *
     * @param nConnID  行情连接代码
     * @param category K 线种类
     * @param market   市场代码 0 深圳，1 上海
     * @param zqdm     证券代码
     * @param start    范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param Count    范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目, 最大值800
     * @param Result   此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo  此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetIndexBars(int nConnID, int category, byte market, String zqdm, int start, ShortByReference Count, byte[] Result, byte[] ErrInfo);

    /**
     * 获取分时行情数据
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码 0 深圳，1 上海
     * @param zqdm    证券代码
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetMinuteTimeData(int nConnID, byte market, String zqdm, byte[] Result, byte[] ErrInfo);

    /**
     * 获取历史分时行情数据
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码 0 深圳，1 上海
     * @param zqdm    证券代码
     * @param date    日期, 比如2017 年2 月1 日为整数20170201
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetHistoryMinuteTimeData(int nConnID, byte market, String zqdm, int date, byte[] Result, byte[] ErrInfo);

    /**
     * 获取某个范围内的分笔成交数据
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码 0 深圳，1 上海
     * @param zqdm    证券代码
     * @param start   范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param Count   范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetTransactionData(int nConnID, byte market, String zqdm, int start, ShortByReference Count, byte[] Result, byte[] ErrInfo);

    /**
     * 获取某个范围内的历史分笔成交数据
     * 券商行情服务器侧限制最大允许单次返回2000 条记录，如果需要更多数据则需要多次调用循环读取
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码 0 深圳，1 上海
     * @param zqdm    证券代码
     * @param start   范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param Count   范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目
     * @param date    日期, 比如2017 年2 月1 日为整数20170201
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetHistoryTransactionData(int nConnID, byte market, String zqdm, int start, ShortByReference Count, int date, byte[] Result, byte[] ErrInfo);

    /**
     * 获取F10 资料的类别
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码 0 深圳，1 上海
     * @param zqdm    证券代码
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetCompanyInfoCategory(int nConnID, byte market, String zqdm, byte[] Result, byte[] ErrInfo);

    /**
     * 获取某一类F10 资料的具体内容
     *
     * @param nConnID  行情连接代码
     * @param market   市场代码 0 深圳，1 上海
     * @param zqdm     证券代码
     * @param fileName 类目的文件名, 由TdxL2Hq_GetCompanyInfoCategory 返回信息中获取
     * @param start    类目的开始位置, 由TdxL2Hq_GetCompanyInfoCategory 返回信息中获取
     * @param length   类目的长度, 由TdxL2Hq_GetCompanyInfoCategory 返回信息中获取
     * @param Result   此API 执行返回后，Result 内保存了返回的查询数据。出错时为空字符串
     * @param ErrInfo  此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetCompanyInfoContent(int nConnID, byte market, String zqdm, String fileName, int start, int length, byte[] Result, byte[] ErrInfo);

    /**
     * 获取除权除息数据
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码 0 深圳，1 上海
     * @param zqdm    证券代码
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetXDXRInfo(int nConnID, byte market, String zqdm, byte[] Result, byte[] ErrInfo);

    /**
     * 获取财务数据
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码 0 深圳，1 上海
     * @param zqdm    证券代码
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetFinanceInfo(int nConnID, byte market, String zqdm, byte[] Result, byte[] ErrInfo);

    /**
     * 批量获取多个证券的十档报价
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码;第i 个元素表示第i 个证券的市场代码 0 深圳，1 上海
     * @param zqdm    证券代码;Count 个证券代码组成的数组
     * @param count   API 执行前,表示用户要请求的证券数目,最大100 (不同券商可能不同，具体请咨询券商或测试)
     *                API 执行后,保存了实际返回的股票数目
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetSecurityQuotes10(int nConnID, byte[] market, String[] zqdm, int count, byte[] Result, byte[] ErrInfo);

    /**
     * 获取某个范围内的逐笔成交数据
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码 0 深圳，1 上海
     * @param zqdm    证券代码
     * @param start   范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param Count   范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目, 最大值2000
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetDetailTransactionData(int nConnID, byte market, String zqdm, int start, ShortByReference Count, byte[] Result, byte[] ErrInfo);

    /**
     * 获取某个范围内的逐笔委托数据
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码 0 深圳，1 上海
     * @param zqdm    证券代码
     * @param start   范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param Count   范围大小，API 执行前,表示用户要请求的K 线数目；API 执行后,保存了实际返回的K 线数目；最大2000
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetDetailOrderData(int nConnID, byte market, String zqdm, int start, ShortByReference Count, byte[] Result, byte[] ErrInfo);

    /**
     * 获取买卖队列数据
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码 0 深圳，1 上海
     * @param zqdm    证券代码
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxL2Hq_GetBuySellQueue(int nConnID, byte market, String zqdm, byte[] Result, byte[] ErrInfo);


    /** 扩展行情API*/

    /**
     * 连接通达信扩展行情服务器
     *
     * @param ip      服务器IP，可在券商软件登录界面中的通讯设置中查得
     * @param port    服务器端口
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回行情连接代码, 失败返回0
     */
    int TdxExHq_Connect(String ip, int port, byte[] Result, byte[] ErrInfo);

    /**
     * 断开同服务器的连接
     *
     * @param nConnID 行情连接代码
     */
    void TdxExHq_Disconnect(int nConnID);

    /**
     * 获取扩展行情中支持的各个市场的市场代码
     *
     * @param nConnID 行情连接代码
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回行情连接代码, 失败返回0
     */
    boolean TdxExHq_GetMarkets(int nConnID, byte[] Result, byte[] ErrInfo);

    /**
     * 获取所有商品的总数量
     *
     * @param nConnID 行情连接代码
     * @param count   此API 执行返回后，保存了返回的商品总数
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回行情连接代码, 失败返回0
     */
    boolean TdxExHq_GetInstrumentCount(int nConnID, IntByReference count, byte[] ErrInfo);

    /**
     * 获取指定范围内商品的代码
     *
     * @param nConnID 行情连接代码
     * @param start   范围的开始位置, 由TdxExHq_GetInstrumentCount 返回信息中确定
     * @param count   商品数量, 由TdxExHq_GetInstrumentCount 返回信息中获取
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxExHq_GetInstrumentInfo(int nConnID, int start, IntByReference count, byte[] Result, byte[] ErrInfo);

    /**
     * 获取指定商品的盘口五档报价
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码
     * @param zqdm    商品代码, pnCount 个证券代码组成的数组
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxExHq_GetInstrumentQuote(int nConnID, byte market, String zqdm, byte[] Result, byte[] ErrInfo);

    /**
     * 获取指定商品的K 线数据
     *
     * @param nConnID  行情连接代码
     * @param category K 线种类
     * @param market   市场代码
     * @param zqdm     商品代码
     * @param start    范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param Count    范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目, 最大值800
     * @param Result   此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo  此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxExHq_GetInstrumentBars(int nConnID, int category, byte market, String zqdm, int start, ShortByReference Count, byte[] Result, byte[] ErrInfo);

    /**
     * 获取指定商品的分时行情数据
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码
     * @param zqdm    证券代码
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxExHq_GetMinuteTimeData(int nConnID, byte market, String zqdm, byte[] Result, byte[] ErrInfo);

    /**
     * 获取指定商品的历史分时行情数据
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码
     * @param zqdm    证券代码
     * @param date    日期, 比如2017 年2 月1 日为整数20170201
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxExHq_GetHistoryMinuteTimeData(int nConnID, byte market, String zqdm, int date, byte[] Result, byte[] ErrInfo);

    /**
     * 获取指定商品在某个范围的分笔成交数据
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码
     * @param zqdm    证券代码
     * @param start   范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param Count   范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxExHq_GetTransactionData(int nConnID, byte market, String zqdm, int start, ShortByReference Count, byte[] Result, byte[] ErrInfo);

    /**
     * 获取指定商品在某个范围的历史分笔成交数据
     * 券商行情服务器侧限制最大允许单次返回2000 条记录，如果需要更多数据则需要多次调用循环读取
     *
     * @param nConnID 行情连接代码
     * @param market  市场代码
     * @param zqdm    证券代码
     * @param start   范围开始位置,最后一条K 线位置是0, 前一条是1, 依此类推
     * @param Count   范围大小，API 执行前,表示用户要请求的K 线数目, API 执行后,保存了实际返回的K 线数目
     * @param date    日期, 比如2017 年2 月1 日为整数20170201
     * @param Result  此API 执行返回后，Result 内保存了返回的查询数据，形式为表格数据；行数据之间通过\n 字符分割，列数据之间通过\t 分隔；一般要分配1024*1024 字节的空间。出错时为空字符串
     * @param ErrInfo 此API 执行返回后，如果出错，保存了错误信息说明。一般要分配256 字节的空间。没出错时为空字符串
     * @return 成功返回true, 失败返回false
     */
    boolean TdxExHq_GetHistoryTransactionData(int nConnID, byte market, String zqdm, int date, int start, ShortByReference Count, byte[] Result, byte[] ErrInfo);
}

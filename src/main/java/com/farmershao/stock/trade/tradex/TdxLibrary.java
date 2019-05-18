package com.farmershao.stock.trade.tradex;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.ShortByReference;

/**
 * 通达信交易DLL接口
 * <p>
 * Created by kongkp on 2017/1/5.
 */
public interface TdxLibrary extends Library {
	// 基本版函数
	int OpenTdx(short ClientType, String ClientVersion, byte CliType, byte VipTermFlag, byte[] ErrInfo);

	void CloseTdx();

	int Logon(int Qsid, String Host, short Port, String Version, short YybId, byte AccountType, String AccountNo,
              String TradeAccount, String JyPassword, String TxPassword, byte[] ErrInfo);

	void Logoff(int ClientID);

	void QueryData(int ClientID, int Category, byte[] Result, byte[] ErrInfo);

	void SendOrder(int ClientID, int Category, int PriceType, String Gddm, String Zqdm, float Price, int Quantity,
                   byte[] Result, byte[] ErrInfo);

	void CancelOrder(int ClientID, String ExchangeID, String hth, byte[] Result, byte[] ErrInfo);

	void GetQuote(int ClientID, String Zqdm, byte[] Result, byte[] ErrInfo);

	void Repay(int ClientID, String Amount, byte[] Result, byte[] ErrInfo);

	// 普通批量版新增的函数
	void QueryHistoryData(int ClientID, int Category, String StartDate, String EndDate, byte[] Result, byte[] ErrInfo);

	void QueryDatas(int ClientID, int[] Category, int Count, Pointer[] Result, Pointer[] ErrInfo);

	void SendOrders(int ClientID, int[] Category, int[] PriceType, String[] Gddm, String[] Zqdm, float[] Price,
                    int[] Quantity, int Count, Pointer[] Result, Pointer[] ErrInfo);

	void CancelOrders(int ClientID, String[] ExchangeID, String[] hth, int Count, Pointer[] Result, Pointer[] ErrInfo);

	void GetQuotes(int ClientID, String[] Zqdm, int Count, Pointer[] Result, Pointer[] ErrInfo);

	// 高级批量版新增的函数
	void QueryMultiAccountsDatas(int[] ClientID, int[] Category, int Count, Pointer[] Result, Pointer[] ErrInfo);

	void SendMultiAccountsOrders(int[] ClientID, int[] Category, int[] PriceType, String[] Gddm, String[] Zqdm,
                                 float[] Price, int[] Quantity, int Count, Pointer[] Result, Pointer[] ErrInfo);

	void CancelMultiAccountsOrders(int[] ClientID, String[] ExchangeID, String[] hth, int Count, Pointer[] Result,
                                   Pointer[] ErrInfo);

	void GetMultiAccountsQuotes(int[] ClientID, String[] Zqdm, int Count, Pointer[] Result, Pointer[] ErrInfo);

	// 行情查詢API
	int TdxHq_Connect(String IP, short Port, byte[] Result, byte[] ErrInfo);

	void TdxHq_Disconnect(int nConnID);

	boolean TdxHq_GetSecurityCount(int nConnID, byte Market, ShortByReference Result, byte[] ErrInfo);

	boolean TdxHq_GetSecurityList(int nConnID, byte Market, short Start, ShortByReference Count, byte[] Result,
                                  byte[] ErrInfo);

	boolean TdxHq_GetSecurityBars(int nConnID, byte Category, byte Market, String Zqdm, short Start,
                                  ShortByReference Count, byte[] Result, byte[] ErrInfo);

	boolean TdxHq_GetIndexBars(int nConnID, byte Category, byte Market, String Zqdm, short Start,
                               ShortByReference Count, byte[] Result, byte[] ErrInfo);

	boolean TdxHq_GetMinuteTimeData(int nConnID, byte Market, String Zqdm, byte[] Result, byte[] ErrInfo);

	boolean TdxHq_GetHistoryMinuteTimeData(int nConnID, byte Market, String Zqdm, int date, byte[] Result,
                                           byte[] ErrInfo);

	boolean TdxHq_GetTransactionData(int nConnID, byte Market, String Zqdm, short Start, ShortByReference Count,
                                     byte[] Result, byte[] ErrInfo);

	boolean TdxHq_GetHistoryTransactionData(int nConnID, byte Market, String Zqdm, short Start, ShortByReference Count,
                                            int date, byte[] Result, byte[] ErrInfo);

	boolean TdxHq_GetSecurityQuotes(int nConnID, byte[] Market, String[] Zqdm, ShortByReference Count, byte[] Result,
                                    byte[] ErrInfo);

	boolean TdxHq_GetCompanyInfoCategory(int nConnID, byte Market, String Zqdm, byte[] Result, byte[] ErrInfo);

	boolean TdxHq_GetCompanyInfoContent(int nConnID, byte Market, String Zqdm, String FileName, int Start, int Length,
                                        byte[] Result, byte[] ErrInfo);

	boolean TdxHq_GetXDXRInfo(int nConnID, byte Market, String Zqdm, byte[] Result, byte[] ErrInfo);

	boolean TdxHq_GetFinanceInfo(int nConnID, byte Market, String Zqdm, byte[] Result, byte[] ErrInfo);

	// L2行情
	// 获取深圳逐笔委托某个范围内的数据
	boolean TdxL2Hq_GetSecurityQuotes10(int connId, byte[] Market, String[] Zqdm, ShortByReference Count, byte[] Result,
                                        byte[] ErrInfo);

	int TdxL2Hq_Connect(String IP, short Port, String pszL2User, String pszL2Password, byte[] Result, byte[] ErrInfo);

	// 断开服务器
	void TdxL2Hq_Disconnect(int connId);

	// 获取深圳逐笔委托某个范围内的数据
	boolean TdxL2Hq_GetDetailOrderData(int connId, byte Market, String Zqdm, int Start, ShortByReference Count,
                                       byte[] Result, byte[] ErrInfo);

	// 获取深圳逐笔委托某个范围内的数据
	boolean TdxL2Hq_GetTransactionData(int connId, byte Market, String Zqdm, short Start, ShortByReference Count,
                                       byte[] Result, byte[] ErrInfo);

	// 获取买卖队列数据
	boolean TdxL2Hq_GetDetailTransactionData(int connId, byte Market, String Zqdm, int Start, ShortByReference Count,
                                             byte[] Result, byte[] ErrInfo);

	// 获取逐笔成交某个范围内的数据
	boolean TdxL2Hq_GetBuySellQueue(int connId, byte Market, String Zqdm, byte[] Result, byte[] ErrInfo);

}
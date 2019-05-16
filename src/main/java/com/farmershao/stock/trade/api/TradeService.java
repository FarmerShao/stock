/**
 * Copyright (c) 2008 by sangame.com.
 * All right reserved.
 */
package com.farmershao.stock.trade.api;

import com.farmershao.stock.trade.constant.NameValue;
import com.farmershao.stock.trade.dto.ClientConnectionDto;
import com.farmershao.stock.trade.dto.OrderDto;
import com.farmershao.stock.trade.exception.TradeException;

import java.util.Date;

/**
 * @author Teng
 * @date 2018年6月8日
 */
public interface TradeService {



    /**
     * 查询交易数据
     *
     * @param dataCate 查询信息种类
     * @return 返回对应种类的交易数据集合json数组字符串
     * @throws IllegalStateException 没有登录交易账户时，抛出此异常
     * @throws TradeException        当返回“请重新登录交易服务器”时，直接调用登出和登录方法重新登录
     */
     String queryData(NameValue dataCate, String accountNo) throws  TradeException;

     String queryData(NameValue dataCate, ClientConnectionDto connectionDto) throws TradeException;

    /**
     * 委托下单接口
     *
     * @param order 委托单
     * @return 含有委托编号的查询数据
     * @throws TradeException 当返回“请重新登录交易服务器”时，直接调用登出和登录方法重新登录
     */
     OrderDto sendOrder(OrderDto order, String accountNo) throws TradeException;

    /**
     * 撤销委托接口
     *
     * @param order 带有委托编号的委托单
     * @return 表格形式的查询数据
     * @throws TradeException 当返回“请重新登录交易服务器”时，直接调用登出和登录方法重新登录
     */
     boolean cancelOrder(OrderDto order, String accountNo) throws TradeException ;

    /**
     * 获取证券的实时五档行情接口
     *
     * @param stockCode 证券代码
     * @return 实时五档行情json字符串
     * @throws TradeException 当返回“请重新登录交易服务器”时，直接调用登出和登录方法重新登录
     */
     String getQuote(String stockCode, String accountNo) throws TradeException;

    /**
     * 查询当前可交易股票数量
     *
     * @param order 委托信息
     * @return 可卖股票数量json数组字符串
     * @throws TradeException 当返回“请重新登录交易服务器”时，直接调用登出和登录方法重新登录
     */
     String getTrandableQuantity(OrderDto order, String accountNo) throws TradeException ;

    /**
     * 融资融券账户直接还款
     *
     * @param amount 还款金额
     * @return 表格形式查询数据
     * @throws TradeException 当返回“请重新登录交易服务器”时，直接调用登出和登录方法重新登录
     */
     String repay(int amount, String accountNo) throws TradeException, TradeException;

    /**
     * 查询历史交易数据
     *
     * @param dataType  查询信息的种类
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 查询数据json数组字符串
     * @throws TradeException 当返回“请重新登录交易服务器”时，直接调用登出和登录方法重新登录
     */
     String queryHistoryData(final NameValue dataType, Date startDate, Date endDate, String accountNo) throws TradeException ;


}

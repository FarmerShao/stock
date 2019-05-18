package com.farmershao.stock.trade.tradex;

import com.farmershao.stock.trade.enums.DataCate;
import com.farmershao.stock.trade.enums.OrderCate;
import com.farmershao.stock.trade.enums.PriceCate;
import com.farmershao.stock.trade.exception.TradeException;
import com.farmershao.stock.trade.model.suport.DataTable;
import com.farmershao.stock.trade.model.suport.DataTableError;
import com.farmershao.stock.trade.model.suport.Quote;
import com.google.common.base.Preconditions;
import com.sun.jna.Pointer;

import java.util.ArrayList;
import java.util.List;


/**
 * 处理多帐号同时提交交易操作
 * <p>
 * Created by kongkp on 2017/1/8.
 */
public class TdxClientFed {
    private TdxLibrary tdxLibrary;

    TdxClientFed(TdxLibrary tdxLibrary) {
        this.tdxLibrary = tdxLibrary;
    }

    /***********************************************************************
     * 多帐号同时查询数据
     ***********************************************************************/
    public static class QueryDataFed {
        private TdxLibrary tdxLibrary;
        List<TdxClient> clients = new ArrayList<>();
        List<DataCate> dataCates = new ArrayList<>();

        public QueryDataFed(TdxLibrary tdxLibrary) {
            this.tdxLibrary = tdxLibrary;
        }


        public QueryDataFed addQueryData(TdxClient client, DataCate dataCate) {
            Preconditions.checkArgument(client != null, "client参数不能为null");
            Preconditions.checkArgument(dataCate != null, "dataCate参数不能为null");
            clients.add(client);
            dataCates.add(dataCate);
            return this;
        }

        public List<DataTable> execute() {
            if (clients.size() == 0) {
                return new ArrayList<>();
            } else {
                int count = clients.size();
                int[] clientIds = getClientIds(clients);
                int[] dataCateIds = TdxClient.enumToIntArr(dataCates);

                Pointer[] rltData = TdxClient.makeMemorys(count, 1024 * 1024);
                Pointer[] errData = TdxClient.makeMemorys(count, 256);

                tdxLibrary.QueryMultiAccountsDatas(clientIds, dataCateIds, count, rltData, errData);
                List<DataTable> results = TdxClient.transfer(clients, rltData, errData, (int i, String d, String t) -> {
                    String dataType = dataCates.get(i).getName();
                    if ("error".equals(t)) {
                        return new DataTableError(d);
                    } else {
                        return new DataTable(dataType, d);
                    }
                });
                clients.clear();
                dataCates.clear();
                return results;
            }
        }
    }

    private ThreadLocal<QueryDataFed> queryDataHolder = new ThreadLocal<>();

    public QueryDataFed addQueryData(TdxClient client, DataCate dataCate) {
        QueryDataFed queryDataFed = queryDataHolder.get();
        if (queryDataFed == null) {
            queryDataFed = new QueryDataFed(tdxLibrary);
            queryDataHolder.set(queryDataFed);
        }
        queryDataFed.addQueryData(client, dataCate);
        return queryDataFed;
    }


    /***********************************************************************
     * 多帐号同时提交单子
     ***********************************************************************/
    private static class SendOrderFed {
        private TdxLibrary tdxLibrary;
        List<TdxClient> clients = new ArrayList<>();
        List<OrderCate> orderCates = new ArrayList<>();
        List<PriceCate> priceCates = new ArrayList<>();
        List<String> stockCodes = new ArrayList<>();
        List<Float> prices = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        public SendOrderFed(TdxLibrary tdxLibrary) {
            this.tdxLibrary = tdxLibrary;
        }

        public SendOrderFed addSendOrder(
                TdxClient client,
                OrderCate orderCate,
                PriceCate priceCate,
                String stockCode,
                float price,
                int quantity) {
            Preconditions.checkArgument(client != null, "client参数不能为null");
            Preconditions.checkArgument(orderCate != null, "orderCate参数不能为null");
            Preconditions.checkArgument(priceCate != null, "priceCate参数不能为null");
            Preconditions.checkArgument(stockCode != null, "stockCode参数不能为null");

            clients.add(client);
            orderCates.add(orderCate);
            priceCates.add(priceCate);
            stockCodes.add(stockCode);
            prices.add(price);
            quantities.add(quantity);
            return this;
        }

        public List<String> execute() throws TradeException {
            if (clients.size() == 0) {
                return new ArrayList<>();
            } else {
                int count = clients.size();
                int[] clientIds = getClientIds(clients);
                int[] orderCateIds = TdxClient.enumToIntArr(orderCates);
                int[] priceCateIds = TdxClient.enumToIntArr(priceCates);
                String[] stocks = this.stockCodes.toArray(new String[count]);
                String[] gddms = getGddms(clients, stockCodes);
                float[] priceArr = floatListToArray(prices);
                int[] quantityArr = integerListToArray(quantities);

                Pointer[] rltData = TdxClient.makeMemorys(count, 1024 * 1024);
                Pointer[] errData = TdxClient.makeMemorys(count, 256);

                tdxLibrary.SendMultiAccountsOrders(clientIds, orderCateIds, priceCateIds, gddms, stocks, priceArr, quantityArr, count, rltData, errData);
                List<String> results = TdxClient.transfer(clients, rltData, errData, (int i, String d, String t) -> {
                    if ("error".equals(t)) {
                        return d;
                    } else {
                        return d;
                    }
                });
                clients.clear();
                orderCates.clear();
                priceCates.clear();
                stockCodes.clear();
                prices.clear();
                quantities.clear();
                return results;
            }
        }
    }

    private ThreadLocal<SendOrderFed> sendOrderHolder = new ThreadLocal<>();

    public SendOrderFed addQueryData(
            TdxClient client,
            OrderCate orderCate,
            PriceCate priceCate,
            String stockCode,
            float price,
            int quantity) {
        SendOrderFed sendOrderFed = sendOrderHolder.get();
        if (sendOrderFed == null) {
            sendOrderFed = new SendOrderFed(tdxLibrary);
            sendOrderHolder.set(sendOrderFed);
        }
        sendOrderFed.addSendOrder(client, orderCate, priceCate, stockCode, price, quantity);
        return sendOrderFed;
    }

    /***********************************************************************
     * 多帐号同时取消订单
     ***********************************************************************/
    private static class CancelOrderFed {
        private TdxLibrary tdxLibrary;
        List<TdxClient> clients = new ArrayList<>();
        List<String> exchangeIds = new ArrayList<>();
        List<String> orderNos = new ArrayList<>();

        public CancelOrderFed(TdxLibrary tdxLibrary) {
            this.tdxLibrary = tdxLibrary;
        }


        public CancelOrderFed addCancelOrder(TdxClient client, String exchangeId, String orderNo) {
            clients.add(client);
            exchangeIds.add(exchangeId);
            orderNos.add(orderNo);
            return this;
        }

        public List<String> execute() {
            if (clients.size() == 0) {
                return new ArrayList<>();
            } else {
                int count = clients.size();
                int[] clientIds = getClientIds(clients);
                String[] exchangeIds = this.exchangeIds.toArray(new String[]{});
                String[] orderNos = this.orderNos.toArray(new String[]{});

                Pointer[] rltData = TdxClient.makeMemorys(count, 1024 * 1024);
                Pointer[] errData = TdxClient.makeMemorys(count, 256);

                tdxLibrary.CancelMultiAccountsOrders(clientIds, exchangeIds, orderNos, count, rltData, errData);
                List<String> results = TdxClient.transfer(clients, rltData, errData, (int i, String d, String t) -> {
                    if ("error".equals(t)) {
                        return d;
                    } else {
                        return d;
                    }
                });
                clients.clear();
                this.exchangeIds.clear();
                this.orderNos.clear();
                return results;
            }
        }
    }

    private ThreadLocal<CancelOrderFed> cancelOrderFedHolder = new ThreadLocal<>();

    public CancelOrderFed addCancelOrder(TdxClient client, String exchangeId, String orderNo) {
        CancelOrderFed cancelOrderFed = cancelOrderFedHolder.get();
        if (cancelOrderFed == null) {
            cancelOrderFed = new CancelOrderFed(tdxLibrary);
            cancelOrderFedHolder.set(cancelOrderFed);
        }
        cancelOrderFed.addCancelOrder(client, exchangeId, orderNo);
        return cancelOrderFed;
    }


    /***********************************************************************
     * 多帐号同时查询盘口
     ***********************************************************************/
    private static class GetQuoteFed {
        private TdxLibrary tdxLibrary;
        List<TdxClient> clients = new ArrayList<>();
        List<String> stockCodes = new ArrayList<>();

        public GetQuoteFed(TdxLibrary tdxLibrary) {
            this.tdxLibrary = tdxLibrary;
        }


        public GetQuoteFed addGetQuote(TdxClient client, String stockNo) {
            clients.add(client);
            stockCodes.add(stockNo);
            return this;
        }

        public List<Quote> execute() {
            if (clients.size() == 0) {
                return new ArrayList<>();
            } else {
                int count = clients.size();
                int[] clientIds = getClientIds(clients);
                String[] stocks = this.stockCodes.toArray(new String[]{});

                Pointer[] rltData = TdxClient.makeMemorys(count, 1024 * 1024);
                Pointer[] errData = TdxClient.makeMemorys(count, 256);

                tdxLibrary.GetMultiAccountsQuotes(clientIds, stocks, count, rltData, errData);
                List<Quote> results = TdxClient.transfer(clients, rltData, errData, (int i, String d, String t) -> TdxClient.getQuote(stocks[i], d, t));
                clients.clear();
                stockCodes.clear();
                return results;
            }
        }
    }

    private ThreadLocal<GetQuoteFed> getQuoteFedHolder = new ThreadLocal<>();

    public GetQuoteFed addGetQuote(TdxClient client, String stockNo) {
        GetQuoteFed getQuoteFed = getQuoteFedHolder.get();
        if (getQuoteFed == null) {
            getQuoteFed = new GetQuoteFed(tdxLibrary);
            getQuoteFedHolder.set(getQuoteFed);
        }
        getQuoteFed.addGetQuote(client, stockNo);
        return getQuoteFed;
    }


    /***********************************************************************
     * Share utility functions
     ***********************************************************************/
    private static int[] getClientIds(List<TdxClient> clients) {
        int[] clientIds = new int[clients.size()];
        for (int i = 0, len = clients.size(); i < len; i++) {
            clientIds[i] = clients.get(i).getClientId();
        }
        return clientIds;
    }

    private static String[] getGddms(List<TdxClient> clients, List<String> stockCodes) throws TradeException {
        String[] gddms = new String[clients.size()];
        for (int i = 0, len = clients.size(); i < len; i++) {
            TdxClient client = clients.get(i);
            gddms[i] = client.getGddm(stockCodes.get(i));
        }
        return gddms;
    }

    private static int[] integerListToArray(List<Integer> integerList) {
        int[] intArr = new int[integerList.size()];
        for (int i = 0, len = integerList.size(); i < len; i++) {
            intArr[i] = integerList.get(i);
        }
        return intArr;
    }

    private static float[] floatListToArray(List<Float> floatList) {
        float[] floatArr = new float[floatList.size()];
        for (int i = 0, len = floatList.size(); i < len; i++) {
            floatArr[i] = floatList.get(i);
        }
        return floatArr;
    }


}

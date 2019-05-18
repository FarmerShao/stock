package com.farmershao.stock.trade.model.suport;

/**
 * 查询Quote出错信息返回对象
 *
 * Created by kongkp on 2017/1/8.
 */
public class QuoteError extends Quote implements Error {
    private static final long serialVersionUID = 1L;

    private String errorMessage;

    public QuoteError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorMsg() {
        return errorMessage;
    }
}

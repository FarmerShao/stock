package com.farmershao.stock.trade.model.suport;

/**
 * 代表DataTable数据出错对象
 * <p>
 * Created by kongkp on 2017/1/8.
 */
public class DataTableError extends DataTable implements Error {
    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public DataTableError(String name, String errorMessage) {
        super(name, new String[]{"错误消息"});
        this.addRow(new String[]{errorMessage});
        this.errorMessage = errorMessage;
    }


    public DataTableError(String errorMessage) {
        super("dataTableError", new String[]{"错误消息"});
        this.addRow(new String[]{errorMessage});
        this.errorMessage = errorMessage;
    }

    public DataTableError(String errorMessage, String[] extInfoColumns) {
        super("dataTableError", extInfoColumns);
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorMsg() {
        return errorMessage;
    }
}

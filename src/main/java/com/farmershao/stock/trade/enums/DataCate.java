package com.farmershao.stock.trade.enums;

public enum DataCate implements IdAndName {
    /**
     * 资金
     */
    ZJ("资金", 0),
    /**
     * 股份
     */
    GF("股份", 1),
    /**
     * 当日委托
     */
    DRWT("当日委托", 2),
    /**
     * 当日成交
     */
    DRCJ("当日成交", 3),
    /**
     * 可撤单
     */
    KCD("可撤单", 4),
    /**
     * 股东代码
     */
    GDDM("股东代码", 5),
    /**
     * 融资余额
     */
    RZYE("融资余额", 6),
    /**
     * 融券余额
     */
    RQYE("融券余额", 7),
    /**
     * 可融证券
     */
    KRZQ("可融证券", 8);

    private String name;
    private int id;

    private DataCate(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return id + name;
    }
}

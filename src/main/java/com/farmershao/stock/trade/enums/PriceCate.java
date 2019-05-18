package com.farmershao.stock.trade.enums;

public enum PriceCate implements IdAndName {
    /**
     * 上海限价委托 深圳限价委托
     */
    XJWT("XJWT", 0),
    /**
     * (市价委托)深圳对方最优价格
     */
    SZ_DFZY("SZ_DFZY", 1),
    /**
     * (市价委托)深圳本方最优价格
     */
    SZ_BFZY("SZ_BFZY", 2),
    /**
     * (市价委托)深圳即时成交剩余撤销
     */
    SZ_JCSC("SZ_JCSC", 3),
    /**
     * (市价委托)上海五档即成剩撤 深圳五档即成剩撤
     */
    WDJCSC("WDJCSC", 4),
    /**
     * (市价委托)深圳全额成交或撤销
     */
    SZ_QECJHCX("SZ_QECJHCX", 5),
    /**
     * (市价委托)上海五档即成转限价
     */
    SH_WDJCZXJ("SH_WDJCZXJ", 6);


    private String name;
    private int id;

    private PriceCate(String name, int id) {
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
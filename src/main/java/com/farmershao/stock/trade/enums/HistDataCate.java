package com.farmershao.stock.trade.enums;

public enum HistDataCate implements IdAndName {
    /**
     * 历史委托
     */
    LSWT("历史委托", 0),
    /**
     * 历史成交
     */
    LSCJ("历史成交", 1),
    /**
     * 交割单
     */
    JGD("交割单", 2);

    private String name;
    private int id;

    private HistDataCate(String name, int id) {
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
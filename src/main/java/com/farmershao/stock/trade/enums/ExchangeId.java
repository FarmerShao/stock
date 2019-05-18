package com.farmershao.stock.trade.enums;

/**
 * 交易所ID：上海1，深圳0(招商证券普通账户深圳是2)
 */
public enum ExchangeId implements IdAndName {
    SH("上交所", 1),
    SZ("深交所", 0),
    SZ_ZSZQ("深交所(招商证券)", 2);

    private String name;
    private int id;

    private ExchangeId(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public byte getByteId() {
        return (byte) id;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return id + name;
    }
}

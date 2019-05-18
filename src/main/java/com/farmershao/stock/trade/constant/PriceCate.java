package com.farmershao.stock.trade.constant;

/**
 * 报价方式
 *
 * @author Shao Yu
 * @since 2018/4/25 9:45
 **/
public enum PriceCate implements NameValue<String,Integer> {

    /**
     * (XJWT)上海限价委托 深圳限价委托
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

    PriceCate(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return id;
    }

    public Object getObject() {
        return PriceCate.class;
    }
}

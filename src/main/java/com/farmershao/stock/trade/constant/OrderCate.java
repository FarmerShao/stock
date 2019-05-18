package com.farmershao.stock.trade.constant;

/**
 * 委托种类
 *
 * @author Shao Yu
 * @since 2018/4/25 9:41
 **/
public enum OrderCate implements NameValue<String,Integer> {

    MR("买入", 0),
    MC("卖出", 1),
    RZMR("融资买入", 2),
    RQMC("融券卖出", 3),
    MQHQ("买券还券", 4),
    MQHK("卖券还款", 5),
    XQHQ("现券还券", 6),
    DBPMR("担保品买入", 7),
    DBPMC("担保品卖出", 8);

    private String name;
    private int id;

    OrderCate(String name, int id) {
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
        return OrderCate.class;
    }
}

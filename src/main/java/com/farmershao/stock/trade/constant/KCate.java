package com.farmershao.stock.trade.constant;

/**
 * K线种类
 *
 * @author Shao Yu
 * @since 2018/4/25 9:40
 **/
public enum KCate implements NameValue<String,Integer> {

    MIN5("5分钟", 0),
    MIN15("15分钟", 1),
    MIN30("30分钟", 2),
    HOUR("1小时", 3),
    DAY("日线", 4),
    WEEK("周", 5),
    MON("月", 6),
    MIN("分钟", 7),
    MIN_1("分钟", 8),
    DAY_1("日线1", 9),
    QUARTER("季线", 10),
    YEAR("年线", 11);

    private String name;
    private int id;

    KCate(String name, int id) {
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
        return KCate.class;
    }

    public byte getByteId(){return  (byte)id;}
}

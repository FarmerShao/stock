package com.farmershao.stock.trade.constant;

/**
 * 交易所(（市场）)ID：上海1，深圳0(招商证券普通账户深圳是2)
 *
 * @author Shao Yu
 * @since 2018/4/25 9:37
 **/
public enum ExchangeId implements NameValue<String, Integer> {

    SH("上交所", 1){
        @Override
        public Object getObject() {
            return null;
        }
    },
    SZ("深交所", 0){
        @Override
        public Object getObject() {
            return null;
        }
    },
    SZ_ZSZQ("深交所(招商证券)", 2){
        @Override
        public Object getObject() {
            return null;
        }
    };

    private String name;
    private int id;

    ExchangeId(String name, int id) {
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

    public  byte getByteId(){
        return (byte)id;
    }
}

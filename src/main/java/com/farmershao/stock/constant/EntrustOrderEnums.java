package com.farmershao.stock.constant;

import lombok.Getter;

/**
 * 委托单相关的枚举
 *
 * @author ShaoYu
 * @since 2019/5/19 0019 上午 10:55
 */
public class EntrustOrderEnums {


    @Getter
    public enum BuyType{

        /**
         * 限价单
         */
        LIMIT_PRICE(1, "限价单"),

        /**
         * 市价单
         */
        MARKET_PRICE(2, "市价单");

        private Integer type;
        private String desc;

        BuyType(Integer type, String desc) {
            this.type = type;
            this.desc = desc;
        }

    }

    @Getter
    public enum Direction{

        /**
         * 买
         */
        BUY(1, "买"),
        /**
         * 卖
         */
        SELL(2, "卖");

        private Integer direction;
        private String desc;

        Direction(Integer direction, String desc) {
            this.direction = direction;
            this.desc = desc;
        }
    }

    @Getter
    public enum Status{

        /**
         * 未成交
         */
        NOT_DEAL(1, "未成交"),
        /**
         * 已成交
         */
        DEAL(2, "已成交"),
        /**
         * 部分成交
         */
        PART_DEAL(3, "部分成交"),
        /**
         * 用户撤销
         */
        CANCEL(4, "用户撤销"),
        /**
         * 待提交
         */
        UN_COMMIT(5, "待提交"),
        ;

        private Integer status;
        private String desc;

        Status(Integer status, String desc) {
            this.status = status;
            this.desc = desc;
        }
    }

    @Getter
    public enum Faker{
        YES(1, "是"),
        NO(2, "否");

        private Integer value;
        private String desc;

        Faker(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }
}

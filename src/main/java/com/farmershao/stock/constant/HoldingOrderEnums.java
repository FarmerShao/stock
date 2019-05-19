package com.farmershao.stock.constant;

import lombok.Getter;

/**
 * HoldingOrderEnums: 持仓但相关枚举
 *
 * @author Farmer Shao
 * @since 2019-05-19 19:28
 */
public class HoldingOrderEnums {

    @Getter
    public enum Status{

        /**
         * 持仓中
         */
        HOLDING(1, "持仓中"),
        /**
         * 已平仓
         */
        SELL(2, "已平仓"),
        /**
         * 部分成交
         */
        SELLING(3, "平仓中"),
        ;

        private Integer status;
        private String desc;

        Status(Integer status, String desc) {
            this.status = status;
            this.desc = desc;
        }
    }

    @Getter
    public enum Unbind{
        YES(1, "是"),
        NO(2, "否");

        private Integer value;
        private String desc;

        Unbind(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

    @Getter
    public enum Believe{
        YES(1, "是"),
        NO(2, "否");

        private Integer value;
        private String desc;

        Believe(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

}

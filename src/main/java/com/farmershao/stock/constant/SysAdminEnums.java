package com.farmershao.stock.constant;

/**
 * ActivityEnums
 *
 * @author Shao Yu
 * @since 2019/4/10 11:14
 **/
public class SysAdminEnums {

    public enum DeleteFlagType {

        DEL(new Byte("1"), "删除"),
        COMMON(new Byte("2"), "未删除"),
        ;

        DeleteFlagType(Byte value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        private Byte value;
        private String desc;

        public Byte getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        public static DeleteFlagType getEnum(Byte value) {
            DeleteFlagType[] values = values();
            for (DeleteFlagType type : values) {
                if (type.getValue().equals(value)) {
                    return type;
                }
            }
            return null;
        }
    }

}

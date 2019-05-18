package com.farmershao.stock.constant;

/**
 * SmsEnum
 *
 * @author zhaoda
 * @Description 短信相关枚举
 * @since 2019/5/18 16:54
 **/
public class SmsEnum {

    public enum DLZXSmsType {
        NORMAL_MESSAGE("10"),   //发送普通短信70字
        LONG_MESSAGE("12"),    //发送长短信450字
        PACK_MESSAGE("13"),    //发送打包短信不同内容
        RECEIVE_MESSAGE("20"),    //接收回复短信
        SEARCH_ACCOUNT_BALANCE("30"),    //查询账户余额
        UPDATE_PASSWORD("50"),    //修改接口密码
        GET_NET_IP("100");    // 获取外网IP，用于生成x_pwd_md5


        private String value;

        DLZXSmsType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 短信消息类型枚举
     *
     * @author Shao Yu
     * @since 2018/5/31 9:40
     **/
    public enum SmsMessageTypeEnum {
        REGISTRY("注册", 1){
            @Override
            String getContent(String authCode, String appName) {
                return String.format("验证码：%s，欢迎加入%s，感谢您的支持", authCode, appName);
            }
        },
        FORGET_PASSWORD("忘记密码", 2){
            @Override
            String getContent(String authCode, String appName) {
                return String.format("您的验证码为：%s。如非本人操作，请忽略本短信，祝您投资顺利", authCode);
            }
        },
        WITHDRAW("提现操作", 3){
            @Override
            String getContent(String authCode, String appName) {
                return String.format("你正在绑定银行卡，验证码是：%s。", authCode);
            }
        };

        private String name;
        private Integer value;

        SmsMessageTypeEnum(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        abstract String getContent(String authCode, String appName);

        public static String getContentByValue(Integer value, String authCode, String appName) {
            SmsMessageTypeEnum[] typeEnums = SmsMessageTypeEnum.values();
            for (SmsMessageTypeEnum typeEnum : typeEnums) {
                if (typeEnum.value.equals(value))
                    return typeEnum.getContent(authCode, appName);
            }
            //默认返回注册
            return REGISTRY.getContent(authCode, appName);
        }

        public static String getNameByValue(Integer value) {
            SmsMessageTypeEnum[] typeEnums = SmsMessageTypeEnum.values();
            for (SmsMessageTypeEnum typeEnum : typeEnums) {
                if (typeEnum.value.equals(value)) {
                    return typeEnum.name;
                }
            }
            //默认返回注册
            return REGISTRY.getName();
        }

        public String getName() {
            return this.name;
        }

        public Integer getValue() {
            return this.value;
        }
    }

    public enum ResultType {
        SUCCESS(1, "成功"),
        FAIL(2, "失败");

        private Integer value;
        private String name;

        ResultType(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

    }

    public enum AppName {
        STOCK("stock", "软件"),
        ;

        private String value;
        private String name;

        AppName(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public static String getNameByValue(String value) {
            for (AppName temp: AppName.values()) {
                if(temp.getValue().equals(value)){
                    return temp.getName();
                }
            }
            return "默认软件";
        }
    }

}

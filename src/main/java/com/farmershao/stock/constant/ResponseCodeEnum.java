package com.farmershao.stock.constant;

/**
 * ResponseCodeEnum
 *
 * @author : Shao Yu
 * @date 2019/5/5 19:19
 * @since : 1.0.0
 **/
public enum ResponseCodeEnum {

    /**
     * 处理成功
     */
    SUCCESS(0, "success"),
    /**
     * 请求处理失败
     */
    FAILURE(1, "failure"),

    /**
     * 策略匹配杠杆倍数已达最大保存数量
     */
    MAX_MARGIN_RATE_NUMBER(100, "策略匹配杠杆倍数已达最大保存数量"),

    ;

    private int code;
    private String msg;

    ResponseCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ResponseCodeEnum getByCode(int code) {
        ResponseCodeEnum[] values = values();
        for (ResponseCodeEnum codeEnum : values) {
            if (codeEnum.getCode() == code) {
                return codeEnum;
            }
        }
        return FAILURE;
    }
}

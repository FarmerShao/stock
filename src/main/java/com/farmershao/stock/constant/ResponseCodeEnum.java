package com.farmershao.stock.constant;

/**
 * ResponseCodeEnum
 *
 * @author Shao Yu
 * @since 2019/3/11 10:31
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
package com.farmershao.stock.trade.dto;

/**
 * 请求返回对象
 *
 * @author Shao Yu
 * @since 2018/4/24 14:51
 **/
public class ResponseJsonDto {

    /** 返回编码 */
    private Integer code;
    /** 返回消息 */
    private String msg;
    /** 返回数据 */
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

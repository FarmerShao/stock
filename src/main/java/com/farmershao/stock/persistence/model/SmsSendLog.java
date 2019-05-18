package com.farmershao.stock.persistence.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 短信发送日志
 *
 * @author Shao Yu
 * @since 2018/5/7 14:19
 **/
@Setter
@Getter
public class SmsSendLog{

    //手机号
    private String mobile;
    //发送结果 1.成功 2.失败
    private Integer resultType;
    //短信内容
    private String message;
    //返回值
    private String resultCode;
    //短信渠道
    private String channel;

    private String messageType;

}

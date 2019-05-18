package com.farmershao.stock.constant;

/**
 * CacheKeyEnum
 *
 * @author : Shao Yu
 * @date 2019/5/5 19:19
 * @since : 1.0.0
 **/
public enum  CacheKeyEnum {

    /**
     * 股票账户资金
     */
    ACCOUNT_MONEY("account.money", 12 * 60 * 60L),

    //======= BackPermission ======
    /**
     *
     */
    BACK_PERMISSION_ARRAY("back.permission.array.", 30 * 60L),
    
    //============= SMS =============
    /**短信验证码：登录/注册*/
    LOGIN_SMS_CODE("login.sms.code.", 30 * 60L),
    /**短信验证码：绑定银行卡*/
    ADD_BANK_SMS_CODE("add.bank.code.", 30 * 60L),
    /**短信登录图片验证码*/
    SMS_IMGCODE_LOGIN("sms.imgcode.login", 30 * 60L),
    /**短信验证码60S防刷*/
    SMS_SIXTY_SECONDS("sms.sixty.seconds.", 30 * 60L),
    /**短信发送通道验证码 注册*/
    SIGN_NAME_LOGIN_SMS_CODE("sign.name.login.sms.code.", 30 * 60L),
    /**短信发送通道验证码 注册数量*/
    SIGN_NAME_LOGIN_SMS_NUM("sign.name.login.sms.num.", 30 * 60L),
    /**10分钟内已发短信数*/
    TEN_MINUTE_COUNT("ten.minute.count.", 10 * 60L),
    /**一天已发短信数*/
    ONE_DAY_COUNT("one.day.count.", 24 * 60 * 60L),
    ;



    /**
     * 缓存的key
     */
    private String key;
    /**
     * 过期时间，单位 秒
     */
    private Long expire;

    CacheKeyEnum(String key, Long expire) {
        this.key = key;
        this.expire = expire;
    }

    public String getKey() {
        return key;
    }

    public Long getExpire() {
        return expire;
    }
}

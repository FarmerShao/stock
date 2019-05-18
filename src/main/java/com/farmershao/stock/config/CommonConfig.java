package com.farmershao.stock.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * CommonConfig
 *
 * @author : Shao Yu
 * @date 2019/5/5 19:19
 * @since : 1.0.0
 **/
@Configuration
@Getter
@Setter
public class CommonConfig {

    /**
     * JWT 秘钥
     */
    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * JWT 秘钥
     */
    @Value("${jwt.expire.interval}")
    private Long jwtExpireInterval;

    /**
     * JWT 跨域白名单
     */
    @Value("${jwt.white.ip}")
    private String jwtWhiteIp;
    /**
     * 10min最多多少条
     */
    @Value("${sms.ten.minutes.max}")
    private Long smsTenMinutesMax;
    /**
     * 一天最多多少条
     */
    @Value("${sms.one.day.max}")
    private Long smsOneDayMax;
    /**
     * 东联企业id
     */
    @Value("${dlzx.xeid}")
    private String eid;
    /**
     * 东联会员id
     */
    @Value("${dlzx.xuid}")
    private String uid;
    /**
     * 东联中讯密码
     */
    @Value("${dlzx.xpwd}")
    private String pwd;
    /**
     * app名称
     */
    @Value("${app.name}")
    private String appName;
}

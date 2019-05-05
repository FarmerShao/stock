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
    @Value("${jwt.origin.ip}")
    private String jwtOriginIp;
}

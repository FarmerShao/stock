package com.farmershao.stock.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.farmershao.stock.config.CommonConfig;
import com.farmershao.stock.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON Web Token
 *
 * @author : Zhao Da
 * @since : 2019/4/28 10:29
 */
@Component
@Slf4j
public class JwtUtil {

    @Autowired
    private CommonConfig commonConfig;

    public String createTokenWithClaim(String userName, String role, String roleName) {
        try {
            // HMAC SHA256  : HS256
            Algorithm algorithm = Algorithm.HMAC256(commonConfig.getJwtSecret());
            Map<String, Object> map = new HashMap<>(2 >> 2);
            Date nowDate = new Date();
            // 过期
            Date expireDate = new Date(System.currentTimeMillis() + commonConfig.getJwtExpireInterval());
            map.put("alg", algorithm.getName());
            map.put("typ", "JWT");

            return JWT.create()
                    // 设置头部信息 Header
                    .withHeader(map)
                    // 设置 自定义
                    .withClaim("very", AesEncryptUtil.getEncrypt(userName, Constant.AES_KEY))
                    .withClaim("role", role)
                    .withClaim("roleName", roleName)
                    // iss-签发人：签名是有谁生成 例如 服务器
                    .withIssuer("SERVICE")
                    // sub (subject)：主题： 签名的主题
                    .withSubject("system token")
                    // nbf (Not Before)：生效时间： 定义在什么时间之前，该jwt都是不可用的.
                    //.withNotBefore(new Date())
                    // aud (audience)：受众： 签名的观众 也可以理解谁接受签名的
                    .withAudience("web")
                    // iat (Issued At)：签发时间： 生成签名的时间
                    .withIssuedAt(nowDate)
                    // exp (expiration time)：过期时间： 签名过期的时间
                    .withExpiresAt(expireDate)
                    // 签名 Signature
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            log.error("create token error", exception);
        }
        return "";
    }

    public Map<String, Claim> parseToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(commonConfig.getJwtSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("SERVICE")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaims();
        } catch (JWTVerificationException exception){
            throw new JWTVerificationException("parse token error");
        }
    }
}

package com.example.petstorebackend.AccountLogAndRegister.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * token过期时间24h
     */
    private static final long EXPIRE_TIME = 60* 60 * 1000 * 24;
    /**
     * token秘钥
     */
    private static final String SECRET = "csu_stu_web_software";

    public static String getToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }

    public static String getUsername(String token)   {
        Claim claim = JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token)
                .getClaim("claims");
        return claim.asMap().get("username").toString();
    }
}

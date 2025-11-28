package com.kuangkuang.kuangkuang.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    public static String signKey = "kuangkuang";
    public static String empId = "zty";
    private static long expire = 43200000L;
    public static String createJwt(Map<String,Object> claims){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS384;
        long expMills = System.currentTimeMillis()+expire;
        Date exp = new Date(expMills);

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .signWith(signatureAlgorithm,signKey.getBytes(StandardCharsets.UTF_8))
                .setExpiration(exp);
        return builder.compact();
    }
    public static Claims parseJwt(String secretKey,String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token).getBody();
        return  claims;
    }
}

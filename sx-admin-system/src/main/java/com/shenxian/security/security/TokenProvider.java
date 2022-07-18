package com.shenxian.security.security;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.shenxian.security.config.bean.SecurityProperties;
import com.shenxian.utils.RedisUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author: shenxian
 * @date: 2022/7/11 9:35
 */
@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    public static final String AUTHORITIES_KEY = "user";
    private final SecurityProperties properties;
    private final RedisUtils redisUtils;
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;

    public TokenProvider(SecurityProperties properties, RedisUtils redisUtils) {
        this.properties = properties;
        this.redisUtils = redisUtils;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        jwtBuilder = Jwts.builder().signWith(key, SignatureAlgorithm.HS512);
    }

    /**
     * 创建 token 永不过期，token 有效性转移到 redis 维护
     * @param authentication 鉴权信息
     * @return /
     */
    public String createToken(Authentication authentication) {
        return jwtBuilder
                .setId(IdUtil.simpleUUID())
                .claim(AUTHORITIES_KEY, authentication.getName())
                .setSubject(authentication.getName())
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = this.getClaims(token);
        User principal = new User(claims.getSubject(), "******", new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(principal, token, new ArrayList<>());
    }

    public Claims getClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 检查 token 续期
     * @param token /
     */
    public void checkRenewal(String token) {
        // 计算token的过期时间
        long time = redisUtils.getExpire(properties.getOnlineKey() + token) * 1000;
        DateTime expireDate = DateUtil.offset(new Date(), DateField.MILLISECOND, (int) time);
        // 计算当前时间与过期时间的时间差
        long differ = expireDate.getTime() - System.currentTimeMillis();
        // 如果时间差在 token 续期检查的时间范围内，token 续期
        if (differ <= properties.getDetect()) {
            long renew = time + properties.getRenew();
            redisUtils.expire(properties.getOnlineKey() + token, renew, TimeUnit.MILLISECONDS);
        }

    }

    public String getToken(HttpServletRequest request) {
        final String header = request.getHeader(properties.getHeader());
        if (header != null && header.startsWith(properties.getTokenStartWith())) {
            return header.substring(7);
        }
        return null;
    }
}

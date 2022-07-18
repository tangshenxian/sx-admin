package com.shenxian.security.config.bean;

import lombok.Data;

/**
 * @author: shenxian
 * @date: 2022/7/11 9:38
 */
@Data
public class SecurityProperties {

    /**
     * RequestHeaders: Authorization
     */
    private String header;
    /**
     * 令牌前缀：Bearer
     */
    private String tokenStartWith;
    /**
     * Base64 对令牌进行编码
     */
    private String base64Secret;
    /**
     * 令牌过期时间 单位：毫秒
     */
    private Long tokenValidityInSeconds;
    /**
     * 在线用户 key，根据 key 查询 redis 中在线用户数据
     */
    private String onlineKey;
    /**
     * 验证码 key
     */
    private String codeKey;
    /**
     * token 续期检查时间范围 单位：毫秒
     */
    private Long detect;
    /**
     * token 续期时间 单位：毫秒
     */
    private Long renew;

    public String getTokenStartWith() {
        return tokenStartWith + " ";
    }
}

package com.shenxian.security.service;

import cn.hutool.core.util.IdUtil;
import com.shenxian.config.RsaProperties;
import com.shenxian.exception.BadRequestException;
import com.shenxian.security.config.bean.CaptchaProperties;
import com.shenxian.security.config.bean.CaptchaTypeEnum;
import com.shenxian.security.config.bean.SecurityProperties;
import com.shenxian.security.security.TokenProvider;
import com.shenxian.security.service.dto.AuthUserDto;
import com.shenxian.security.service.dto.JwtUserDto;
import com.shenxian.utils.RedisUtils;
import com.shenxian.utils.Result;
import com.shenxian.utils.RsaUtils;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: shenxian
 * @date: 2022/7/11 14:14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final RedisUtils redisUtils;
    private final TokenProvider tokenProvider;
    private final OnlineUserService onlineUserService;
    private final CaptchaProperties captchaProperties;
    private final SecurityProperties securityProperties;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 登录授权 返回用户信息与token
     *
     * @param authUser /
     * @param request  /
     * @return /
     */
    public Object login(AuthUserDto authUser, HttpServletRequest request) {
        // 私钥解密
        String password;
        try {
            password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
        } catch (Exception e) {
            return Result.error("密码解密异常");
        }
        // 验证码
        String code = (String) redisUtils.get(authUser.getUuid());
        // 清除验证码
        redisUtils.del(authUser.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(authUser.getCode()) || !StringUtils.equalsIgnoreCase(authUser.getCode(), code)) {
            throw new BadRequestException("验证码错误");
        }
        // security 认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成 token
        String token = tokenProvider.createToken(authentication);
        final JwtUserDto principal = (JwtUserDto) authentication.getPrincipal();
        // 保存在线用户信息
        onlineUserService.save(principal, token, request);
        // 返回 token 与登录信息
        Map<String, Object> authInfo = new HashMap<>(8);
        authInfo.put("token", securityProperties.getTokenStartWith() + token);
        authInfo.put("user", principal);

        return authInfo;
    }

    /**
     * 获取验证码
     *
     * @return /
     */
    public Map<String, Object> code() {
        // 获取运算结果
        Captcha captcha = captchaProperties.getCaptcha();
        String uuid = securityProperties.getCodeKey() + IdUtil.simpleUUID();
        // 当验证码的类型为 arithmetic 时且长度 ==2 时，captcha.text() 的结果有几率为浮点型
        String code = captcha.text();
        if (captcha.getCharType() - 1 == CaptchaTypeEnum.ARITHMETIC.ordinal() && code.contains(".")) {
            code = code.split("\\.")[0];
        }
        // 保存验证码
        redisUtils.set(uuid, code, captchaProperties.getExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> result = new HashMap<>(8);
        result.put("uuid", uuid);
        result.put("code", captcha.toBase64());
        return result;
    }

    /**
     * 退出登录
     *
     * @param request /
     */
    public void logout(HttpServletRequest request) {
        String token = tokenProvider.getToken(request);
        onlineUserService.delete(token);
    }

}

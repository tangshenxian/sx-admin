package com.shenxian.security.security;

import cn.hutool.core.util.StrUtil;
import com.shenxian.security.config.bean.SecurityProperties;
import com.shenxian.security.service.OnlineUserService;
import com.shenxian.security.service.dto.OnlineUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: shenxian
 * @date: 2022/7/14 15:10
 */
@Slf4j
@RequiredArgsConstructor
public class TokenFilter extends GenericFilter {

    private final TokenProvider tokenProvider;
    private final SecurityProperties properties;
    private final OnlineUserService onlineUserService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String authorization = request.getHeader(properties.getHeader());
        if (StrUtil.isNotBlank(authorization)) {
            String token = this.resolveToken(authorization);
            if (StrUtil.isNotBlank(token)) {
                OnlineUserDto user = onlineUserService.getOne(properties.getOnlineKey() + token);
                if (user != null) {
                    Authentication authentication = tokenProvider.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    // token 续期
                    tokenProvider.checkRenewal(token);
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public String resolveToken(String authorization) {
        if (StringUtils.hasText(authorization) && authorization.startsWith(properties.getTokenStartWith())) {
            return authorization.replace(properties.getTokenStartWith(), "");
        }
        log.error("非法token：{}", authorization);
        return null;
    }
}

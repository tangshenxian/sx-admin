package com.shenxian.security.service;

import com.shenxian.security.config.bean.SecurityProperties;
import com.shenxian.security.service.dto.JwtUserDto;
import com.shenxian.security.service.dto.OnlineUserDto;
import com.shenxian.utils.EncryptUtils;
import com.shenxian.utils.RedisUtils;
import com.shenxian.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author: shenxian
 * @date: 2022/7/11 15:38
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OnlineUserService {

    private final RedisUtils redisUtils;
    private final SecurityProperties properties;

    /**
     * 保存在线用户信息
     *
     * @param jwtUserDto /
     * @param token      /
     * @param request    /
     */
    public void save(JwtUserDto jwtUserDto, String token, HttpServletRequest request) {
        String dept = jwtUserDto.getUser().getDept().getName();
        String ip = StringUtils.getIp(request);
        String browser = StringUtils.getBrowser(request);
        String address = StringUtils.getCityInfo(ip);

        try {
            OnlineUserDto onlineUserDto = new OnlineUserDto(jwtUserDto.getUsername(), jwtUserDto.getUser().getNickName(), dept, browser, ip, address, EncryptUtils.desEncrypt(token), new Date());
            redisUtils.set(properties.getOnlineKey() + token, onlineUserDto, properties.getTokenValidityInSeconds() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询用户
     *
     * @param key /
     * @return /
     */
    public OnlineUserDto getOne(String key) {
        return (OnlineUserDto) redisUtils.get(key);
    }

    /**
     * 剔除登录用户
     *
     * @param token
     */
    public void delete(String token) {
        String key = properties.getOnlineKey() + token;
        redisUtils.del(key);
    }

}

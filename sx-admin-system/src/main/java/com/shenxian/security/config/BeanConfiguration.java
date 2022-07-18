package com.shenxian.security.config;

import com.shenxian.security.config.bean.CaptchaProperties;
import com.shenxian.security.config.bean.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件转换 bean 的统一配置类
 *
 * @author: shenxian
 * @date: 2022/7/11 9:48
 */
@Configuration
public class BeanConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "jwt")
    public SecurityProperties securityProperties() {
        return new SecurityProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "captcha")
    public CaptchaProperties captchaProperties() {
        return new CaptchaProperties();
    }

}

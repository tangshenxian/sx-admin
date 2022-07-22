package com.shenxian;

import com.shenxian.utils.SpringContextHolder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author: shenxian
 * @date: 2022/7/4 10:43
 */
@MapperScan(basePackages = {"com.shenxian.**.mapper"})
@SpringBootApplication
public class SxAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SxAdminApplication.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

}

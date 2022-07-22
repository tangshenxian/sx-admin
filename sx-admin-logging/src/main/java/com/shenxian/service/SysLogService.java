package com.shenxian.service;

import com.shenxian.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author shenxian
 * @since 2022-07-22
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 保存日志数据
     * @param username 用户
     * @param browser 浏览器
     * @param ip 请求ip
     * @param joinPoint /
     * @param log 日志实体
     */
    @Async
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SysLog log);
}

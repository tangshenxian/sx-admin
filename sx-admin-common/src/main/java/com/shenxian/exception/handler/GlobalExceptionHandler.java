package com.shenxian.exception.handler;

import com.shenxian.exception.BadRequestException;
import com.shenxian.utils.Result;
import com.shenxian.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: shenxian
 * @date: 2022/7/11 11:21
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义异常处理
     *
     * @param e /
     * @return /
     */
    @ExceptionHandler(BadRequestException.class)
    public Result badRequestException(BadRequestException e) {
        log.error(ThrowableUtil.getStackTrace(e));
        return Result.error(e.getStatus(), e.getMessage());
    }

    /**
     * BadCredentialsException
     *
     * @param e /
     * @return /
     */
    @ExceptionHandler(BadCredentialsException.class)
    public Result badCredentialsException(BadCredentialsException e) {
        String message = "坏的凭证".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
        log.error(ThrowableUtil.getStackTrace(e));
        return Result.error(message);
    }

    /**
     * 处理所有不可知的异常
     *
     * @return /
     */
    @ExceptionHandler(Throwable.class)
    public Result handleException(Throwable e) {
        log.error(ThrowableUtil.getStackTrace(e));
        return Result.error(500, "系统异常");
    }
}

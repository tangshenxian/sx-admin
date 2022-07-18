package com.shenxian.security.rest;

import com.shenxian.security.service.AuthService;
import com.shenxian.security.service.dto.AuthUserDto;
import com.shenxian.utils.Result;
import com.shenxian.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: shenxian
 * @date: 2022/7/11 11:11
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "系统：系统授权接口")
public class AuthorizationController {

    private final AuthService authService;

    @ApiOperation("登录授权")
    @PostMapping("/login")
    public Result login(@RequestBody @Validated AuthUserDto authUser, HttpServletRequest request) {
        return Result.success().data(authService.login(authUser, request));
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/info")
    public Result info() {
        return Result.success().data(SecurityUtils.getCurrentUser());
    }

    @ApiOperation("获取验证码")
    @GetMapping("/code")
    public Result code() {
        return Result.success().data(authService.code());
    }

    @ApiOperation("退出登录")
    @DeleteMapping("/logout")
    public Result logout(HttpServletRequest request) {
        authService.logout(request);
        return Result.success();
    }

}

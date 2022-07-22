package com.shenxian.rest;


import com.shenxian.annotation.Log;
import com.shenxian.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author shenxian
 * @since 2022-07-22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/logs")
@Api(tags = "系统：日志管理")
public class SysLogController {

    @Log("测试日志记录")
    @ApiOperation("测试日志")
    @GetMapping("/logTest")
    public Result logTest() {
        return Result.success().message("请求成功");
    }

}

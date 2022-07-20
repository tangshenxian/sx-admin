package com.shenxian.system.rest;

import com.shenxian.system.service.SysMenuService;
import com.shenxian.system.service.dto.SysMenuDto;
import com.shenxian.utils.Result;
import com.shenxian.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 系统菜单 前端控制器
 * </p>
 *
 * @author shenxian
 * @since 2022-07-08
 */
@Slf4j
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
@Api(tags = "系统：菜单管理")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    @ApiOperation("获取所有菜单")
    @GetMapping(value = "/build")
    public Result build() {
        List<SysMenuDto> menuDtoList = sysMenuService.findByUser(SecurityUtils.getCurrentUserId());
        List<SysMenuDto> menuTree =sysMenuService.buildTree(menuDtoList);
        return Result.success().data(sysMenuService.buildMenus(menuTree));
    }

}

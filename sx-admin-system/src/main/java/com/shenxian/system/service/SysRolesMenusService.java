package com.shenxian.system.service;

import com.shenxian.system.entity.SysRolesMenus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色菜单关联 服务类
 * </p>
 *
 * @author shenxian
 * @since 2022-07-14
 */
public interface SysRolesMenusService extends IService<SysRolesMenus> {

    List<SysRolesMenus> roleMenusByRoleId(Set<Long> roleIds);
}

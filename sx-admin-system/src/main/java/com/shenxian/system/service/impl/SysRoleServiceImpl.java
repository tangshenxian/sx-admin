package com.shenxian.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenxian.system.entity.SysMenu;
import com.shenxian.system.entity.SysRole;
import com.shenxian.system.entity.SysRolesMenus;
import com.shenxian.system.mapper.SysRoleMapper;
import com.shenxian.system.service.SysMenuService;
import com.shenxian.system.service.SysRoleService;
import com.shenxian.system.service.SysRolesMenusService;
import com.shenxian.system.service.dto.SysUserDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author shenxian
 * @since 2022-07-08
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRolesMenusService sysRolesMenusService;
    private final SysMenuService sysMenuService;

    @Override
    public List<GrantedAuthority> grantedAuthorities(SysUserDto user) {
        Set<String> permissions = new HashSet<>();
        if (user.getIsAdmin()) {
            permissions.add("admin");
            return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }
        // 获取用户对应 SysRole 角色
        Set<SysRole> roles = this.baseMapper.rolesByUserId(user.getId());
        Set<Long> roleIds = roles.stream().map(SysRole::getRoleId).collect(Collectors.toSet());
        // 获取角色对应 SysRolesMenu 菜单ID
        Map<Long, List<SysRolesMenus>> roleMenuIds = sysRolesMenusService.roleMenusByRoleId(roleIds).stream()
                .collect(Collectors.groupingBy(SysRolesMenus::getRoleId));
        Set<Long> menuIds = roleMenuIds.values().stream().flatMap(item -> item.stream().map(SysRolesMenus::getMenuId)).collect(Collectors.toSet());
        // 获取 SysMenu 菜单
        List<SysMenu> menus = sysMenuService.listByIds(menuIds);
        permissions = menus.stream().map(SysMenu::getPermission).filter(StringUtils::isNotBlank).collect(Collectors.toSet());

        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}

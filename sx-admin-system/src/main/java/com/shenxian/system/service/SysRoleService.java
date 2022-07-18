package com.shenxian.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shenxian.system.entity.SysRole;
import com.shenxian.system.service.dto.SysUserDto;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author shenxian
 * @since 2022-07-08
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 获取用户权限信息
     * @param user /
     * @return /
     */
    List<GrantedAuthority> grantedAuthorities(SysUserDto user);

}

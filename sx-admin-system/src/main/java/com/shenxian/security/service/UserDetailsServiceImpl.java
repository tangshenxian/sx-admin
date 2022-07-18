package com.shenxian.security.service;

import com.shenxian.exception.BadRequestException;
import com.shenxian.security.service.dto.JwtUserDto;
import com.shenxian.system.service.SysRoleService;
import com.shenxian.system.service.SysUserService;
import com.shenxian.system.service.dto.SysUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author: shenxian
 * @date: 2022/7/14 9:45
 */
@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserDto user = sysUserService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        if (user.getEnabled().equals(Boolean.FALSE)) {
            throw new BadRequestException("账号未激活！");
        }
        return new JwtUserDto(user, sysRoleService.grantedAuthorities(user));
    }
}

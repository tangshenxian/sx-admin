package com.shenxian.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shenxian.system.entity.SysUser;
import com.shenxian.system.service.dto.SysUserDto;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author shenxian
 * @since 2022-07-08
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询
     * @param username /
     * @return /
     */
    SysUserDto findByUsername(String username);

}

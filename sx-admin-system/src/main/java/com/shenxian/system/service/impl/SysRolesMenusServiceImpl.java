package com.shenxian.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shenxian.system.entity.SysRolesMenus;
import com.shenxian.system.mapper.SysRolesMenusMapper;
import com.shenxian.system.service.SysRolesMenusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色菜单关联 服务实现类
 * </p>
 *
 * @author shenxian
 * @since 2022-07-14
 */
@Service
public class SysRolesMenusServiceImpl extends ServiceImpl<SysRolesMenusMapper, SysRolesMenus> implements SysRolesMenusService {

    @Override
    public List<SysRolesMenus> roleMenusByRoleId(Set<Long> roleIds) {
        QueryWrapper<SysRolesMenus> query = new QueryWrapper<>();
        query.lambda().in(SysRolesMenus::getRoleId, roleIds);
        return this.baseMapper.selectList(query);
    }
}

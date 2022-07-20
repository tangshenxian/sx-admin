package com.shenxian.system.mapper;

import com.shenxian.system.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>
 * 系统菜单 Mapper 接口
 * </p>
 *
 * @author shenxian
 * @since 2022-07-08
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    LinkedHashSet<SysMenu> menusByRoleIds(@Param("roleIds") Set<Long> roleIds, @Param("type") Long type);
}

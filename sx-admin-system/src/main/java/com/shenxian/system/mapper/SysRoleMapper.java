package com.shenxian.system.mapper;

import com.shenxian.system.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author shenxian
 * @since 2022-07-08
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    Set<SysRole> rolesByUserId(@Param("userId") Long userId);

}

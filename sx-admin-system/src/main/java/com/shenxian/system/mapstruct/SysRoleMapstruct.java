package com.shenxian.system.mapstruct;

import com.shenxian.system.entity.SysRole;
import com.shenxian.system.service.dto.SysRoleSmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

/**
 * @author: shenxian
 * @date: 2022/7/14 10:13
 */
@Mapper(componentModel = "spring")
public interface SysRoleMapstruct {

    /**
     * entity转dto
     *
     * @param sysUser /
     * @return /
     */
    @Mapping(source = "roleId", target = "id")
    SysRoleSmallDto convertToSmallDto(SysRole sysUser);

    /**
     * entity转dto -- list
     * @param sysUser
     * @return
     */
    List<SysRoleSmallDto> convertToSmallDtos(Set<SysRole> sysUser);

}

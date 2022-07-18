package com.shenxian.system.mapstruct;

import com.shenxian.system.entity.SysUser;
import com.shenxian.system.service.dto.SysUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author: shenxian
 * @date: 2022/7/14 10:13
 */
@Mapper(componentModel = "spring")
public interface SysUserMapstruct {

    /**
     * entity转dto
     * @param sysUser /
     * @return /
     */
    @Mapping(source = "userId", target = "id")
    SysUserDto convertToDto(SysUser sysUser);

}

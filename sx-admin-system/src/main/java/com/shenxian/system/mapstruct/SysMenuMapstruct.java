package com.shenxian.system.mapstruct;

import com.shenxian.system.entity.SysMenu;
import com.shenxian.system.service.dto.SysMenuDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author: shenxian
 * @date: 2022/7/14 10:13
 */
@Mapper(componentModel = "spring")
public interface SysMenuMapstruct {

    /**
     * entity转dto
     *
     * @param sysUser /
     * @return /
     */
    @Mapping(source = "menuId", target = "id")
    SysMenuDto convertToDto(SysMenu sysUser);

}

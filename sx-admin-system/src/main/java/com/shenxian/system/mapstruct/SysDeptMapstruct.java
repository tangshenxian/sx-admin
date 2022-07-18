package com.shenxian.system.mapstruct;

import com.shenxian.system.entity.SysDept;
import com.shenxian.system.service.dto.SysDeptSmallDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author: shenxian
 * @date: 2022/7/15 10:45
 */
@Mapper(componentModel = "spring")
public interface SysDeptMapstruct {

    /**
     * entity转dto
     * @param sysDept /
     * @return /
     */
    @Mapping(source = "deptId", target = "id")
    SysDeptSmallDto convertToSmallDto(SysDept sysDept);

}

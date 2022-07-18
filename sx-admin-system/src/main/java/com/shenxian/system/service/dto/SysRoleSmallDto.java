package com.shenxian.system.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: shenxian
 * @date: 2022/7/11 10:41
 */
@Data
public class SysRoleSmallDto implements Serializable {

    private Long id;

    private String name;

    private Integer level;

    private String dataScope;
}

package com.shenxian.system.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @author: shenxian
* @date: 2022/7/11 10:41
*/
@Data
@NoArgsConstructor
public class SysJobSmallDto implements Serializable {

    private Long id;

    private String name;
}
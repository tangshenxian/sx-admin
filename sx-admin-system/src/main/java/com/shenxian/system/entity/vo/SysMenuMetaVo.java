package com.shenxian.system.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author shenxian
 * @date 2022-07-20
 */
@Data
@AllArgsConstructor
public class SysMenuMetaVo implements Serializable {

    private String title;

    private String icon;

    private Boolean noCache;
}

package com.shenxian.system.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.shenxian.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author shenxian
 * @date 2022/7/11 10:41
 */
@Getter
@Setter
public class SysUserDto extends BaseDTO implements Serializable {

    private Long id;

    private Set<SysRoleSmallDto> roles;

    private Set<SysJobSmallDto> jobs;

    private SysDeptSmallDto dept;

    private Long deptId;

    private String username;

    private String nickName;

    private String email;

    private String phone;

    private String gender;

    private String avatarName;

    private String avatarPath;

    @JSONField(serialize = false)
    private String password;

    private Boolean enabled;

    @JSONField(serialize = false)
    private Boolean isAdmin = false;

    private Date pwdResetTime;
}

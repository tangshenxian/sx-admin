package com.shenxian.security.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author: shenxian
 * @date: 2022/7/11 10:41
 */
@Getter
@Setter
public class AuthUserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";
}

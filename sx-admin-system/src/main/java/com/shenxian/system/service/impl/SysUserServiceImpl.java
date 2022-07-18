package com.shenxian.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenxian.system.entity.SysUser;
import com.shenxian.system.mapper.SysDeptMapper;
import com.shenxian.system.mapper.SysUserMapper;
import com.shenxian.system.mapstruct.SysDeptMapstruct;
import com.shenxian.system.mapstruct.SysUserMapstruct;
import com.shenxian.system.service.SysDeptService;
import com.shenxian.system.service.SysUserService;
import com.shenxian.system.service.dto.SysDeptSmallDto;
import com.shenxian.system.service.dto.SysUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author shenxian
 * @since 2022-07-08
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysDeptMapper sysDeptMapper;
    private final SysUserMapstruct sysUserMapstruct;
    private final SysDeptMapstruct sysDeptMapstruct;

    @Override
    public SysUserDto findByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        SysUserDto userDto = sysUserMapstruct.convertToDto(this.baseMapper.selectOne(wrapper));
        if (userDto != null) {
            SysDeptSmallDto smallDept = sysDeptMapstruct.convertToSmallDto(sysDeptMapper.selectById(userDto.getDeptId()));
            userDto.setDept(smallDept);
        }
        return userDto;
    }
}

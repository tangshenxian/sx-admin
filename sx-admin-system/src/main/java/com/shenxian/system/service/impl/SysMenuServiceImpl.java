package com.shenxian.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.shenxian.system.entity.SysMenu;
import com.shenxian.system.entity.SysRole;
import com.shenxian.system.entity.vo.SysMenuMetaVo;
import com.shenxian.system.entity.vo.SysMenuVo;
import com.shenxian.system.mapper.SysMenuMapper;
import com.shenxian.system.mapper.SysRoleMapper;
import com.shenxian.system.mapstruct.SysMenuMapstruct;
import com.shenxian.system.mapstruct.SysRoleMapstruct;
import com.shenxian.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shenxian.system.service.SysRoleService;
import com.shenxian.system.service.dto.SysMenuDto;
import com.shenxian.system.service.dto.SysRoleSmallDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单 服务实现类
 * </p>
 *
 * @author shenxian
 * @since 2022-07-08
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMapstruct sysRoleMapstruct;
    private final SysMenuMapstruct sysMenuMapstruct;

    @Override
    public List<SysMenuDto> findByUser(Long currentUserId) {
        Set< SysRole > roles = sysRoleMapper.rolesByUserId(currentUserId);
        List<SysRoleSmallDto> smallDtos = sysRoleMapstruct.convertToSmallDtos(roles);
        Set<Long> roleIds = smallDtos.stream().map(SysRoleSmallDto::getId).collect(Collectors.toSet());
        LinkedHashSet<SysMenu> menus = this.baseMapper.menusByRoleIds(roleIds, 2L);
        return menus.stream().map(sysMenuMapstruct::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<SysMenuDto> buildTree(List<SysMenuDto> menuDtos) {
        List<SysMenuDto> trees = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (SysMenuDto menuDTO : menuDtos) {
            if (null == menuDTO.getPid()) {
                trees.add(menuDTO);
            }
            for (SysMenuDto it : menuDtos) {
                if (it.getPid() != null && it.getPid().equals(menuDTO.getId())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
        }
        if (trees.size() == 0) {
            trees = menuDtos.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        return trees;
    }

    @Override
    public List<SysMenuVo> buildMenus(List<SysMenuDto> menuDtos) {
        List<SysMenuVo> list = new LinkedList<>();
        menuDtos.forEach(menuDTO -> {
            if (menuDTO != null) {
                List<SysMenuDto> menuDtoList = menuDTO.getChildren();
                SysMenuVo menuVo = new SysMenuVo();
                menuVo.setName(ObjectUtil.isNotEmpty(menuDTO.getComponentName()) ? menuDTO.getComponentName()
                        : menuDTO.getTitle());
                // 一级目录需要加斜杠，不然会报警告
                menuVo.setPath(menuDTO.getPid() == null ? "/" + menuDTO.getPath() : menuDTO.getPath());
                menuVo.setHidden(menuDTO.getHidden());
                // 如果不是外链
                if (!menuDTO.getIFrame()) {
                    if (menuDTO.getPid() == null) {
                        menuVo.setComponent(
                                StrUtil.isEmpty(menuDTO.getComponent()) ? "Layout" : menuDTO.getComponent());
                    } else if (!StrUtil.isEmpty(menuDTO.getComponent())) {
                        menuVo.setComponent(menuDTO.getComponent());
                    }
                }
                menuVo.setMeta(new SysMenuMetaVo(menuDTO.getTitle(), menuDTO.getIcon(), !menuDTO.getCache()));
                if (menuDtoList != null && menuDtoList.size() != 0) {
                    menuVo.setAlwaysShow(true);
                    menuVo.setRedirect("noredirect");
                    menuVo.setChildren(buildMenus(menuDtoList));
                    // 处理是一级菜单并且没有子菜单的情况
                } else if (menuDTO.getPid() == null) {
                    SysMenuVo menuVo1 = new SysMenuVo();
                    menuVo1.setMeta(menuVo.getMeta());
                    // 非外链
                    if (!menuDTO.getIFrame()) {
                        menuVo1.setPath("index");
                        menuVo1.setName(menuVo.getName());
                        menuVo1.setComponent(menuVo.getComponent());
                    } else {
                        menuVo1.setPath(menuDTO.getPath());
                    }
                    menuVo.setName(null);
                    menuVo.setMeta(null);
                    menuVo.setComponent("Layout");
                    List<SysMenuVo> list1 = new ArrayList<>();
                    list1.add(menuVo1);
                    menuVo.setChildren(list1);
                }
                list.add(menuVo);
            }
        });
        return list;
    }

}

package com.shenxian.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shenxian.system.entity.SysMenu;
import com.shenxian.system.entity.vo.SysMenuVo;
import com.shenxian.system.service.dto.SysMenuDto;

import java.util.List;

/**
 * <p>
 * 系统菜单 服务类
 * </p>
 *
 * @author shenxian
 * @since 2022-07-08
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据当前用户获取菜单
     *
     * @param currentUserId /
     * @return /
     */
    List<SysMenuDto> findByUser(Long currentUserId);

    /**
     * 构建菜单树
     *
     * @param menuDtoList /
     * @return /
     */
    List<SysMenuDto> buildTree(List<SysMenuDto> menuDtoList);

    /**
     * 构建菜单树
     *
     * @param menuDtos /
     * @return /
     */
    List<SysMenuVo> buildMenus(List<SysMenuDto> menuDtos);
}

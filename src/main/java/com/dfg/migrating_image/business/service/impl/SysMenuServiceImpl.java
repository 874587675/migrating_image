package com.dfg.migrating_image.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfg.migrating_image.business.entity.SysMenu;
import com.dfg.migrating_image.business.mapper.SysMenuMapper;
import com.dfg.migrating_image.business.param.convertor.SysMenuConvertor;
import com.dfg.migrating_image.business.param.page.SysMenuPageParam;
import com.dfg.migrating_image.business.param.page.vo.PageVO;
import com.dfg.migrating_image.business.param.query.SysMenuQueryParam;
import com.dfg.migrating_image.business.param.remove.RemoveBaseParam;
import com.dfg.migrating_image.business.param.save.SysMenuSaveParam;
import com.dfg.migrating_image.business.param.update.SysMenuUpdateParam;
import com.dfg.migrating_image.business.param.vo.SysMenuVO;
import com.dfg.migrating_image.business.service.SysMenuService;
import com.dfg.migrating_image.common.constant.MenuConstants;
import com.dfg.migrating_image.common.exception.ServiceException;
import com.dfg.migrating_image.framework.security.core.AuthenticationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author zgc
 * @since 2025-06-30 11:38:23
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * 保存菜单权限
     *
     * @param sysMenuSaveParam 保存菜单权限参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysMenu(SysMenuSaveParam sysMenuSaveParam) {
        sysMenuMapper.insert(SysMenuConvertor.SAVE.saveParamToEntity(sysMenuSaveParam));
    }

    /**
     * 修改菜单权限
     *
     * @param sysMenuUpdateParam 修改菜单权限参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSysMenu(SysMenuUpdateParam sysMenuUpdateParam) {
        sysMenuMapper.updateById(SysMenuConvertor.UPDATE.updateParamToEntity(sysMenuUpdateParam));
    }

    /**
     * 删除菜单权限属性
     *
     * @param removeBaseParam 删除菜单权限属性参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeSysMenu(RemoveBaseParam removeBaseParam) {
        String serializableId = removeBaseParam.getSerializableId();
        boolean exists = sysMenuMapper.exists(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, serializableId));
        if (exists) {
            throw new ServiceException("存在子菜单，不允许删除");
        }
        sysMenuMapper.deleteById(serializableId);
    }

    /**
     * 后台用户查询菜单权限列表
     *
     * @return 菜单权限VO列表
     */
    @Override
    public List<SysMenuVO> backListSysMenu() {
        String backUserId = AuthenticationContextHolder.getBackUserId();
        List<SysMenu> sysMenuList = sysMenuMapper.selectMenuIdByUserId(backUserId);
        List<SysMenuVO> sysMenuVOList = SysMenuConvertor.LIST.listEntityToListVO(sysMenuList);
        // 递归构建菜单树
        sysMenuVOList.forEach(menu -> buildMenuTree(menu, backUserId));
        return sysMenuVOList;
    }

    private void buildMenuTree(SysMenuVO parentMenu, String userId) {
        List<SysMenu> subMenuList = sysMenuMapper.selectSubMenuIdByUserId(userId, parentMenu.getMenuId());
        if (CollectionUtil.isEmpty(subMenuList)) {
            parentMenu.setChildren(new ArrayList<>());
            return;
        }
        List<SysMenuVO> subMenuVOList = SysMenuConvertor.LIST.listEntityToListVO(subMenuList);
        parentMenu.setChildren(subMenuVOList);
        subMenuVOList.forEach(subMenu -> buildMenuTree(subMenu, userId));
    }

    /**
     * 后台用户分页查询菜单权限列表
     *
     * @param sysMenuPageParam 查询菜单权限列表参数
     * @return 菜单权限VO列表
     */
    @Override
    public PageVO<SysMenuVO> backPageSysMenu(SysMenuPageParam sysMenuPageParam) {
        String sysMenuId = sysMenuPageParam.getMenuId();
        Page<SysMenu> page = page(new Page<>(sysMenuPageParam.getPageNo(), sysMenuPageParam.getPageSize()),
                new LambdaQueryWrapper<SysMenu>()
                        .eq(ObjectUtil.isNotEmpty(sysMenuId), SysMenu::getMenuId, sysMenuId)
                        .eq(SysMenu::getParentId, MenuConstants.ROOT_PARENT_ID)
                        .orderByAsc(SysMenu::getMenuSort)
        );
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return PageVO.addPageData(page, Collections.emptyList());
        }

        Set<String> rootIds = page.getRecords().stream()
                .map(SysMenu::getMenuId)
                .collect(Collectors.toSet());

        List<SysMenu> allMenus = sysMenuMapper.selectList(
                new LambdaQueryWrapper<SysMenu>()
                        .orderByAsc(SysMenu::getMenuSort)
        );

        List<SysMenuVO> allMenuVOList = SysMenuConvertor.LIST.listEntityToListVO(allMenus);
        List<SysMenuVO> fullTree = buildTree(allMenuVOList);

        List<SysMenuVO> resultTree = fullTree.stream()
                .filter(menu -> rootIds.contains(menu.getMenuId()))
                .collect(Collectors.toList());

        return PageVO.addPageData(page, resultTree);
    }

    /**
     * 构建菜单树
     */
    private List<SysMenuVO> buildTree(List<SysMenuVO> menuList) {
        if (CollectionUtil.isEmpty(menuList)) {
            return Collections.emptyList();
        }

        Map<String, SysMenuVO> menuMap = menuList.stream()
                .collect(Collectors.toMap(SysMenuVO::getMenuId, Function.identity()));

        menuList.forEach(menu -> {
            if (ObjectUtil.isEmpty(menu.getChildren())) {
                menu.setChildren(new ArrayList<>());
            }
        });

        List<SysMenuVO> rootList = new ArrayList<>();
        for (SysMenuVO menu : menuList) {
            String parentId = menu.getParentId();
            if (MenuConstants.ROOT_PARENT_ID.equals(parentId)) {
                rootList.add(menu);
            } else {
                SysMenuVO parent = menuMap.get(parentId);
                if (ObjectUtil.isNotEmpty(parent)) {
                    parent.getChildren().add(menu);
                } else {
                    rootList.add(menu);
                }
            }
        }

        return rootList;
    }

    /**
     * 后台用户查询菜单权限详情
     *
     * @param sysMenuQueryParam 查询菜单权限详情参数
     * @return 菜单权限VO
     */
    @Override
    public SysMenuVO backQuerySysMenu(SysMenuQueryParam sysMenuQueryParam) {
        return SysMenuConvertor.QUERY.entityToVo(sysMenuMapper.selectById(sysMenuQueryParam.getMenuId()));
    }

}

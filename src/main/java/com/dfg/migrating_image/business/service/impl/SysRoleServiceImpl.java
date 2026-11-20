package com.dfg.migrating_image.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfg.migrating_image.business.entity.SysMenu;
import com.dfg.migrating_image.business.entity.SysRole;
import com.dfg.migrating_image.business.entity.SysRoleMenu;
import com.dfg.migrating_image.business.entity.SysUserRole;
import com.dfg.migrating_image.business.mapper.SysMenuMapper;
import com.dfg.migrating_image.business.mapper.SysRoleMapper;
import com.dfg.migrating_image.business.mapper.SysRoleMenuMapper;
import com.dfg.migrating_image.business.mapper.SysUserRoleMapper;
import com.dfg.migrating_image.business.param.convertor.SysMenuConvertor;
import com.dfg.migrating_image.business.param.convertor.SysRoleConvertor;
import com.dfg.migrating_image.business.param.list.SysRoleListParam;
import com.dfg.migrating_image.business.param.page.SysRolePageParam;
import com.dfg.migrating_image.business.param.page.vo.PageVO;
import com.dfg.migrating_image.business.param.query.SysRoleQueryParam;
import com.dfg.migrating_image.business.param.remove.RemoveBaseParam;
import com.dfg.migrating_image.business.param.save.SysRoleSaveParam;
import com.dfg.migrating_image.business.param.update.SysRoleUpdateParam;
import com.dfg.migrating_image.business.param.vo.SysMenuVO;
import com.dfg.migrating_image.business.param.vo.SysRoleVO;
import com.dfg.migrating_image.business.service.SysRoleService;
import com.dfg.migrating_image.common.constant.MenuConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author zgc
 * @since 2025-06-30 11:42:56
 */
@Service
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 保存角色信息
     *
     * @param sysRoleSaveParam 保存角色信息参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysRole(SysRoleSaveParam sysRoleSaveParam) {
        SysRole sysRole = SysRoleConvertor.SAVE.saveParamToEntity(sysRoleSaveParam);
        sysRoleMapper.insert(sysRole);

        String roleId = sysRole.getRoleId();

        List<SysMenuVO> selfMenuList = sysRoleSaveParam.getSelfMenuList();

        if (CollectionUtil.isNotEmpty(selfMenuList)) {
            List<String> menuIds = selfMenuList.stream()
                    .map(SysMenuVO::getMenuId)
                    .toList();

            if (CollectionUtil.isNotEmpty(menuIds)) {
                List<SysRoleMenu> roleMenus = menuIds.stream()
                        .map(menuId -> SysRoleMenu.builder()
                                .roleId(roleId)
                                .menuId(menuId)
                                .build())
                        .collect(Collectors.toList());

                sysRoleMenuMapper.insertBatchSomeColumn(roleMenus);
            }
        }
    }

    /**
     * 修改角色信息
     *
     * @param sysRoleUpdateParam 修改角色信息参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSysRole(SysRoleUpdateParam sysRoleUpdateParam) {
        String roleId = sysRoleUpdateParam.getRoleId();

        sysRoleMapper.updateById(SysRoleConvertor.UPDATE.updateParamToEntity(sysRoleUpdateParam));

        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));


        List<SysMenuVO> selfMenuList = sysRoleUpdateParam.getSelfMenuList();
        if (CollectionUtil.isNotEmpty(selfMenuList)) {

            List<String> menuIds = selfMenuList.stream()
                    .map(SysMenuVO::getMenuId)
                    .toList();

            List<SysMenu> allMenus = sysMenuMapper.selectList(
                    new LambdaQueryWrapper<SysMenu>()
                            .orderByAsc(SysMenu::getMenuSort)
            );
            Map<String, SysMenu> menuMap = allMenus.stream()
                    .collect(Collectors.toMap(SysMenu::getMenuId, Function.identity()));

            Set<String> allMenuIds = new HashSet<>(menuIds);
            for (String menuId : menuIds) {
                addParentMenuIds(menuId, menuMap, allMenuIds);
            }

            List<SysRoleMenu> roleMenus = allMenuIds.stream()
                    .map(menuId -> SysRoleMenu.builder().roleId(roleId).menuId(menuId).build())
                    .collect(Collectors.toList());

            sysRoleMenuMapper.insertBatchSomeColumn(roleMenus);
        }
    }

    /**
     * 递归补全父级菜单
     */
    private void addParentMenuIds(String menuId, Map<String, SysMenu> menuMap, Set<String> resultSet) {
        SysMenu menu = menuMap.get(menuId);
        if (ObjectUtil.isNull(menu)) {
            return;
        }
        String parentId = menu.getParentId();
        if (!MenuConstants.ROOT_PARENT_ID.equals(parentId)) {
            if (resultSet.add(parentId)) {
                addParentMenuIds(parentId, menuMap, resultSet);
            }
        }
    }


    /**
     * 删除角色信息属性
     *
     * @param removeBaseParam 删除角色信息属性参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeSysRole(RemoveBaseParam removeBaseParam) {
        String serializableId = removeBaseParam.getSerializableId();
        boolean exists = sysUserRoleMapper.exists(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getRoleId, serializableId)
        );
        if (exists) {
            throw new RuntimeException("角色已分配用户，不允许删除");
        }
        sysRoleMapper.deleteById(serializableId);
    }

    /**
     * 后台用户查询角色信息列表
     *
     * @param sysRoleListParam 查询角色信息列表参数
     * @return 角色信息VO列表
     */
    @Override
    public List<SysRoleVO> backListSysRole(SysRoleListParam sysRoleListParam) {
        String sysRoleId = sysRoleListParam.getRoleId();
        List<SysRole> list = list(
                new LambdaQueryWrapper<SysRole>()
                        .eq(ObjectUtil.isNotEmpty(sysRoleId), SysRole::getRoleId, sysRoleId)
                        .orderByDesc(SysRole::getRoleSort)
        );
        return SysRoleConvertor.LIST.listEntityToListVO(list);
    }

    /**
     * 后台用户分页查询角色信息列表
     *
     * @param sysRolePageParam 查询角色信息列表参数
     * @return 角色信息VO列表
     */
    @Override
    public PageVO<SysRoleVO> backPageSysRole(SysRolePageParam sysRolePageParam) {
        String sysRoleId = sysRolePageParam.getRoleId();
        Page<SysRole> page = page(new Page<>(sysRolePageParam.getPageNo(), sysRolePageParam.getPageSize()),
                new LambdaQueryWrapper<SysRole>()
                        .eq(ObjectUtil.isNotEmpty(sysRoleId), SysRole::getRoleId, sysRoleId)
                        .orderByDesc(SysRole::getRoleSort)
        );
        return PageVO.addPageData(page, SysRoleConvertor.LIST.listEntityToListVO(page.getRecords()));
    }

    /**
     * 后台用户查询角色信息详情
     *
     * @param sysRoleQueryParam 查询角色信息详情参数
     * @return 角色信息VO
     */
    @Override
    public SysRoleVO backQuerySysRole(SysRoleQueryParam sysRoleQueryParam) {
        String roleId = sysRoleQueryParam.getRoleId();

        SysRoleVO sysRoleVO = SysRoleConvertor.QUERY.entityToVo(
                sysRoleMapper.selectById(roleId)
        );

        List<SysMenu> allMenus = sysMenuMapper.selectList(
                new LambdaQueryWrapper<SysMenu>()
                        .orderByAsc(SysMenu::getMenuSort)
        );
        List<SysMenuVO> allMenuVOList = SysMenuConvertor.LIST.listEntityToListVO(allMenus);

        List<SysMenuVO> allTreeMenuList = buildTree(allMenuVOList);
        sysRoleVO.setAllMenuList(allTreeMenuList);

        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(
                new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId)
        );

        if (CollectionUtil.isNotEmpty(sysRoleMenuList)) {
            // 对应角色拥有的菜单ID
            Set<String> menuIds = sysRoleMenuList.stream()
                    .map(SysRoleMenu::getMenuId)
                    .collect(Collectors.toSet());

            List<SysMenuVO> selfTreeMenuList = filterTreeByMenuIds(allTreeMenuList, menuIds);
            sysRoleVO.setSelfMenuList(selfTreeMenuList);
        } else {
            sysRoleVO.setSelfMenuList(Collections.emptyList());
        }

        return sysRoleVO;
    }

    /**
     * 构建菜单树
     */
    public static List<SysMenuVO> buildTree(List<SysMenuVO> menuList) {
        if (CollectionUtil.isEmpty(menuList)) {
            return Collections.emptyList();
        }
        Map<String, SysMenuVO> menuMap = menuList.stream()
                .collect(Collectors.toMap(SysMenuVO::getMenuId, Function.identity()));

        menuList.forEach(menu -> menu.setChildren(new ArrayList<>()));

        return menuList.stream()
                .filter(menu -> {
                    String parentId = menu.getParentId();
                    if (ObjectUtil.isNotEmpty(parentId) && menuMap.containsKey(parentId)) {
                        menuMap.get(parentId).getChildren().add(menu);
                        return false;  // 子节点加入对应根节点的children列表
                    }
                    return true;  // 是根节点不处理
                })
                .collect(Collectors.toList());
    }

    /**
     * 根据菜单ID过滤树，保留包含这些菜单的子树
     */
    private List<SysMenuVO> filterTreeByMenuIds(List<SysMenuVO> treeList, Set<String> menuIds) {
        if (CollectionUtil.isEmpty(treeList)) {
            return Collections.emptyList();
        }

        List<SysMenuVO> result = new ArrayList<>();
        for (SysMenuVO menu : treeList) {
            // 如果当前节点或子节点包含目标菜单ID
            if (menuIds.contains(menu.getMenuId()) || containsTargetMenu(menu.getChildren(), menuIds)) {
                // 深拷贝当前节点
                SysMenuVO copy = new SysMenuVO();
                BeanUtil.copyProperties(menu, copy);
                // 递归处理子节点
                copy.setChildren(filterTreeByMenuIds(menu.getChildren(), menuIds));
                result.add(copy);
            }
        }
        return result;
    }

    private boolean containsTargetMenu(List<SysMenuVO> children, Set<String> menuIds) {
        if (CollectionUtil.isEmpty(children)) {
            return false;
        }
        for (SysMenuVO child : children) {
            if (menuIds.contains(child.getMenuId()) ||
                    containsTargetMenu(child.getChildren(), menuIds)) {
                return true;
            }
        }
        return false;
    }
}
